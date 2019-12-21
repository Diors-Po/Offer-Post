package com.cowbeer.offerpost.service;

import com.cowbeer.offerpost.data.command.LoginCommand;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.ResetPwdCommand;
import com.cowbeer.offerpost.data.command.UpdateAccountCommand;
import com.cowbeer.offerpost.data.vo.EnterpriseVO;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.model.Account;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 16:30
 */
public interface AccountService {
    Account register(RegisterCommand command, Integer roleType);

    Account login(LoginCommand command);

    Account update(Long accountId, UpdateAccountCommand command);

    Account updateAvatar(Long accountId, String data);

    List<UserVO> getAllUsers();

    List<EnterpriseVO> getAllAuthPassedEnterprise();

    void resetPwd(Long accountId, ResetPwdCommand command);
}
