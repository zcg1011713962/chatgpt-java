package com.chat;

import com.chat.bean.Response;
import com.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
public class APITest extends BaseTest{
    @Resource
    private ChatService<Response> chatService;

    @Test
    public void test(){
        try {
            Response chat = chatService.models().get();
            log.info("{}", chat);
        } catch (Exception e) {
            log.error("{}", e);
        }

    }

}
