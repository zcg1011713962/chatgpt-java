package com.chat.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -9165975143171890506L;
    protected String id;
    protected String object;
    protected int created;
    protected String model;

}
