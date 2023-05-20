package com.chat.bean;

public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class Builder<T> {
        private BaseResponse<T> response;

        public Builder() {
            response = new BaseResponse();
        }

        public Builder code(int code) {
            response.setCode(code);
            return this;
        }

        public Builder message(String message) {
            response.setMessage(message);
            return this;
        }

        public Builder data(T data) {
            response.setData(data);
            return this;
        }

        public BaseResponse build() {
            return response;
        }
    }
}
