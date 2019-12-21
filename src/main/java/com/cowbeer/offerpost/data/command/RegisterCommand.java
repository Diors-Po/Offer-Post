package com.cowbeer.offerpost.data.command;

import com.cowbeer.offerpost.constants.CheckStatus;
import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.utils.EncryptUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 12:00
 */
@Data
public class RegisterCommand {

    @NotNull(message = "用户名不可为空")
    private String name;

    @NotNull(message = "邮箱不可为空")
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式非法")
    private String email;

    @NotNull(message = "密码不可为空")
    @Length(min = 6, max = 16, message = "密码长度需要设为6~16位")
    private String password;

    @Pattern(regexp = "^1[3|4|5|7|8][0-9]\\d{4,8}$", message = "手机号非法")
    private String mobile;

    private String avatar;

    public Account getAccount(){
        Account account = new Account();
        BeanUtils.copyProperties(this, account, "password");
        account.setPassword(EncryptUtil.encryptMD5(this.password));
        account.setRole(2);
        account.setStatus(CheckStatus.ACCOUNT_AVAILABLE);
        return account;
    }

}
