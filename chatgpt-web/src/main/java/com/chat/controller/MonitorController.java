package com.chat.controller;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import com.chat.annotation.ApiMonitor;
import com.chat.bean.BaseResponse;
import com.chat.cst.APIConstant;
import com.chat.emu.ApiEmu;
import com.chat.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
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
    Mono<BaseResponse<JSONObject>> chatCount() {
        Object count = redisUtil.get(ApiEmu.CHAT.getDescribe());
        JSONObject json = new JSONObject();
        json.set("count", count == null ? 0 : count);
        BaseResponse baseResponse = new BaseResponse.Builder()
                .code(HttpStatus.HTTP_OK)
                .data(json)
                .build();
        return Mono.just(baseResponse);
    }
}
