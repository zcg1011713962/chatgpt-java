package com.chat.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageRequest extends Request{
    // 描述
    private String prompt;
    // 生成图片n张
    private int n;
    // 图片尺寸 '256x256', '512x512', '1024x1024'
    private String size = "1024x1024";


}
