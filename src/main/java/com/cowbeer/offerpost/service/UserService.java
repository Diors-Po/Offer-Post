package com.cowbeer.offerpost.service;

import com.cowbeer.offerpost.data.command.RegisterUserCommand;
import com.cowbeer.offerpost.data.vo.UserVO;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:37
 */
public interface UserService {
    UserVO registerUser(RegisterUserCommand command);
}
