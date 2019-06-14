package com.fgj.comment.domain.dto;

import lombok.Data;

@Data
public class UserCommentDto {
    private Integer id;
    private Integer cardId;
    private String username;
    private String comment;
    private Integer likerNum;
    private Integer refCommentId;
    private String refUsername;
}
