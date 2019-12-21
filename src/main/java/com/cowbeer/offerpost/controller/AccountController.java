package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.annotation.LoginRequired;
import com.cowbeer.offerpost.data.command.LoginCommand;
import com.cowbeer.offerpost.data.command.UpdateAccountCommand;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.exception.ForbiddenRequestException;
import com.cowbeer.offerpost.exception.UnauthorizedException;
import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 16:46
 */
@Slf4j
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping(value = "/api/login")
    public Account login(@Validated @RequestBody LoginCommand command, HttpSession session){
        Account account = accountService.login(command);
        session.setAttribute("accountId", account.getId());
        log.info("登录成功: " + account.getId());
        return account;
    }

    @LoginRequired
    @PutMapping(value = "/api/account/{accountId}")
    public Account updateAccount(@Validated @RequestBody UpdateAccountCommand command, @PathVariable("accountId") Long accountId,
                                 BindingResult result, HttpSession session){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new UnauthorizedException("无权修改他人信息");
        return accountService.update(accountId, command);
    }

    @LoginRequired
    @PutMapping(value = "/api/account/{accountId}/avatar")
    public Account updateAvatar(@PathVariable("accountId") Long accountId, @RequestBody String data, HttpSession session){
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new ForbiddenRequestException("无权修改他人信息");
        return accountService.updateAvatar(accountId, data);
    }
}
