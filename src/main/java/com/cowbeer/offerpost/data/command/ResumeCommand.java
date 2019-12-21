package com.cowbeer.offerpost.data.command;

import com.cowbeer.offerpost.model.Resume;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 12:01
 */
@Data
public class ResumeCommand {

    @NotNull(message = "简历名称不可为空")
    private String name;
    @NotNull(message = "请上传简历附件")
    private String annexUrl;

    public Resume getResume(){
        Resume resume = new Resume();
        BeanUtils.copyProperties(this, resume);
        return resume;
    }
}
