package com.chat.controller;

import com.chat.annotation.ApiMonitor;
import com.chat.bean.BaseResponse;
import com.chat.bean.ChatRequest;
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
public class ChatController {
    @Autowired
    private ChatService<BaseResponse> chatService;

    @ApiMonitor(ApiEmu.CHAT)
    @PostMapping(APIConstant.CHAT)
    Mono<BaseResponse> chat(@RequestBody ChatRequest chatRequest) {
        log.info("{}", chatRequest);
        return Mono.fromFuture(chatService.completions(chatRequest));
    }




}
