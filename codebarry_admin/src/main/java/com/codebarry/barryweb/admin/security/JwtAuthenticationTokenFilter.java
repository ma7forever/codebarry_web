package com.codebarry.barryweb.admin.security;

import com.codebarry.barryweb.admin.globle.RedisConf;
import com.codebarry.barryweb.base.global.Constants;
import com.codebarry.barryweb.commons.config.jwt.Audience;
import com.codebarry.barryweb.commons.config.jwt.JwtTokenUtil;
import com.codebarry.barryweb.commons.entity.OnlineAdmin;
import com.codebarry.barryweb.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author :cjh
 * @date : 15:09 2021/3/22
 */


@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private Audience audience;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value(value = "${tokenHead}")
    private String tokenHead;

    @Value(value = "${tokenHeader}")
    private String tokenHeader;

    /**
     * token过期的时间
     */
    @Value(value = "${audience.expiresSecond}")
    private Long expiresSecond;

    /**
     * token刷新的时间
     */
    @Value(value = "${audience.refreshSecond}")
    private Long refreshSecond;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String authHeader = request.getHeader(tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {

            log.error("传递过来的token为: {}", authHeader);

            final String token = authHeader.substring(tokenHead.length());
            // 私钥
            String base64Secret = audience.getBase64Secret();
            // 获取在线的管理员信息
            String onlineAdmin = redisUtil.get(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + authHeader);
            if (StringUtils.isNotEmpty(onlineAdmin) && !jwtTokenUtil.isExpiration(token, base64Secret)) {
                /**
                 * 得到过期时间
                 */
                Date expirationDate = jwtTokenUtil.getExpiration(token, base64Secret);
                long nowMillis = System.currentTimeMillis();
                Date nowDate = new Date(nowMillis);
                // 得到两个日期相差的间隔，秒
                Integer survivalSecond = DateUtils.getSecondByTwoDay(expirationDate, nowDate);
                // 当存活时间小于更新时间，那么将颁发新的Token到客户端，同时重置新的过期时间
                // 而旧的Token将会在不久之后从Redis中过期
                if (survivalSecond < refreshSecond) {
                    // 生成一个新的Token
                    String newToken = tokenHead + jwtTokenUtil.refreshToken(token, base64Secret, expiresSecond * 1000);
                    // 生成新的token，发送到客户端
                    CookieUtils.setCookie("Admin-Token", newToken, expiresSecond.intValue());
                    OnlineAdmin newOnlineAdmin = JsonUtils.jsonToPojo(onlineAdmin, OnlineAdmin.class);
                    // 获取旧的TokenUid
                    String oldTokenUid = newOnlineAdmin.getTokenId();
                    // 随机生成一个TokenUid，用于换取Token令牌
                    String tokenUid = StringUtils.getUUID();
                    newOnlineAdmin.setTokenId(tokenUid);
                    newOnlineAdmin.setToken(newToken);
                    newOnlineAdmin.setExpireTime(DateUtils.getDateStr(new Date(), expiresSecond));
                    newOnlineAdmin.setLoginTime(DateUtils.getNowTime());
                    // 移除原来的旧Token和TokenUid
                    redisUtil.delete(RedisConf.LOGIN_TOKEN_KEY + Constants.SYMBOL_COLON + authHeader);
                    redisUtil.delete(RedisConf.LOGIN_UUID_KEY + Constants.SYMBOL_COLON + oldTokenUid);
                    // 将新token赋值，用于后续使用
                    authHeader = newToken;

                    // 将新的Token存入Redis中
                    redisUtil.setEx(RedisConf.LOGIN_TOKEN_KEY + Constants.SYMBOL_COLON + newToken, JsonUtils.objectToJson(newOnlineAdmin), expiresSecond, TimeUnit.SECONDS);
                    // 维护 uuid - token 互相转换的Redis集合【主要用于在线用户管理】
                    redisUtil.setEx(RedisConf.LOGIN_UUID_KEY + Constants.SYMBOL_COLON + tokenUid, newToken, expiresSecond, TimeUnit.SECONDS);
                }
            } else {
                filterChain.doFilter(request, response);
                return;
            }


            filterChain.doFilter(request, response);
        }
    }
}