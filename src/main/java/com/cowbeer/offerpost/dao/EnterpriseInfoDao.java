package com.cowbeer.offerpost.dao;

import com.cowbeer.offerpost.model.EnterpriseInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 11:05
 */
public interface EnterpriseInfoDao extends PagingAndSortingRepository<EnterpriseInfo, Long> {
    List<EnterpriseInfo> findAll();

    Optional<EnterpriseInfo> findByAccountId(Long accountId);


}
