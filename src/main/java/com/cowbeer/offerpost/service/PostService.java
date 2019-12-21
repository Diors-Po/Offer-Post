package com.cowbeer.offerpost.service;

import com.cowbeer.offerpost.data.command.PostCommand;
import com.cowbeer.offerpost.data.vo.PostInfoVO;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 10:59
 */
public interface PostService {
    PostInfoVO addPostInfo(Long accountId, PostCommand command);

    PostInfoVO getPostInfoById(Long postId);
}
