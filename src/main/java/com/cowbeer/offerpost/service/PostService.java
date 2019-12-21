package com.cowbeer.offerpost.service;

import com.cowbeer.offerpost.data.command.PostCommand;
import com.cowbeer.offerpost.data.command.SearchPostCommand;
import com.cowbeer.offerpost.data.vo.PostInfoVO;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 10:59
 */
public interface PostService {
    PostInfoVO addPostInfo(Long accountId, PostCommand command);

    PostInfoVO getPostInfoById(Long postId);

    List<PostInfoVO> getPosts(Integer status);

    List<PostInfoVO> getPosts();

    List<PostInfoVO> getPostsByEnterprise(Long accountId);

    PostInfoVO passPostInfo(Long postId);

    PostInfoVO rejectPostInfo(Long postId);

    List<PostInfoVO> searchPosts(SearchPostCommand command, String keyword);
}
