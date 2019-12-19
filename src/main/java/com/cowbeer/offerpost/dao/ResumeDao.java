package com.cowbeer.offerpost.dao;

import com.cowbeer.offerpost.model.Resume;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 11:10
 */
public interface ResumeDao extends PagingAndSortingRepository<Resume, Long> {
    List<Resume> findAll();

    List<Resume> findByAccountId(Long accountId);
}
