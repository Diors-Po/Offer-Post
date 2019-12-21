package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.annotation.LoginRequired;
import com.cowbeer.offerpost.data.command.ResumeCommand;
import com.cowbeer.offerpost.data.vo.ResumeVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.exception.ForbiddenRequestException;
import com.cowbeer.offerpost.model.Resume;
import com.cowbeer.offerpost.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 11:57
 */
@Slf4j
@RestController
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @PostMapping("/api/user/{accountId}/resume")
    public ResumeVO addResume(@Validated @RequestBody ResumeCommand command, @PathVariable("accountId") Long accountId,
                              BindingResult result, HttpSession session){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new ForbiddenRequestException("无权操作他人账号");
        return resumeService.addResume(accountId,command);
    }

    @GetMapping("/api/user/resume/{resumeId}")
    public ResumeVO getResumeById(@PathVariable("resumeId") Long resumeId){
        return resumeService.getResumeInfoById(resumeId);
    }

    @LoginRequired
    @GetMapping("/api/user/{accountId}/resumes")
    public List<Resume> getResumesByUser(@PathVariable("accountId") Long accountId, HttpSession session){
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new ForbiddenRequestException("无权操作他人账号");
        return resumeService.getResumeInfoByAccountId(accountId);
    }
}
