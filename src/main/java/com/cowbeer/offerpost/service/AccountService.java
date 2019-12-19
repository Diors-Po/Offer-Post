package com.cowbeer.offerpost.service;

import com.cowbeer.offerpost.data.command.LoginCommand;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.model.Account;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 16:30
 */
public interface AccountService {
    Account register(RegisterCommand command, Integer roleType);

    Account login(LoginCommand command);
}
