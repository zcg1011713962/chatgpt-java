package com.chat.util;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

import javax.net.ssl.SSLException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class HttpUtil implements ThreadFactory {
    private final AtomicInteger mThreadNum = new AtomicInteger(1);
    private static AsyncHttpClient asyncHttpClient;
    private static final HttpUtil HTTP_UTIL = new HttpUtil();
    private static final String HTTP_THREAD_NAME = "http_thread_name";

    static {
        init();
    }

    private static void init() {
        asyncHttpClient = build(config()
                .setMaxRequestRetry(3)
                .setRequestTimeout(60_000)
                .setReadTimeout(60_000)
                .setKeepAlive(true)
                .setConnectTimeout(60_000)
                /*.setEventLoopGroup(new NioEventLoopGroup(2*4))*/
                .setThreadFactory(HTTP_UTIL)
        );
    }

    private static AsyncHttpClient build(DefaultAsyncHttpClientConfig.Builder configBuilder) {
        return new DefaultAsyncHttpClient(configBuilder.build());
    }

    private static DefaultAsyncHttpClientConfig.Builder config() {
        try {
            // 在 AsyncHttpClient 中添加 HTTPS 支持
            // 默认情况下，AsyncHttpClient 将验证 SSL 证书。如果您要使用不受信任的证书请使用以下代码
            return new DefaultAsyncHttpClientConfig.Builder()
                    .setSslContext(SslContextBuilder.forClient()
                            .trustManager(InsecureTrustManagerFactory.INSTANCE).build());
        } catch (SSLException e) {
            log.error("{}", e);
        }
        return null;
    }


    public static AsyncHttpClient asyncHttpClient() {
        return asyncHttpClient;
    }

    @Override
    public Thread newThread(Runnable runable) {
        Thread thread = new Thread(runable, HTTP_THREAD_NAME + mThreadNum.incrementAndGet());
        return thread;
    }



}
