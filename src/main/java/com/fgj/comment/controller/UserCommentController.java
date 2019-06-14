package com.fgj.comment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fgj.comment.domain.dto.UserCommentDto;
import com.fgj.comment.domain.vo.UserCommentVo;
import com.fgj.comment.mybatisplus.pojo.MPage;
import com.fgj.comment.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/user-comment")
public class UserCommentController {
    @Autowired
    private UserCommentService userCommentService;

    @GetMapping(params = "cardId")
    public IPage<UserCommentVo> findUserCommentsByCardId(@RequestParam("cardId")Integer cardId, MPage page, @AuthenticationPrincipal UserDetails userDetails){
        return userCommentService.findUserComments(page,cardId,userDetails.getUsername());
    }

    @GetMapping(params = "refCommentId")
    public IPage<UserCommentVo> findUserCommentsByRefCommentId(@RequestParam("refCommentId")Integer refCommentId,MPage page,@AuthenticationPrincipal UserDetails userDetails){
        return userCommentService.findRepliedComments(page,refCommentId,userDetails.getUsername());
    }

    @PostMapping
    public void comment(@RequestBody UserCommentDto userCommentDto,
                        @AuthenticationPrincipal UserDetails userDetails){
        String[] sArr = {"fgj","galigeigei","gogogo","iloveyou123","douyu","huya","xiongmao","yy"};
        Integer i = new Random().nextInt(sArr.length);
//        userCommentDto.setUsername(userDetails.getUsername());
        userCommentDto.setUsername(sArr[i]);
        userCommentDto.setLikerNum(0);
        userCommentService.create(userCommentDto);
    }

    @PostMapping("/{id}/liker-num")
    public void like(@PathVariable("id")Integer commentId,
                     @AuthenticationPrincipal UserDetails userDetails){
        userCommentService.addLikerNum(commentId,userDetails.getUsername());
    }

}
