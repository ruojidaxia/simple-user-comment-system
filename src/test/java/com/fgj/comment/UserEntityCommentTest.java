package com.fgj.comment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fgj.comment.domain.dto.UserCommentDto;
import com.fgj.comment.mybatisplus.entity.UserComment;
import com.fgj.comment.mybatisplus.mapper.UserCommentMapper;
import com.fgj.comment.service.UserCommentService;
import com.fgj.comment.utils.UserCommentUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserEntityCommentTest {
    @Autowired
    UserCommentService userCommentService;
    @Autowired
    UserCommentMapper userCommentMapper;
    Random random = new Random();

    public void createUserComment(){
        String[] sArr = {"fgj","galigeigei","gogogo","iloveyou123","douyu","huya","xiongmao","yy"};
        Integer i = new Random().nextInt(sArr.length);
        UserCommentDto userCommentDto = new UserCommentDto();
        userCommentDto.setCardId(1);
        userCommentDto.setComment(UserCommentUtil.generateComment());
        userCommentDto.setUsername(sArr[i]);
        userCommentDto.setLikerNum(0);
        userCommentService.create(userCommentDto);
    }
    public void createReplyComment(Integer refCommentId,String refUsername){
        String[] sArr = {"fgj","galigeigei","gogogo","iloveyou123","douyu","huya","xiongmao","yy"};
        Integer i = new Random().nextInt(sArr.length);
        UserCommentDto userCommentDto = new UserCommentDto();
        userCommentDto.setCardId(1);
        userCommentDto.setComment(UserCommentUtil.generateComment());
        userCommentDto.setRefCommentId(refCommentId);
        userCommentDto.setRefUsername(refUsername);
        userCommentDto.setUsername(sArr[i]);
        userCommentDto.setLikerNum(0);
        userCommentService.create(userCommentDto);
    }
    @Test
    public void testCreateUserComment(){
        for(int i=0;i<10000;i++){
            createUserComment();
        }
    }
    @Test
    public void testCreateReplyUserComment(){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.isNull("ref_comment_id");
        List<UserComment> userCommentList = userCommentMapper.selectList(wrapper);
        for(int i=0;i<10000;i++){
            UserComment userComment = userCommentList.get(random.nextInt(userCommentList.size()));
            createReplyComment(userComment.getId(),null);
        }
    }
    @Test
    public void synReplyNum(){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.isNull("ref_comment_id");
        List<UserComment> userCommentList = userCommentMapper.selectList(wrapper);
        System.out.println(userCommentList.size());
        userCommentList.forEach(item->{
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("ref_comment_id",item.getId());
            Integer i = userCommentMapper.selectCount(queryWrapper);
            item.setRepliedNum(i);
            userCommentMapper.updateById(item);
        });
    }
    @Test
    public void test4(){
        for(int i=0;i<10*1000;i++){
            IPage<UserComment> page = userCommentMapper.selectPage(new Page<>(i+1,1000),null);
            if(CollectionUtils.isEmpty(page.getRecords()))break;
            page.getRecords().forEach(item->{
                item.setId(null);
                item.setCardId(2);
                userCommentMapper.insert(item);
            });
        }
    }
}
