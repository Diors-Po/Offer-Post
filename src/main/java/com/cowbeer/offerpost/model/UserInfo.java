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
@Entity(name = "user_info")
public class UserInfo {

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "account_id")
    Long accountId;

    @Column(name = "age")
    Integer age;

    @Column(name = "gengder")
    Integer gender;

    @Column(name = "university")
    String university;

    @Column(name = "education")
    Integer education;

    @Column(name = "update_time")
    Timestamp updateTime;
}
