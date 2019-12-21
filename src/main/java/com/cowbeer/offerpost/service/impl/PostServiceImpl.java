package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.dao.EnterpriseInfoDao;
import com.cowbeer.offerpost.dao.PostInfoDao;
import com.cowbeer.offerpost.data.command.PostCommand;
import com.cowbeer.offerpost.data.vo.PostInfoVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.model.EnterpriseInfo;
import com.cowbeer.offerpost.model.PostInfo;
import com.cowbeer.offerpost.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 10:59
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostInfoDao postInfoDao;

    @Autowired
    EnterpriseInfoDao enterpriseInfoDao;

    @Override
    public PostInfoVO addPostInfo(Long accountId,PostCommand command) {
        PostInfo postInfo = command.getPostInfo();
        postInfo.setAccountId(accountId);
        return processVO(postInfoDao.save(postInfo));
    }

    @Override
    public PostInfoVO getPostInfoById(Long postId) {
        Optional<PostInfo> postInfoOptional = postInfoDao.findById(postId);
        if (!postInfoOptional.isPresent())
            throw new BadRequestException("该招聘信息不存在");
        return processVO(postInfoOptional.get());
    }

    private PostInfoVO processVO(PostInfo postInfo){
        Optional<EnterpriseInfo> enterpriseInfoOptional = enterpriseInfoDao.findByAccountId(postInfo.getAccountId());
        PostInfoVO postInfoVO = new PostInfoVO();
        postInfoVO.setPostInfo(postInfo);
        enterpriseInfoOptional.ifPresent(postInfoVO::setEnterpriseInfo);
        return postInfoVO;
    }
}
