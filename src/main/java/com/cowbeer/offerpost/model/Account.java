package com.cowbeer.offerpost.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-14 22:32
 */
@Data
@Entity(name = "account")
public class Account {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "role")
    private String role;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}
