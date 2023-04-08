package com.chat.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRequest extends Request{

    private String model;

    private Messages[] messages;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Messages{
        private String role;
        private String content;
    }


}
