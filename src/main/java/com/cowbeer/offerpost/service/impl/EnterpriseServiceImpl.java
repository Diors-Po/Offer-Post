package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.constants.CheckStatus;
import com.cowbeer.offerpost.constants.RoleType;
import com.cowbeer.offerpost.dao.*;
import com.cowbeer.offerpost.data.PostInfoDTO;
import com.cowbeer.offerpost.data.StatisticsDTO;
import com.cowbeer.offerpost.data.command.CheckCommand;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateEnterpriseAuthCommand;
import com.cowbeer.offerpost.data.vo.EnterpriseManagePostVO;
import com.cowbeer.offerpost.data.vo.EnterpriseVO;
import com.cowbeer.offerpost.data.vo.UserResumeVO;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.model.EnterpriseInfo;
import com.cowbeer.offerpost.model.PostInfo;
import com.cowbeer.offerpost.service.AccountService;
import com.cowbeer.offerpost.service.EnterpriseService;
import com.cowbeer.offerpost.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    PostInfoDao postInfoDao;

    @Autowired
    ResumeDao resumeDao;

    @Autowired
    Resume2PostDao resume2PostDao;

    @Autowired
    UserInfoDao userInfoDao;

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
    public List<EnterpriseVO> getCheckedEnterprises() {
        List<EnterpriseInfo> enterpriseInfos = enterpriseInfoDao.findByAuthStatusOrAuthStatus(CheckStatus.AUTH_PASS, CheckStatus.AUTH_FAIL);
        return getEnterpriseVOS(enterpriseInfos);
    }

    @Override
    public List<EnterpriseVO> getUncheckEnterprises() {
        List<EnterpriseInfo> enterpriseInfos = enterpriseInfoDao.findByAuthStatus(CheckStatus.AUTH_ING);
        return getEnterpriseVOS(enterpriseInfos);
    }

    private List<EnterpriseVO> getEnterpriseVOS(List<EnterpriseInfo> enterpriseInfos) {
        enterpriseInfos.sort(Comparator.comparing(EnterpriseInfo::getUpdateTime));
        return enterpriseInfos.stream().map(enterpriseInfo -> {
            EnterpriseVO enterpriseVO = new EnterpriseVO();
            enterpriseVO.setEnterpriseInfo(enterpriseInfo);
            Optional<Account> accountOptional = accountDao.findById(enterpriseInfo.getAccountId());
            accountOptional.ifPresent(enterpriseVO::setAccount);
            return enterpriseVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<EnterpriseVO> getPassedEnterprises() {
        List<EnterpriseInfo> enterpriseInfos = enterpriseInfoDao.findByAuthStatus(CheckStatus.AUTH_PASS);
        return getEnterpriseVOS(enterpriseInfos);
    }

    @Override
    public List<EnterpriseVO> getFailedEnterprises() {
        List<EnterpriseInfo> enterpriseInfos = enterpriseInfoDao.findByAuthStatus(CheckStatus.AUTH_FAIL);
        return getEnterpriseVOS(enterpriseInfos);
    }

    @Override
    public List<EnterpriseVO> getAllEnterprise() {
        List<EnterpriseInfo> enterpriseInfos = enterpriseInfoDao.findAll();
        return getEnterpriseVOS(enterpriseInfos);
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

    @Override
    public EnterpriseVO passEnterpriseAuth(Long accountId) {
        Optional<EnterpriseInfo> infoOptional = enterpriseInfoDao.findByAccountId(accountId);
        if (!infoOptional.isPresent())
            throw new BadRequestException("该账号未提交企业认证信息");
        EnterpriseInfo info = infoOptional.get();
        info.setAuthStatus(CheckStatus.AUTH_PASS);
        info.setCheckMessage("审核通过");
        return new EnterpriseVO(accountDao.findById(accountId).get(), enterpriseInfoDao.save(info));
    }

    @Override
    public EnterpriseVO rejectEnterpriseAuth(Long accountId, CheckCommand checkCommand) {
        Optional<EnterpriseInfo> infoOptional = enterpriseInfoDao.findByAccountId(accountId);
        if (!infoOptional.isPresent())
            throw new BadRequestException("该账号未提交企业认证信息");
        EnterpriseInfo info = infoOptional.get();
        info.setAuthStatus(CheckStatus.AUTH_FAIL);
        info.setCheckMessage(checkCommand.getMessage());
        return new EnterpriseVO(accountDao.findById(accountId).get(), enterpriseInfoDao.save(info));
    }

    @Override
    public PostInfoDTO getPostInfoList(Long accountId) {
        PostInfoDTO postInfoDTO = new PostInfoDTO();
        List<PostInfo> authIngPost = postInfoDao.findByAccountIdAndStatus(accountId, CheckStatus.AUTH_ING);
        List<PostInfo> authFailedPost = postInfoDao.findByAccountIdAndStatus(accountId, CheckStatus.AUTH_FAIL);
        List<PostInfo> authPassPost = postInfoDao.findByAccountIdAndStatus(accountId, CheckStatus.AUTH_PASS);


        postInfoDTO.setAuthIng(processEnterpriseManagePostVO(authIngPost));
        postInfoDTO.setAuthFailed(processEnterpriseManagePostVO(authFailedPost));
        List<EnterpriseManagePostVO> authPassPostVO = processEnterpriseManagePostVO(authPassPost);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        postInfoDTO.setNotStarted(authPassPostVO.stream()
                .filter(enterpriseManagePostVO -> enterpriseManagePostVO.getStartTime().after(currentTime))
                .collect(Collectors.toList()));
        postInfoDTO.setProcessing(authPassPostVO.stream()
                .filter(enterpriseManagePostVO -> enterpriseManagePostVO.getStartTime().before(currentTime)&&enterpriseManagePostVO.getEndTime().after(currentTime))
                .collect(Collectors.toList()));
        postInfoDTO.setEnded(authPassPostVO.stream()
                .filter(enterpriseManagePostVO -> enterpriseManagePostVO.getEndTime().before(currentTime))
                .collect(Collectors.toList()));

        return postInfoDTO;
    }

    private List<EnterpriseManagePostVO> processEnterpriseManagePostVO(List<PostInfo> postInfos){
        return postInfos.stream().map(postInfo -> {
            EnterpriseManagePostVO enterpriseManagePostVO = new EnterpriseManagePostVO();
            BeanUtils.copyProperties(postInfo, enterpriseManagePostVO);
            List<UserResumeVO> userResumes = resume2PostDao.findByPostId(postInfo.getId()).stream().map(resume2Post -> {
                UserResumeVO userResumeVO = new UserResumeVO();
                userResumeVO.setUserVO(new UserVO(accountDao.findById(resume2Post.getAccountId()).get(),
                        userInfoDao.findByAccountId(resume2Post.getAccountId()).get()));
                userResumeVO.setResume(resumeDao.findById(resume2Post.getResumeId()).get());
                return userResumeVO;
            }).collect(Collectors.toList());
            enterpriseManagePostVO.setUserResumeInfos(userResumes);
            return enterpriseManagePostVO;
        }).collect(Collectors.toList());
    }


}
