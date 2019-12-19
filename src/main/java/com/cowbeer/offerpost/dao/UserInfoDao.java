package com.cowbeer.offerpost.dao;

import com.cowbeer.offerpost.model.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 11:12
 */
public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, Long> {
    List<UserInfo> findAll();

    List<UserInfo> findByAccountId(Long accountId);
}
