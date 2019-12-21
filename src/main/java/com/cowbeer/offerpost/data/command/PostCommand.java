package com.cowbeer.offerpost.data.command;

import com.cowbeer.offerpost.constants.CheckStatus;
import com.cowbeer.offerpost.model.PostInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 10:57
 */
@Data
public class PostCommand {

    @NotNull(message = "招聘标题不可为空")
    private String title;

    @NotNull(message = "描述不可为空")
    private String description;

    @NotNull(message = "工作地点不可为空")
    private String workLocation;

    @NotNull(message = "薪水不可为空")
    private Integer salary;

    @NotNull(message = "招聘人数不可为空")
    private Integer headCount;

    @NotNull(message = "请选择信息发布开始时间")
    private Timestamp startTime;
    @NotNull(message = "请选择信息发布结束时间")
    private Timestamp endTime;

    public PostInfo getPostInfo(){
        PostInfo postInfo = new PostInfo();
        BeanUtils.copyProperties(this, postInfo);
        postInfo.setStatus(CheckStatus.AUTH_ING);
        return postInfo;
    }

}
