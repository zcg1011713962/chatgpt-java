package com.chat.intercept;

import cn.hutool.http.HttpStatus;
import com.chat.annotation.ApiMonitor;
import com.chat.bean.BaseException;
import com.chat.cst.APIConstant;
import com.chat.util.RedisUtil;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiMonitorAspect {
    // 每秒最多可以执行多少次
    private RateLimiter rateLimiter = RateLimiter.create(APIConstant.DEFAULT_RATE_LIMITER);
    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.chat.annotation.ApiMonitor)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            ApiMonitor apiMonitor = signature.getMethod().getAnnotation(ApiMonitor.class);
            if(apiMonitor != null){
                redisUtil.increment(apiMonitor.value().getDescribe(), 1);
            }
            if(rateLimiter.tryAcquire()){
                result = joinPoint.proceed();
            }else{
                throw new BaseException(HttpStatus.HTTP_FORBIDDEN, "访问频繁,请稍后再试");
            }
        } catch (Throwable e) {
            throw e;
        }
        return result;
    }


}
