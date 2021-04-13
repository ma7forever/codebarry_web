package com.codebarry.barryweb.myweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :cjh
 * @date : 16:41 2021/2/18
 */

@MapperScan("com.codebarry.barryweb.xo.mapper*")
@ComponentScan(basePackages = {
        "com.codebarry.barryweb.commons",
        "com.codebarry.barryweb.utils",
        "com.codebarry.barryweb.myweb",
        "com.codebarry.barryweb.xo.service"
                })
@SpringBootApplication
public  class   WebApplication {
        public static void main(String[] args) {
                SpringApplication.run(WebApplication.class, args);
        }
}
