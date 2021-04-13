package com.codebarry.barrayweb.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :cjh
 * @date : 13:07 2021/2/18
 */
@RefreshScope
@Api(value = "登录相关接口", tags = {"登录相关接口"})
@RestController
@RequestMapping("/auth")
public class LoginController {
    @RequestMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public String login(String name){
        return "登陆成功";
    }
}
