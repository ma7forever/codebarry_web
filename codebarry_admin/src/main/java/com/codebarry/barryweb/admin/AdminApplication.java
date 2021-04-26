package com.codebarry.barryweb.admin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :cjh
 * @date : 16:35 2021/1/15
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.codebarry.barryweb.admin",
        "com.codebarry.barryweb.xo.service",
        "com.codebarry.barryweb.commons.config",
        "com.codebarry.barryweb.utils"
})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
