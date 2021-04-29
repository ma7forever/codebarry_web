package com.codebarry.barryweb.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author :cjh
 * @date : 16:35 2021/1/15
 */
@EnableOpenApi
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.codebarry.barryweb.commons.config",
        "com.codebarry.barryweb.utils",
        "com.codebarry.barryweb.admin",
        "com.codebarry.barryweb.xo",
        "com.codebarry.barryweb.base"
})
@MapperScan(basePackages = "com.codebarry.barryweb.xo.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
