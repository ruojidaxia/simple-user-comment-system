package com.fgj.comment.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("user_comment")
@Data
public class UserComment implements Serializable {
    @TableId(type = IdType.AUTO)
    protected Integer id;

    @TableField(value = "card_id")
    private Integer cardId;

    @TableField(value = "username")
    private String username;

    @TableField(value = "comment")
    private String comment;

    @TableField(value = "liker_num")
    private Integer likerNum;

    @TableField(value = "replied_num")
    private Integer repliedNum;

    @TableField(value = "ref_comment_id")
    private Integer refCommentId;

    @TableField(value = "ref_username")
    private String refUsername;

    @TableField(value = "created_date",fill = FieldFill.INSERT,update = "now()")
    private Date createdDate;
}
