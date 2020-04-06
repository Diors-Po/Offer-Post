package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.annotation.LoginRequired;
import com.cowbeer.offerpost.data.PostInfoDTO;
import com.cowbeer.offerpost.data.StatisticsDTO;
import com.cowbeer.offerpost.data.command.CheckCommand;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateEnterpriseAuthCommand;
import com.cowbeer.offerpost.data.vo.EnterpriseVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.exception.ForbiddenRequestException;
import com.cowbeer.offerpost.exception.UnauthorizedException;
import com.cowbeer.offerpost.service.EnterpriseService;
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
 * @date 2019-12-19 21:40
 */
@Slf4j
@RestController
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;

    @PostMapping(value = "/api/enterprise")
    public EnterpriseVO registerEnterprise(@Validated @RequestBody RegisterCommand command, BindingResult result){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        return enterpriseService.registerEnterprise(command);
    }

    @GetMapping(value = "/api/enterprise/{accountId}")
    public EnterpriseVO getEnterprise(@PathVariable("accountId") Long accountId){
        return enterpriseService.getEnterprise(accountId);
    }

    @LoginRequired
    @PutMapping(value = "/api/enterprise/{accountId}")
    public EnterpriseVO updateEnterprise(@Validated @RequestBody UpdateEnterpriseAuthCommand command, @PathVariable("accountId") Long accountId,
                                         BindingResult result, HttpSession session){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new ForbiddenRequestException("无权修改他人信息");
        return enterpriseService.updateEnterpriseAuth(accountId, command);
    }

    @LoginRequired
    @GetMapping(value = "/api/enterprise/{accountId}/statistics")
    public StatisticsDTO statisticsInfo(@PathVariable("accountId") Long accountId, HttpSession session){
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new ForbiddenRequestException("无权查看他人信息");
        return enterpriseService.getStatisticsInfo(accountId);
    }

    @PutMapping(value = "/api/enterprise/{accountId}/auth_info/pass")
    public EnterpriseVO passEnterpriseAuth(@PathVariable("accountId") Long accountId){
        return enterpriseService.passEnterpriseAuth(accountId);
    }

    @PutMapping(value = "/api/enterprise/{accountId}/auth_info/fail")
    public EnterpriseVO failEnterpriseAuth(@PathVariable("accountId") Long accountId,  @Validated @RequestBody CheckCommand command
            , BindingResult result){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        return enterpriseService.rejectEnterpriseAuth(accountId, command);
    }

    @GetMapping(value = "/api/enterprises/status/checked")
    public List<EnterpriseVO> getCheckedEnterprises(){
        return enterpriseService.getCheckedEnterprises();
    }

    @GetMapping(value = "/api/enterprises/status/unchecked")
    public List<EnterpriseVO> getUncheckedEnterprises(){
        return enterpriseService.getUncheckEnterprises();
    }

    @GetMapping(value = "/api/enterprises/status/fail")
    public List<EnterpriseVO> getFailedEnterprises(){
        return enterpriseService.getFailedEnterprises();
    }

    @GetMapping(value = "/api/enterprises/status/pass")
    public List<EnterpriseVO> getPassedEnterprises(){
        return enterpriseService.getPassedEnterprises();
    }

    @GetMapping(value = "/api/enterprises")
    public List<EnterpriseVO> getAllEnterprise(){
        return enterpriseService.getAllEnterprise();
    }

    @LoginRequired
    @GetMapping(value = "/api/enterprise/{accountId}/posts/manage")
    public PostInfoDTO getPostInfoList(@PathVariable("accountId") Long accountId, HttpSession session){
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new ForbiddenRequestException("无权修改他人信息");
        return enterpriseService.getPostInfoList(accountId);
    }

}
