package com.cowbeer.offerpost.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-14 22:33
 */
@Data
@Entity(name = "post_info")
public class PostInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "enterprise_id")
    private Long enterpriseId;

    @Column(name = "description")
    private String description;

    @Column(name = "work_location")
    private String workLocation;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "head_count")
    private Integer headCount;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;



}
