package com.chat.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageResponse extends Response{

    private Datas [] data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Datas{
        private String url;
    }
}
