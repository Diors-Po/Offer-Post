package com.cowbeer.offerpost.dao;

import com.cowbeer.offerpost.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:10
 */
public interface AccountDao extends PagingAndSortingRepository<Account, Long> {
    List<Account> findAll();
}
