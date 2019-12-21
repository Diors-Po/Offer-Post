package com.cowbeer.offerpost.data.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 20:29
 */
@Data
public class EnterpriseManagePostVO {
    private Long id;
    private Long accountId;
    private String title;
    private String description;
    private String workLocation;
    private Integer salary;
    private Integer headCount;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private List<UserResumeVO> userResumeInfos;

}
