package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.annotation.LoginRequired;
import com.cowbeer.offerpost.data.command.LoginCommand;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateUserCommand;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.exception.ForbiddenRequestException;
import com.cowbeer.offerpost.exception.UnauthorizedException;
import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.service.AccountService;
import com.cowbeer.offerpost.service.UserService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:27
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户注册接口
     * @param command
     * @param result
     * @return
     */
    @PostMapping(value = "/api/user")
    public UserVO registerUser(@Validated @RequestBody RegisterCommand command, BindingResult result){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        return userService.registerUser(command);
    }

    /**
     * 更新用户信息
     * @param command
     * @param accountId
     * @param session
     * @param result
     * @return
     */
    @LoginRequired
    @PutMapping(value = "/api/user/{accountId}")
    public UserVO updateUser(@Validated @RequestBody UpdateUserCommand command,
                                @PathVariable("accountId") Long accountId,
                                HttpSession session, BindingResult result){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new UnauthorizedException("无权修改他人信息");
        return userService.updateUser(accountId,command);
    }

    /**
     * 获取用户信息
     * @param accountId
     * @return
     */
    @GetMapping(value = "/api/user/{accountId}")
    public UserVO getUserInfo(@PathVariable("accountId") Long accountId){
        return userService.getUser(accountId);
    }


    @LoginRequired
    @PostMapping(value = "/api/user/{accountId}/post/{postId}/resume/{resumeId}")
    public void sendResume(@PathVariable("accountId") Long accountId, @PathVariable("postId") Long postId,
                           @PathVariable("resumeId") Long resumeId, HttpSession session){
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new ForbiddenRequestException("无权操作他人账号");
        log.info("accountId: "+accountId + ", postId: "+postId + " resumeId: "+resumeId);
        userService.sendResume(accountId, postId, resumeId);
    }


}
