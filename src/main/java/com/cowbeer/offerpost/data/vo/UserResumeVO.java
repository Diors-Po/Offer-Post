package com.cowbeer.offerpost.data.vo;

import com.cowbeer.offerpost.model.Resume;
import lombok.Data;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 20:38
 */
@Data
public class UserResumeVO {
    private UserVO userVO;
    private Resume resume;
}
