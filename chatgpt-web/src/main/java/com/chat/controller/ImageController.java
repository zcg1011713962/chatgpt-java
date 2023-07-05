package com.chat.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.chat.annotation.ApiMonitor;
import com.chat.bean.BaseResponse;
import com.chat.bean.ImageRequest;
import com.chat.cst.APIConstant;
import com.chat.emu.ApiEmu;
import com.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ImageController {
    @Autowired
    private ChatService<BaseResponse> chatService;

    @ApiMonitor(ApiEmu.IMAGES_GENERATIONS)
    @PostMapping(APIConstant.IMAGES_GENERATIONS)
    Mono<BaseResponse> createImages(@RequestBody ImageRequest imageRequest) {
        log.info("{}", imageRequest);
        return Mono.fromFuture(chatService.createImages(imageRequest));
    }


}
