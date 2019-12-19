package com.cowbeer.offerpost.data.command;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 13:49
 */
@Data
public class UpdateAccountCommand {


    @NotNull(message = "用户名不可为空")
    private String name;

    @Pattern(regexp = "^1[3|4|5|7|8][0-9]\\d{4,8}$", message = "手机号非法")
    private String mobile;

    private String avatar;
}
