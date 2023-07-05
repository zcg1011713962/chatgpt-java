package com.chat;

import com.chat.bean.ChatRequest;
import com.chat.controller.ChatController;
import com.chat.controller.MonitorController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SentinelTest extends BaseTest{
    @Autowired
    MonitorController monitorController;
    @Autowired
    ChatController chatController;

    @Test
    public void chatCounTest(){
        while (true){
            try {
                monitorController.chatCount();
            }catch (Exception e){
                log.error("{}", e);
                break;
            }

        }
    }

    @Test
    public void chatTest(){
        while (true){
            try {
                chatController.chat(new ChatRequest());
            }catch (Exception e){
                log.error("{}", e);
                break;
            }

        }
    }

}
