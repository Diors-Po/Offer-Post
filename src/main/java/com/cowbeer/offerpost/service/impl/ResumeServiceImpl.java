package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.constants.RoleType;
import com.cowbeer.offerpost.dao.*;
import com.cowbeer.offerpost.data.command.ResumeCommand;
import com.cowbeer.offerpost.data.vo.Resume2PostVO;
import com.cowbeer.offerpost.data.vo.ResumeVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.exception.ForbiddenRequestException;
import com.cowbeer.offerpost.model.*;
import com.cowbeer.offerpost.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 12:11
 */
@Slf4j
@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    ResumeDao resumeDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    PostInfoDao postInfoDao;

    @Autowired
    Resume2PostDao resume2PostDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Override
    public ResumeVO getResumeInfoById(Long resumeId) {
        Optional<Resume> resumeOptional = resumeDao.findById(resumeId);
        if (!resumeOptional.isPresent())
            throw new BadRequestException("该简历不存在");
        return processVO(resumeOptional.get());
    }

    @Override
    public ResumeVO addResume(Long accountId, ResumeCommand command) {
        Resume resume = command.getResume();
        resume.setAccountId(accountId);
        return processVO(resumeDao.save(resume));
    }

    @Override
    public List<Resume> getResumeInfoByAccountId(Long accountId) {
        return resumeDao.findByAccountId(accountId);
    }

    @Override
    public void deleteResumeById(Long accountId, Long resumeId) {
        Optional<Resume> resumeOptional = resumeDao.findById(resumeId);
        if (!resumeOptional.isPresent())
            throw new BadRequestException("该简历不存在");
        if (resumeOptional.get().getAccountId().equals(accountId) || accountDao.findById(accountId).get().getRole().equals(RoleType.ADMIN)) {
            resumeDao.deleteById(resumeId);
            resume2PostDao.findByResumeId(resumeId).forEach(resume2Post -> {
                resume2PostDao.delete(resume2Post);
            });
        }
        else
            throw new ForbiddenRequestException("无权删除该简历");
    }

    @Override
    public List<Resume2PostVO> getSentResumesByAccountId(Long accountId) {
        return resume2PostDao.findByAccountId(accountId).stream().map(resume2Post -> {
            Resume resume = resumeDao.findById(resume2Post.getResumeId()).get();
            EnterpriseInfo enterpriseInfo = enterpriseInfoDao.findByAccountId(postInfoDao.findById(resume2Post.getPostId()).get().getAccountId()).get();
            PostInfo postInfo = postInfoDao.findById(resume2Post.getPostId()).get();
            return new Resume2PostVO(resume2Post, resume, postInfo, enterpriseInfo);
        }).collect(Collectors.toList());
    }

    private ResumeVO processVO(Resume resume){
        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setResume(resume);
        Optional<UserInfo> userInfoOptional = userInfoDao.findByAccountId(resume.getAccountId());
        userInfoOptional.ifPresent(resumeVO::setUserInfo);
        return resumeVO;
    }
}
