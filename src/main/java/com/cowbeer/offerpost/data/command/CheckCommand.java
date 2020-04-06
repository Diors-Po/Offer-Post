package com.cowbeer.offerpost.data.command;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2020-04-06 23:36
 */
@Data
public class CheckCommand {

    @NotNull(message = "请填写审核未通过原因")
    private String message;
}
