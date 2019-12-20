package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateEnterpriseAuthCommand;
import com.cowbeer.offerpost.data.vo.EnterpriseVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.exception.UnauthorizedException;
import com.cowbeer.offerpost.service.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    @PutMapping(value = "/api/enterprise/{accountId}")
    public EnterpriseVO updateEnterprise(@Validated @RequestBody UpdateEnterpriseAuthCommand command, @PathVariable("accountId") Long accountId,
                                         BindingResult result, HttpSession session){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new UnauthorizedException("无权修改他人信息");
        return enterpriseService.updateEnterpriseAuth(accountId, command);
    }

}
