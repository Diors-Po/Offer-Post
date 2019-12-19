package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.data.command.RegisterUserCommand;
import com.cowbeer.offerpost.data.command.UpdateAccountCommand;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:27
 */
@RestController
public class AccountController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/api/account/user")
    public UserVO registerUser(@Validated @RequestBody RegisterUserCommand command, BindingResult result){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        return userService.registerUser(command);
    }

    @PutMapping(value = "/api/account/{accountId}")
    public UserVO updateAccount(@Validated @RequestBody UpdateAccountCommand command,
                                @PathVariable("accountId") Long accountId, BindingResult result){

        return null;
    }

    @PutMapping(value = "/api/account/user/info")
    public UserVO updateUserInfo(){
        return null;
    }

}
