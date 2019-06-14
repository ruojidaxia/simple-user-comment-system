package com.fgj.comment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fgj.comment.domain.dto.UserCommentDto;
import com.fgj.comment.domain.vo.UserCommentVo;
import com.fgj.comment.mybatisplus.pojo.MPage;

public interface UserCommentService{
    IPage<UserCommentVo> findUserComments(MPage page, Integer cardId, String principleUsername);
    IPage<UserCommentVo> findRepliedComments(MPage page, Integer refCommentId, String principleUsername);
    void create(UserCommentDto userCommentDto);
    void addLikerNum(Integer commentId,String principleUsername);
}
