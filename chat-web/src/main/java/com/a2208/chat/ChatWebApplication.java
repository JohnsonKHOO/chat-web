package com.a2208.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.a2208.chat.dao")
@SpringBootApplication
public class ChatWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatWebApplication.class, args);
    }

}
