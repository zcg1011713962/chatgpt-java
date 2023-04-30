package com.chat.service.impl;

import cn.hutool.json.JSONUtil;
import com.chat.bean.*;
import com.chat.exception.FutureException;
import com.chat.properties.OpenAIProperties;
import com.chat.service.ChatService;
import com.chat.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ChatServiceImpl<T extends Response> implements ChatService<Response> {
    @Autowired(required = false)
    private OpenAIProperties openAIProperties;
    private static final String AUTHORIZATION = "Authorization";
    private static final String API_COMPLETIONS = "/v1/chat/completions";
    private static final String API_MODELS = "/v1/models/davinci";
    private static final String API_IMAGES_GENERATIONS = "/v1/images/generations";

    @Override
    public CompletableFuture<Response> completions(ChatRequest chatRequest) {
        String parms = JSONUtil.toJsonStr(chatRequest);
        log.info("completions:{}", parms);
        String url = new StringBuffer().append(openAIProperties.getApiBaseUrl()).append(API_COMPLETIONS).toString();
        return HttpUtil.asyncHttpClient().preparePost(url)
                .setHeader(AUTHORIZATION, openAIProperties.getAuthorization())
                .setHeader("Content-Type","application/json")
                .setBody(parms)
                .execute().toCompletableFuture()
                .thenApplyAsync(f ->{
                    String body = f.getResponseBody();
                    log.info("{}", body);
                    Response response = JSONUtil.toBean(body, ChatResponse.class);
                    return response;
                }).exceptionally((e)->{
                    log.error("{}", e);
                    throw new FutureException(e.getMessage());
                });
    }

    @Override
    public CompletableFuture<Response> models() {
        String url = new StringBuffer().append(openAIProperties.getApiBaseUrl()).append(API_MODELS).toString();
        return HttpUtil.asyncHttpClient().prepareGet(url)
                .setHeader(AUTHORIZATION, openAIProperties.getAuthorization())
                .execute().toCompletableFuture()
                .thenApplyAsync(f ->{
                    String body = f.getResponseBody();
                    Response response = JSONUtil.toBean(body, Response.class);
                    return response;
                }).exceptionally((e)->{
                    log.error("{}", e);
                    throw new FutureException(e.getMessage());
                });
    }

    @Override
    public CompletableFuture<Response> createImages(ImageRequest imageRequest) {
        String parms = JSONUtil.toJsonStr(imageRequest);
        log.info("createImages: {}", parms);
        String url = new StringBuffer().append(openAIProperties.getApiBaseUrl()).append(API_IMAGES_GENERATIONS).toString();
        return HttpUtil.asyncHttpClient().preparePost(url)
                .setHeader(AUTHORIZATION, openAIProperties.getAuthorization())
                .setHeader("Content-Type","application/json")
                .setBody(parms)
                .execute().toCompletableFuture()
                .thenApplyAsync(f ->{
                    String body = f.getResponseBody();
                    log.info("{}", body);
                    Response response = JSONUtil.toBean(body, ImageResponse.class);
                    return response;
                }).exceptionally((e)->{
                    log.error("{}", e);
                    throw new FutureException(e.getMessage());
                });
    }

    @Override
    public CompletableFuture<Response> imagesEdits() {
        return null;
    }
}
