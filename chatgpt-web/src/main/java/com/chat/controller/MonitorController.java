package com.chat.controller;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chat.annotation.ApiMonitor;
import com.chat.bean.BaseResponse;
import com.chat.cst.APIConstant;
import com.chat.emu.ApiEmu;
import com.chat.util.RedisUtil;
import com.chat.util.SentinelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class MonitorController {
    @Autowired
    private RedisUtil redisUtil;

    @ApiMonitor(ApiEmu.CHAT_COUNT)
    @GetMapping(APIConstant.CHAT_COUNT)
    @SentinelResource(value = "chatCount", entryType = EntryType.IN, blockHandler = "monitorExceptionHandler")
    public Mono<BaseResponse<JSONObject>> chatCount() {
        Object count = redisUtil.get(ApiEmu.CHAT.getDescribe());
        JSONObject json = new JSONObject();
        json.set("count", count == null ? 0 : count);
        BaseResponse baseResponse = new BaseResponse.Builder()
                .code(HttpStatus.HTTP_OK)
                .data(json)
                .build();
        return Mono.just(baseResponse);
    }

    public  Mono<BaseResponse<JSONObject>> monitorExceptionHandler (BlockException e){
        log.error("被限流咯");
        BaseResponse baseResponse = new BaseResponse.Builder()
                .code(HttpStatus.HTTP_OK)
                .message("Sentinel流控调用失败")
                .build();
        return Mono.just(baseResponse);
    }



}
