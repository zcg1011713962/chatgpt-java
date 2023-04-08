package com.chat.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelResponse extends Response{

    private Permission permission;
    private String root;
    private Object parent;

    @Data
    public static class Permission{
        private String id;
        private String object;
        private int created;
        private boolean allow_create_engine;
        private boolean allow_sampling;
        private boolean allow_logprobs;
        private boolean allow_search_indices;
        private boolean allow_view;
        private boolean allow_fine_tuning;
        private String organization;
        private Object group;
        private boolean is_blocking;
    }

}
