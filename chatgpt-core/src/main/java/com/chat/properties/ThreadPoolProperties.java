package com.chat.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolProperties {
    /**
     * 核心线程数
     */
    private int corePoolSize = 10;
    /**
     * 最大线程数
     */
    private int maxPoolSize = 200;
    /**
     * 队列容量
     */
    private int queueCapacity = 200;
    /**
     * 活跃时间
     */
    private int keepAliveSeconds = 60;
}
