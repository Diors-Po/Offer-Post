package com.cowbeer.offerpost.data;

import com.cowbeer.offerpost.data.vo.EnterpriseManagePostVO;
import lombok.Data;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 20:24
 */
@Data
public class PostInfoDTO {
    List<EnterpriseManagePostVO> authIng;
    List<EnterpriseManagePostVO> authFailed;
    List<EnterpriseManagePostVO> notStarted;
    List<EnterpriseManagePostVO> processing;
    List<EnterpriseManagePostVO> ended;
}
