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
@Entity(name = "resume_2_post")
public class Resume2Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "resume_id")
    private Long resumeId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "create_time")
    private Timestamp createTime;
}
