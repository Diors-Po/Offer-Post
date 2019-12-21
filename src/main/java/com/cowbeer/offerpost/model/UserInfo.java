package com.cowbeer.offerpost.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gengder")
    private Integer gender;

    @Column(name = "university")
    private String university;

    @Column(name = "education")
    private Integer education;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;
}
