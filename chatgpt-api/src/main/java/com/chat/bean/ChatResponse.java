package com.chat.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatResponse extends Response{

    private Usage usage;
    private Choices[] choices;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Usage{
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Choices{
        private Message Message;
        private String finish_reason;
        private int index;
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        public static class Message{
            private String role;
            private String content;
        }
    }
}
