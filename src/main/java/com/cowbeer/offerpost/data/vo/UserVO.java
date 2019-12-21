package com.cowbeer.offerpost.data.vo;

import com.cowbeer.offerpost.model.Account;
import com.cowbeer.offerpost.model.UserInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:38
 */
@Data
public class UserVO extends Account{
    private Integer age;
    private Integer gender;
    private String university;
    private Integer education;

    public UserVO(Account account, UserInfo userInfo){
        BeanUtils.copyProperties(account,this);
        this.setPassword("******");
        BeanUtils.copyProperties(userInfo, this, "id");
    }

    public UserVO(Account account){
        BeanUtils.copyProperties(account, this);
        this.setPassword("******");
    }

    public void setUserInfo(UserInfo userInfo){
        BeanUtils.copyProperties(userInfo, this, "id");
    }

    public void setAccount(Account account){
        BeanUtils.copyProperties(account,this);
        this.setPassword("******");
    }
}
