package com.cowbeer.offerpost.data.command;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 15:44
 */
@Data
public class LoginCommand {

    @NotNull(message = "邮箱不可为空")
    private String email;

    @NotNull(message = "密码不可为空")
    private String password;
}
