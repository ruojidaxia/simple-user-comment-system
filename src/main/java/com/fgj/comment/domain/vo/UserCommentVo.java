package com.fgj.comment.domain.vo;

import com.fgj.comment.mybatisplus.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
public class UserCommentVo implements Serializable {
    protected Integer id;
    private Integer cardId;
    private Integer refCommentId;
    private User user;
    private User refUser;
    private String comment;
    private Integer likerNum;
    private Integer repliedNum;
    private Boolean liked;
    private Date createdDate;
    private List<UserCommentVo> repliedComments;
}
