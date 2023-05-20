package com.chat.intercept;

import cn.hutool.http.HttpStatus;
import com.chat.bean.BaseException;
import com.chat.bean.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
    public BaseResponse handleMyException(Exception ex) {
        log.error("{}", ex);
        if(ex instanceof BaseException){
            BaseException e = (BaseException) ex;
            return new BaseResponse.Builder()
                    .code(e.getCode())
                    .message(e.getMessage())
                    .build();
        }
        return new BaseResponse.Builder()
                .code(HttpStatus.HTTP_INTERNAL_ERROR)
                .data(ex.getMessage())
                .build();
    }

}
