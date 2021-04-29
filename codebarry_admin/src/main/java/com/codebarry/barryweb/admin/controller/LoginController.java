package com.codebarry.barryweb.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.codebarry.barryweb.base.global.Constants;
import com.codebarry.barryweb.commons.config.jwt.Audience;
import com.codebarry.barryweb.commons.config.jwt.JwtTokenUtil;
import com.codebarry.barryweb.commons.entity.Admin;
import com.codebarry.barryweb.commons.entity.Role;
import com.codebarry.barryweb.utils.*;
import com.codebarry.barryweb.xo.global.MessageConf;
import com.codebarry.barryweb.xo.global.RedisConf;
import com.codebarry.barryweb.xo.global.SQLConf;
import com.codebarry.barryweb.xo.global.SysConf;
import com.codebarry.barryweb.xo.service.AdminService;
import com.codebarry.barryweb.xo.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author :cjh
 * @date : 13:07 2021/2/18
 */
@RefreshScope
@Api(value = "登录相关接口", tags = {"登录相关接口"})
@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RoleService roleService;
    @Autowired
    private Audience audience;
    @Value(value = "${tokenHead}")
    private String tokenHead;
    @Value(value = "${isRememberMeExpiresSecond}")
    private int isRememberMeExpiresSecond;
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        @ApiParam(name = "username", value = "用户名或邮箱或手机号") @RequestParam(name = "username", required = false) String username,
                        @ApiParam(name = "password", value = "密码") @RequestParam(name = "password", required = false) String password,
                        @ApiParam(name = "isRememberMe", value = "是否记住账号密码") @RequestParam(name = "isRememberMe", required = false, defaultValue = "false") Boolean isRememberMe) {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultUtil.result(SysConf.ERROR, "账号或密码不能为空");
        }
        String ip = IpUtils.getIpAddr(request);
        //限制登陆次数
        String limitCount = redisUtil.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        if (StringUtils.isNotEmpty(limitCount)) {
            Integer tempLimitCount = Integer.valueOf(limitCount);
            if (tempLimitCount >= Constants.NUM_FIVE) {
                return ResultUtil.result(SysConf.ERROR, "密码输错次数过多,已被锁定30分钟");
            }
        }
        Boolean isEmail = CheckUtils.checkEmail(username);
        Boolean isMobile = CheckUtils.checkMobileNumber(username);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (isEmail) {
            queryWrapper.eq(SQLConf.EMAIL, username);
        } else if (isMobile) {
            queryWrapper.eq(SQLConf.MOBILE, username);
        } else {
            queryWrapper.eq(SQLConf.USER_NAME, username);
        }
        Admin admin = adminService.getOne(queryWrapper);
        if (admin == null) {
            // 设置错误登录次数
            return ResultUtil.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request)));
        }
        // 对密码进行加盐加密验证，采用SHA-256 + 随机盐【动态加盐】 + 密钥对密码进行加密
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPassword = encoder.matches(password, admin.getPassWord());
        if (!isPassword) {
            //密码错误，返回提示
            return ResultUtil.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request)));
        }
        List<String> roleUids = new ArrayList<>();
        roleUids.add(admin.getRoleUid());
        List<Role> roles = (List<Role>) roleService.listByIds(roleUids);

        if (roles.size() <= 0) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.NO_ROLE);
        }
        String roleNames = null;
        for (Role role : roles) {
            roleNames += (role.getRoleName() + Constants.SYMBOL_COMMA);
        }
        String roleName = roleNames.substring(0, roleNames.length() - 2);
        long expiration = isRememberMe ? isRememberMeExpiresSecond : audience.getExpiresSecond();
        String jwtToken = jwtTokenUtil.createJWT(admin.getUserName(),
                admin.getUid(),
                roleName,
                audience.getClientId(),
                audience.getName(),
                expiration * 1000,
                audience.getBase64Secret());
        String token = tokenHead + jwtToken;
        Map<String, Object> result = new HashMap<>(Constants.NUM_ONE);
        result.put(SysConf.TOKEN, token);

        //进行登录相关操作
        Integer count = admin.getLoginCount() + 1;
        admin.setLoginCount(count);
        admin.setLastLoginIp(IpUtils.getIpAddr(request));
        admin.setLastLoginTime(new Date());
        admin.updateById();
        // 设置token到validCode，用于记录登录用户
        admin.setValidCode(token);
        // 设置tokenUid，【主要用于换取token令牌，防止token直接暴露到在线用户管理中】
        admin.setTokenUid(StringUtils.getUUID());
        admin.setRole(roles.get(0));
        // 添加在线用户到Redis中【设置过期时间】
        adminService.addOnlineAdmin(admin, expiration);
        return ResultUtil.result(SysConf.SUCCESS, result);
    }

    @RequestMapping("/redis")
    @ApiOperation(value = "redis测试", notes = "redis测试")
    public String redis(){
        redisUtil.set("1","2");
        return redisUtil.get("1");
    }
    /**
     * 设置登录限制，返回剩余次数
     * 密码错误五次，将会锁定10分钟
     *
     * @param request
     */
    private Integer setLoginCommit(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        String count = redisUtil.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        Integer surplusCount = 5;
        if (StringUtils.isNotEmpty(count)) {
            Integer countTemp = Integer.valueOf(count) + 1;
            surplusCount = surplusCount - countTemp;
            redisUtil.setEx(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip, String.valueOf(countTemp), 10, TimeUnit.MINUTES);
        } else {
            surplusCount = surplusCount - 1;
            redisUtil.setEx(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip, "1", 30, TimeUnit.MINUTES);
        }
        return surplusCount;
    }
}
