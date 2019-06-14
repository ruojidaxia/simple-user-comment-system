package com.fgj.comment.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fgj.comment.domain.vo.UserCommentVo;
import com.fgj.comment.mybatisplus.entity.UserComment;
import com.fgj.comment.mybatisplus.pojo.MPage;
import org.apache.ibatis.annotations.Param;

public interface UserCommentMapper extends BaseMapper<UserComment> {
    IPage<UserCommentVo> selectUserCommentsVo(@Param("page") MPage page, @Param("cardId") Integer cardId, @Param("principleUsername") String username);
    IPage<UserCommentVo> selectRepliedCommentsVo(@Param("page") MPage page, @Param("refCommentId") Integer refCommentId, @Param("principleUsername") String username);
}
