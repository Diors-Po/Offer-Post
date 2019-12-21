package com.cowbeer.offerpost.data.command;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-20 09:47
 */
@Data
public class UpdateEnterpriseAuthCommand {

    @NotNull(message = "企业名称不可为空")
    private String enterpriseName;
    @NotNull(message = "地址不可为空")
    private String location;
    @NotNull(message = "描述不可为空")
    private String description;
    @NotNull(message = "社会统一信用码不可为空")
    private String creditCode;
    @NotNull(message = "请上传营业执照扫描件")
    private String licenseUrl;
}
