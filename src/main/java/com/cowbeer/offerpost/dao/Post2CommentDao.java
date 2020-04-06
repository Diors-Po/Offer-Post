package com.cowbeer.offerpost.dao;

import com.cowbeer.offerpost.model.Post2Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2020-04-07 00:05
 */
public interface Post2CommentDao extends PagingAndSortingRepository<Post2Comment, Long> {
}
