package com.chat.handler;

import com.chat.bean.ChatRequest;
import com.chat.bean.ChatResponse;
import com.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class ChatHandler {
    @Autowired
    private ChatService<ChatResponse> chatService;

    @PostMapping("/v1/chat/completions")
    Mono<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        log.info("{}", chatRequest);
        ChatResponse chatResponse= null;
        try {
            chatResponse = chatService.completions(chatRequest).get();
        } catch (Exception e) {
           log.error("{}", e);
        }
        return Mono.just(chatResponse);
    }

}
