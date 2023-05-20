package com.chat.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisPoolProperties {
    private String host;
    private int port;
    private String password;
    private int database;

}
