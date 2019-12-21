package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.constants.CheckStatus;
import com.cowbeer.offerpost.constants.RoleType;
import com.cowbeer.offerpost.dao.AccountDao;
import com.cowbeer.offerpost.dao.EnterpriseInfoDao;
import com.cowbeer.offerpost.data.StatisticsDTO;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateEnterpriseAuthCommand;
import com.cowbeer.offerpost.data.vo.EnterpriseVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.model.EnterpriseInfo;
import com.cowbeer.offerpost.service.AccountService;
import com.cowbeer.offerpost.service.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 22:01
 */
@Slf4j
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDao accountDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Override
    public EnterpriseVO registerEnterprise(RegisterCommand command) {
        return new EnterpriseVO(accountService.register(command, RoleType.ENTERPRISE));
    }

    @Override
    public EnterpriseVO getEnterprise(Long accountId) {
        Optional<Account> accountOptional = accountDao.findById(accountId);
        if (!accountOptional.isPresent())
            throw new BadRequestException("企业不存在");
        Account account = accountOptional.get();
        Optional<EnterpriseInfo> infoOptional = enterpriseInfoDao.findByAccountId(accountId);
        return infoOptional.map(enterpriseInfo -> new EnterpriseVO(account, enterpriseInfo)).orElseGet(() -> new EnterpriseVO(account));
    }

    @Override
    public EnterpriseVO updateEnterpriseAuth(Long accountId, UpdateEnterpriseAuthCommand command) {
        Optional<Account> accountOptional = accountDao.findById(accountId);
        if (!accountOptional.isPresent())
            throw new BadRequestException("企业不存在");
        Optional<EnterpriseInfo> infoOptional = enterpriseInfoDao.findByAccountId(accountId);
        EnterpriseInfo info;
        if (infoOptional.isPresent())
            info = infoOptional.get();
        else {
            info = new EnterpriseInfo();
            info.setAccountId(accountId);
        }
        BeanUtils.copyProperties(command, info);
        info.setAuthStatus(CheckStatus.AUTH_ING);
        return new EnterpriseVO(accountOptional.get(), enterpriseInfoDao.save(info));
    }

    @Override
    public StatisticsDTO getStatisticsInfo(Long accountId) {
        return null;
    }


}
