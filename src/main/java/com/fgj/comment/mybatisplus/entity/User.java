package com.fgj.comment.mybatisplus.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String username;
    private String nickname;
    private String headPhoto;
}
