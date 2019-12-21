package com.cowbeer.offerpost.service;

import com.cowbeer.offerpost.data.StatisticsDTO;
import com.cowbeer.offerpost.data.command.RegisterCommand;
import com.cowbeer.offerpost.data.command.UpdateEnterpriseAuthCommand;
import com.cowbeer.offerpost.data.vo.EnterpriseVO;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 22:00
 */
public interface EnterpriseService {

    EnterpriseVO registerEnterprise(RegisterCommand command);

    EnterpriseVO getEnterprise(Long accountId);

    EnterpriseVO updateEnterpriseAuth(Long accountId, UpdateEnterpriseAuthCommand command);

    StatisticsDTO getStatisticsInfo(Long accountId);

    EnterpriseVO passEnterpriseAuth(Long accountId);

    EnterpriseVO rejectEnterpriseAuth(Long accountId);
}
