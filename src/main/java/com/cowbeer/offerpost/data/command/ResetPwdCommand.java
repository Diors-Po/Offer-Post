package com.cowbeer.offerpost.data.command;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 23:24
 */
@Data
public class ResetPwdCommand {


    @NotNull(message = "原密码不可为空")
    private String oldPassword;

    @NotNull(message = "新密码不可为空")
    @Length(min = 6, max = 16, message = "密码长度需要设为6~16位")
    private String newPassword;

    @NotNull(message = "请填写确认密码")
    @Length(min = 6, max = 16, message = "密码长度需要设为6~16位")
    private String repeatNewPwd;

    public boolean isRepeatEqual(){
        return newPassword.equals(repeatNewPwd);
    }
}
