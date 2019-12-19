package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.dao.AccountDao;
import com.cowbeer.offerpost.dao.UserInfoDao;
import com.cowbeer.offerpost.data.command.RegisterUserCommand;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.model.UserInfo;
import com.cowbeer.offerpost.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:37
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AccountDao accountDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public UserVO registerUser(RegisterUserCommand command) {
        Account account = command.getAccount();
        if (accountDao.findByEmail(account.getEmail()).isPresent()){
            throw new BadRequestException("该邮箱已注册");
        }
        Account saveAccount = accountDao.save(account);
        UserInfo userInfo = new UserInfo();
        userInfo.setAccountId(saveAccount.getId());
        UserInfo saveUserInfo = userInfoDao.save(userInfo);
        return new UserVO(saveAccount,saveUserInfo);
    }
}
