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
 * @date 2020-04-07 00:02
 */
@Data
@Entity(name = "user_star_post")
@NoArgsConstructor
@AllArgsConstructor
public class UserStarPost {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "post_id")
    private Long postId;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public UserStarPost(Long accountId, Long postId){
        this.accountId = accountId;
        this.postId = postId;
    }
}
