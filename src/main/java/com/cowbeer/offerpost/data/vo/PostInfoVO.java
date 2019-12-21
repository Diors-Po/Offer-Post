package com.cowbeer.offerpost.data.vo;

import com.cowbeer.offerpost.model.EnterpriseInfo;
import com.cowbeer.offerpost.model.PostInfo;
import lombok.Data;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 10:56
 */
@Data
public class PostInfoVO {
    private EnterpriseInfo enterpriseInfo;
    private PostInfo postInfo;
}
