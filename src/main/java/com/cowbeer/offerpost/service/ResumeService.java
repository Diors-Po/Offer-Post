package com.cowbeer.offerpost.service;

import com.cowbeer.offerpost.data.command.ResumeCommand;
import com.cowbeer.offerpost.data.vo.ResumeVO;
import com.cowbeer.offerpost.model.Resume;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 12:10
 */
public interface ResumeService {
    ResumeVO getResumeInfoById(Long resumeId);

    ResumeVO addResume(Long accountId, ResumeCommand command);

    List<Resume> getResumeInfoByAccountId(Long accountId);

    void deleteResumeById(Long accountId, Long resumeId);
}
