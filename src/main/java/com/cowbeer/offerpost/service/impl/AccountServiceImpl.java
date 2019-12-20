package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.constants.RoleType;
import com.cowbeer.offerpost.dao.AccountDao;
import com.cowbeer.offerpost.dao.EnterpriseInfoDao;
import com.cowbeer.offerpost.dao.UserInfoDao;
import com.cowbeer.offerpost.data.command.LoginCommand;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateAccountCommand;
import com.cowbeer.offerpost.data.vo.EnterpriseVO;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.model.EnterpriseInfo;
import com.cowbeer.offerpost.model.UserInfo;
import com.cowbeer.offerpost.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 16:31
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Override
    public Account register(RegisterCommand command, Integer roleType) {
        Account account = command.getAccount();
        if (accountDao.findByEmail(account.getEmail()).isPresent()){
            throw new BadRequestException("该邮箱已注册");
        }
        account.setRole(roleType);
        return accountDao.save(account);
    }

    @Override
    public Account login(LoginCommand command) {
        Optional<Account> accountOptional = accountDao.findByEmail(command.getEmail());
        if (!accountOptional.isPresent())
            throw new BadRequestException("用户不存在");
        Account account = accountOptional.get();
        Account accountVO;
        if (account.getRole().equals(RoleType.ADMIN)) {
            accountVO = new UserVO(account);
        }
        else if (account.getRole().equals(RoleType.ENTERPRISE)) {
            //todo
            Optional<EnterpriseInfo> infoOptional = enterpriseInfoDao.findByAccountId(account.getId());
            if (infoOptional.isPresent())
                accountVO = new EnterpriseVO(account, infoOptional.get());
            else
                accountVO = new EnterpriseVO(account);
        }
        else if (account.getRole().equals(RoleType.GENERAL_USER)){
            Optional<UserInfo> infoOptional = userInfoDao.findByAccountId(account.getId());
            UserInfo userInfo;
            if (!infoOptional.isPresent()){
                userInfo = new UserInfo();
                userInfo.setAccountId(account.getId());
                userInfoDao.save(userInfo);
            } else
                userInfo = infoOptional.get();
            accountVO = new UserVO(account, userInfo);
        }else
            throw new BadRequestException("非法的账号，不存在该身份类型-RoleType:"+account.getRole());
        return accountVO;
    }

    @Override
    public Account update(Long accountId, UpdateAccountCommand command) {
        Optional<Account> accountOptional = accountDao.findById(accountId);
        if (!accountOptional.isPresent())
            throw new BadRequestException("用户不存在");
        Account account = accountOptional.get();
        BeanUtils.copyProperties(command, account);
        return accountDao.save(account);
    }
}
