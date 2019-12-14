package com.cowbeer.offerpost.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-14 22:33
 */
@Data
@Entity(name = "enterprise_info")
public class EnterpriseInfo {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "location")
    private String location;

    @Column(name = "credit_code")
    private String creditCode;

    @Column(name = "license_url")
    private String licenseUrl;

    @Column(name = "status")
    private Integer status;

    @Column(name = "update_time")
    private Timestamp updateTime;
}
