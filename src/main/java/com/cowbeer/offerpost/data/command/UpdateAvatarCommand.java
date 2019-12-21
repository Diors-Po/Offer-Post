package com.cowbeer.offerpost.data.command;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 12:55
 */
@Data
public class UpdateAvatarCommand {
    private Long accountId;

    @NotNull(message = "不可上传空数据")
    private String base64str;
}
