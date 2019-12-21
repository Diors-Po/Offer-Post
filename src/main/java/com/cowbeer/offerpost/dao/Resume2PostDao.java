package com.cowbeer.offerpost.dao;

import com.cowbeer.offerpost.model.Resume2Post;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 11:11
 */
public interface Resume2PostDao extends PagingAndSortingRepository<Resume2Post, Long> {
    List<Resume2Post> findByPostId(Long postId);

    List<Resume2Post> findByResumeId(Long resumeId);

    List<Resume2Post> findByAccountId(Long accountId);

    Optional<Resume2Post> findByAccountIdAndPostId(Long accountId, Long postId);
}
