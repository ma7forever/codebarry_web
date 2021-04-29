package com.codebarry.barryweb.myweb.controller;

import com.codebarry.barryweb.commons.entity.Admin;
import com.codebarry.barryweb.commons.entity.User;
import com.codebarry.barryweb.utils.RedisUtil;
import com.codebarry.barryweb.xo.service.AdminService;
import com.codebarry.barryweb.xo.service.UserService;
import com.codebarry.barryweb.xo.service.imp.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :cjh
 * @date : 16:46 2021/2/18
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @GetMapping("/redis")
    public String testRedis(){
        redisUtil.set("1","2");
        System.out.println(redisUtil.get("1"));
        return "结束";
    }
    @GetMapping("/mybatis")
    public Admin testMybatis(){
        /*List<String> ids = new ArrayList<String>();
        ids.add("114514");
        *//*
        System.out.println(userService.getUserListByIds(ids));*/
        return null;

    }
}
