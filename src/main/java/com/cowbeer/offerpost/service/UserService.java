package com.cowbeer.offerpost.service;

import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateUserCommand;
import com.cowbeer.offerpost.data.vo.PostInfoVO;
import com.cowbeer.offerpost.data.vo.UserVO;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:37
 */
public interface UserService {
    UserVO registerUser(RegisterCommand command);

    UserVO updateUser(Long accountId, UpdateUserCommand command);

    UserVO getUser(Long accountId);

    void sendResume(Long accountId, Long postId, Long resumeId);

    void starPost(Long accountId, Long postId);

    void cancelStarPost(Long accountId, Long postId);

    List<PostInfoVO> getStarPostList(Long accountId);
}
