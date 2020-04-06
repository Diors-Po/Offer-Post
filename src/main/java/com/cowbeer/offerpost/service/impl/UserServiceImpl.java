package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.constants.CheckStatus;
import com.cowbeer.offerpost.constants.RoleType;
import com.cowbeer.offerpost.dao.*;
import com.cowbeer.offerpost.data.command.LoginCommand;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateUserCommand;
import com.cowbeer.offerpost.data.vo.EnterpriseVO;
import com.cowbeer.offerpost.data.vo.PostInfoVO;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.model.*;
import com.cowbeer.offerpost.service.AccountService;
import com.cowbeer.offerpost.service.PostService;
import com.cowbeer.offerpost.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    Resume2PostDao resume2PostDao;

    @Autowired
    PostInfoDao postInfoDao;

    @Autowired
    ResumeDao resumeDao;

    @Autowired
    AccountService accountService;

    @Autowired
    UserStarPostDao userStarPostDao;

    @Autowired
    PostService postService;

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

    @Override
    public void sendResume(Long accountId, Long postId, Long resumeId) {
        if (resume2PostDao.findByAccountIdAndPostId(accountId, postId).isPresent())
            throw new BadRequestException("请勿重复投递");
        if (!accountDao.findById(accountId).isPresent())
            throw new BadRequestException("不存在的用户");
        if (!resumeDao.findById(resumeId).isPresent())
            throw new BadRequestException("简历不存在");
        Optional<PostInfo> postInfoOptional = postInfoDao.findById(postId);
        if (!postInfoOptional.isPresent())
            throw new BadRequestException("该招聘不存在");
        else if (!postInfoOptional.get().getStatus().equals(CheckStatus.AUTH_PASS))
            throw new BadRequestException("该招聘暂未通过审核，不可投递");
        else if (postInfoOptional.get().getStartTime().after(new Timestamp(System.currentTimeMillis())))
            throw new BadRequestException("该招聘未开放投递");
        else if (postInfoOptional.get().getEndTime().before(new Timestamp(System.currentTimeMillis())))
            throw new BadRequestException("该招聘已截止");

        Resume2Post resume2Post = new Resume2Post(accountId, postId, resumeId);
        resume2PostDao.save(resume2Post);
    }

    @Override
    public void starPost(Long accountId, Long postId) {
        if (userStarPostDao.findByAccountIdAndPostId(accountId, postId).isPresent())
            throw new BadRequestException("请勿重复收藏");
        if (!accountDao.findById(accountId).isPresent())
            throw new BadRequestException("不存在的用户");
        Optional<PostInfo> postInfoOptional = postInfoDao.findById(postId);
        if (!postInfoOptional.isPresent())
            throw new BadRequestException("该招聘不存在");
        UserStarPost userStarPost = new UserStarPost(accountId, postId);
        userStarPostDao.save(userStarPost);
    }

    @Override
    public void cancelStarPost(Long accountId, Long postId) {
        Optional<UserStarPost> userStarPost = userStarPostDao.findByAccountIdAndPostId(accountId, postId);
        if (!userStarPost.isPresent())
            throw new BadRequestException("未收藏该招聘");
        userStarPostDao.delete(userStarPost.get());
    }

    @Override
    public List<PostInfoVO> getStarPostList(Long accountId) {
        if (!accountDao.findById(accountId).isPresent())
            throw new BadRequestException("不存在的用户");
        return userStarPostDao.findAllByAccountId(accountId).stream()
                .map(userStarPost -> postService.getPostInfoById(userStarPost.getPostId())).collect(Collectors.toList());
    }

}
