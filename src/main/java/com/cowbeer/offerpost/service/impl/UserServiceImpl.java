package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.constants.RoleType;
import com.cowbeer.offerpost.dao.AccountDao;
import com.cowbeer.offerpost.dao.UserInfoDao;
import com.cowbeer.offerpost.data.command.LoginCommand;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateUserCommand;
import com.cowbeer.offerpost.data.vo.EnterpriseVO;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.model.UserInfo;
import com.cowbeer.offerpost.service.AccountService;
import com.cowbeer.offerpost.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Autowired
    AccountService accountService;

    @Override
    public UserVO registerUser(RegisterCommand command) {
        Account saveAccount = accountService.register(command, RoleType.GENERAL_USER);
        UserInfo userInfo = new UserInfo();
        userInfo.setAccountId(saveAccount.getId());
        UserInfo saveUserInfo = userInfoDao.save(userInfo);
        return new UserVO(saveAccount,saveUserInfo);
    }

    @Override
    public UserVO updateUser(Long accountId, UpdateUserCommand command) {
        Optional<Account> accountOptional = accountDao.findById(accountId);
        if (!accountOptional.isPresent())
            throw new BadRequestException("用户不存在");
        Account account = accountOptional.get();
        BeanUtils.copyProperties(command, account);
        UserInfo userInfo;
        Optional<UserInfo> infoOptional = userInfoDao.findByAccountId(accountId);
        if (!infoOptional.isPresent()){
            userInfo = new UserInfo();
            userInfo.setAccountId(accountId);
        }else
            userInfo = infoOptional.get();
        BeanUtils.copyProperties(command, userInfo);
        return new UserVO(accountDao.save(account), userInfoDao.save(userInfo));
    }

    @Override
    public UserVO getUser(Long accountId) {
        Optional<Account> accountOptional = accountDao.findById(accountId);
        Optional<UserInfo> infoOptional = userInfoDao.findByAccountId(accountId);
        if (!accountOptional.isPresent())
            throw new BadRequestException("用户不存在");
        Account account = accountOptional.get();
        if (!infoOptional.isPresent()){
            UserInfo userInfo = new UserInfo();
            userInfo.setAccountId(accountId);
            userInfoDao.save(userInfo);
            infoOptional = userInfoDao.findByAccountId(accountId);
        }
        return new UserVO(account, infoOptional.get());
    }

}
