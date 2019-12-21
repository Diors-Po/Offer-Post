package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.constants.RoleType;
import com.cowbeer.offerpost.dao.AccountDao;
import com.cowbeer.offerpost.dao.ResumeDao;
import com.cowbeer.offerpost.dao.UserInfoDao;
import com.cowbeer.offerpost.data.command.ResumeCommand;
import com.cowbeer.offerpost.data.vo.ResumeVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.exception.ForbiddenRequestException;
import com.cowbeer.offerpost.model.Resume;
import com.cowbeer.offerpost.model.UserInfo;
import com.cowbeer.offerpost.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (resumeOptional.get().getAccountId().equals(accountId) || accountDao.findById(accountId).get().getRole().equals(RoleType.ADMIN))
            resumeDao.deleteById(resumeId);
        else
            throw new ForbiddenRequestException("无权删除该简历");
    }

    private ResumeVO processVO(Resume resume){
        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setResume(resume);
        Optional<UserInfo> userInfoOptional = userInfoDao.findByAccountId(resume.getAccountId());
        userInfoOptional.ifPresent(resumeVO::setUserInfo);
        return resumeVO;
    }
}
