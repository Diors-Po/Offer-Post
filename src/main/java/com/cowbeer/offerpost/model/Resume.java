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
@Entity(name = "resume")
public class Resume {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "name")
    private String name;

    @Column(name = "annex_url")
    private String annexUrl;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp update_time;
}
