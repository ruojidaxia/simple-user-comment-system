package com.fgj.comment.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_comment_like")
@Data
public class UserCommentLike {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    @TableField("comment_id")
    private Integer commentId;
}
