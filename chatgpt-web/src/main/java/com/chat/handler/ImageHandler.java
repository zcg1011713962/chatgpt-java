package com.chat.handler;

import com.chat.bean.ImageRequest;
import com.chat.bean.ImageResponse;
import com.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ImageHandler {
    @Autowired
    private ChatService<ImageResponse> chatService;

    @PostMapping("/v1/images/generations")
    Mono<ImageResponse> createImages(@RequestBody ImageRequest imageRequest) {
        log.info("{}", imageRequest);
        ImageResponse imageResponse= null;
        try {
            imageResponse = chatService.createImages(imageRequest).get();
        } catch (Exception e) {
            log.error("{}", e);
        }
        return Mono.just(imageResponse);
    }


}
