package com.chat.controller;

import cn.hutool.http.HttpStatus;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class ChatController{
    @Autowired
    private ChatService<BaseResponse> chatService;

    @ApiMonitor(ApiEmu.CHAT_COUNT)
    @PostMapping(APIConstant.CHAT)
    @SentinelResource(value = "chat", entryType = EntryType.IN, blockHandler = "chatExceptionHandler")
    public Mono<BaseResponse> chat(@RequestBody ChatRequest chatRequest) {
        log.info("{}", chatRequest);
        return Mono.fromFuture(chatService.completions(chatRequest));
    }


    public Mono<BaseResponse> chatExceptionHandler (ChatRequest chatRequest, BlockException e){
        log.error("被限流咯");
        BaseResponse baseResponse = new BaseResponse.Builder()
                .code(HttpStatus.HTTP_OK)
                .message("Sentinel流控调用失败")
                .build();
        return Mono.fromFuture(CompletableFuture.completedFuture(baseResponse));
    }

}
