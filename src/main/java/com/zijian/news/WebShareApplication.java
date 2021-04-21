package com.zijian.news;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@MapperScan("com.zijian.news.mapper")
public class WebShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebShareApplication.class, args);
    }

    /**
     * Spring Security提供的加密工具，不需要用到SecurityAutoConfiguration，已经在application.properties中排除
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
