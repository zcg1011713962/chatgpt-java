package com.chat.router;

import com.chat.handler.ChatHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResquestRouter {
    @Autowired
    private ChatHandler chatHandler;

}
