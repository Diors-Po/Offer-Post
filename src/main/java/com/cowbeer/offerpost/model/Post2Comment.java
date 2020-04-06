package com.cowbeer.offerpost.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2020-04-06 23:58
 */
@Data
@Entity(name = "post_2_comment")
public class Post2Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "comment")
    private Long comment;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;
}
