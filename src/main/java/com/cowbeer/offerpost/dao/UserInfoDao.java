package com.cowbeer.offerpost.dao;

import com.cowbeer.offerpost.model.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 11:12
 */
public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, Long> {
    List<UserInfo> findAll();

    Optional<UserInfo> findByAccountId(Long accountId);
}
