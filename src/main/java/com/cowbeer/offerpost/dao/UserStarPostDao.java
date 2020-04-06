package com.cowbeer.offerpost.dao;

import com.cowbeer.offerpost.model.UserStarPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2020-04-07 00:04
 */
public interface UserStarPostDao extends PagingAndSortingRepository<UserStarPost, Long> {
    long countAllByPostId(Long postId);

    Optional<UserStarPost> findByAccountIdAndPostId(Long accountId, Long postId);

    List<UserStarPost> findAllByPostId(Long postId);

    List<UserStarPost> findAllByAccountId(Long accountId);
}
