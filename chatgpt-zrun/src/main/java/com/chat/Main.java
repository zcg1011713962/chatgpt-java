package com.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(scanBasePackages = {"com.chat.handler","com.chat.router","com.chat.config","com.chat.properties","com.chat.service"})
@EnableWebFlux
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
