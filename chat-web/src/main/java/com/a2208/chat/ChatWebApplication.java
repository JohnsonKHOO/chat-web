package com.a2208.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.a2208.chat.dao")
@SpringBootApplication
@ServletComponentScan
public class ChatWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatWebApplication.class, args);
    }

}
