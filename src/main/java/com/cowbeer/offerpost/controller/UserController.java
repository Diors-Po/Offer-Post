package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.dao.AccountDao;
import com.cowbeer.offerpost.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:27
 */
@RestController
public class UserController {
    @Autowired
    AccountDao accountDao;

    @GetMapping(value = "/test")
    public List<Account> testDao(){
        return accountDao.findAll();
    }

}
