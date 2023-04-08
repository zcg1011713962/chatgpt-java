package com.chat.exception;

import java.io.Serializable;

public class FutureException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -3072387467123480803L;
    private int code = 10000;
    private String message;

    public FutureException(String message) {
        super(message);
    }

    public FutureException(int code, String message) {
        super(message);
        this.code = code;
    }

}
