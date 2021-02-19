package com.codebarry.barryweb.myweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :cjh
 * @date : 16:41 2021/2/18
 */
@ComponentScan(value= "com.codebarry.barryweb")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class WebApplication {
        public static void main(String[] args) {
                SpringApplication.run(WebApplication.class, args);
        }
}
