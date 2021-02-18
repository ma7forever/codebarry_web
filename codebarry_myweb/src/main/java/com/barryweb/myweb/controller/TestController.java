package com.barryweb.myweb.controller;

import com.codebarry.barryweb.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :cjh
 * @date : 16:46 2021/2/18
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisUtil redisUtil;
    @RequestMapping("/redis")
    public String testRedis(){
        redisUtil.set("1","2");
        System.out.println(redisUtil.get("1"));
        return "结束";
    }
}
