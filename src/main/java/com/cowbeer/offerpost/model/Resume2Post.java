package com.cowbeer.offerpost.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-14 22:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public Resume2Post(Long accountId, Long postId, Long resumeId){
        this.accountId = accountId;
        this.resumeId = resumeId;
        this.postId = postId;
    }
}
