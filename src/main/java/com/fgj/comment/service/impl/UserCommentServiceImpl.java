package com.fgj.comment.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fgj.comment.domain.dto.UserCommentDto;
import com.fgj.comment.domain.vo.UserCommentVo;
import com.fgj.comment.exception.NotFoundException;
import com.fgj.comment.mybatisplus.entity.UserComment;
import com.fgj.comment.mybatisplus.entity.UserCommentLike;
import com.fgj.comment.mybatisplus.mapper.UserCommentLikeMapper;
import com.fgj.comment.mybatisplus.mapper.UserCommentMapper;
import com.fgj.comment.mybatisplus.pojo.MPage;
import com.fgj.comment.service.UserCommentService;
import com.fgj.comment.utils.SnakeBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@CacheConfig(cacheNames = "userCommentCache",keyGenerator = "keyGenerator")
public class UserCommentServiceImpl implements UserCommentService {
    @Autowired
    private UserCommentMapper userCommentMapper;
    @Autowired
    private UserCommentLikeMapper userCommentLikeMapper;

    @Override
    @Cacheable
    public IPage<UserCommentVo> findUserComments(MPage page, Integer cardId, String principleUsername) {
        return userCommentMapper.selectUserCommentsVo(page,cardId,principleUsername);
    }

    @Override
    public IPage<UserCommentVo> findRepliedComments(MPage page,Integer refCommentId,String principleUsername) {
        page.setSearchCount(false);
        page.setMysqlBeginOffset(1L);
        return userCommentMapper.selectRepliedCommentsVo(page,refCommentId,principleUsername);
    }

    @Override
    public void create(UserCommentDto userCommentDto) {
        UserComment userComment = SnakeBeanUtils.fromSource(userCommentDto,UserComment.class,"id");
        userCommentMapper.insert(userComment);
    }

    @Override
    public void addLikerNum(Integer commentId,String principleUsername) {
        UserComment userComment = userCommentMapper.selectById(commentId);
        if(Objects.isNull(userComment)){
            throw new NotFoundException();
        }
        Integer likerNum = userComment.getLikerNum();
        userComment.setLikerNum(++likerNum);
        userCommentMapper.updateById(userComment);
        UserCommentLike userCommentLike = new UserCommentLike();
        userCommentLike.setUsername(principleUsername);
        userCommentLike.setCommentId(commentId);
        userCommentLikeMapper.insert(userCommentLike);
    }
}
