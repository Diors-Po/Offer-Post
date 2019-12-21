package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.annotation.LoginRequired;
import com.cowbeer.offerpost.data.command.PostCommand;
import com.cowbeer.offerpost.data.vo.PostInfoVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.exception.ForbiddenRequestException;
import com.cowbeer.offerpost.model.PostInfo;
import com.cowbeer.offerpost.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 10:53
 */
@Slf4j
@RestController
public class PostInfoController {

    @Autowired
    PostService postService;

    @LoginRequired
    @PostMapping(value = "/api/enterprise/{accountId}/post")
    public PostInfoVO addPostInfo(@Validated @RequestBody PostCommand command, @PathVariable("accountId") Long accountId
            , BindingResult result, HttpSession session){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new ForbiddenRequestException("无权操作他人账号");
        return postService.addPostInfo(accountId, command);
    }

    @GetMapping(value = "/api/enterprise/post/{postId}")
    public PostInfoVO getPostById(@PathVariable("postId") Long postId){
        return postService.getPostInfoById(postId);
    }
}
