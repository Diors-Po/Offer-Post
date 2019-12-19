package com.cowbeer.offerpost.service;

import com.cowbeer.offerpost.data.command.LoginCommand;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateUserCommand;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.model.Account;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:37
 */
public interface UserService {
    UserVO registerUser(RegisterCommand command);

    UserVO updateUser(Long accountId, UpdateUserCommand command);

    UserVO getUser(Long accountId);
}
