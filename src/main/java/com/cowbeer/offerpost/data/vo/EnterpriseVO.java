package com.cowbeer.offerpost.data.vo;

import com.cowbeer.offerpost.constants.CheckStatus;
import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.model.EnterpriseInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 11:59
 */
@Data
@NoArgsConstructor
public class EnterpriseVO extends Account {
    private boolean authPassed = false;
    private EnterpriseInfo enterpriseInfo;

    public EnterpriseVO(Account account, EnterpriseInfo enterpriseInfo){
        BeanUtils.copyProperties(account,this);
        this.setPassword("******");
        this.enterpriseInfo = enterpriseInfo;
        if (enterpriseInfo!=null && enterpriseInfo.getAuthStatus()
                .equals(CheckStatus.AUTH_PASS)){
            this.authPassed = true;
        }
    }

    public EnterpriseVO(Account account){
        BeanUtils.copyProperties(account, this);
        this.setPassword("******");
    }

    public void setAccount(Account account){
        BeanUtils.copyProperties(account, this);
        this.setPassword("******");
    }

    public void setEnterpriseInfo(EnterpriseInfo enterpriseInfo){
        this.enterpriseInfo = enterpriseInfo;
        if (enterpriseInfo!=null && enterpriseInfo.getAuthStatus()
                .equals(CheckStatus.AUTH_PASS)){
            this.authPassed = true;
        }
    }
}
