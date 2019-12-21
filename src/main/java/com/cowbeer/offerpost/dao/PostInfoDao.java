package com.cowbeer.offerpost.dao;

import com.cowbeer.offerpost.model.PostInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 11:08
 */
public interface PostInfoDao extends PagingAndSortingRepository<PostInfo, Long> {
    List<PostInfo> findAll();

    List<PostInfo> findAllByStatus(Integer status);

    List<PostInfo> findByAccountIdAndStatus(Long accountId, Integer status);
}
