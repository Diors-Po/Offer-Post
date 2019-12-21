package com.cowbeer.offerpost.service.impl;

import com.cowbeer.offerpost.constants.CheckStatus;
import com.cowbeer.offerpost.dao.EnterpriseInfoDao;
import com.cowbeer.offerpost.dao.PostInfoDao;
import com.cowbeer.offerpost.data.command.PostCommand;
import com.cowbeer.offerpost.data.command.SearchPostCommand;
import com.cowbeer.offerpost.data.vo.PostInfoVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.model.EnterpriseInfo;
import com.cowbeer.offerpost.model.PostInfo;
import com.cowbeer.offerpost.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<PostInfoVO> getPosts(Integer status) {
        return postInfoDao.findAllByStatus(status).stream().map(this::processVO).collect(Collectors.toList());
    }

    @Override
    public List<PostInfoVO> getPosts() {
        return postInfoDao.findAll().stream().map(this::processVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostInfoVO> getPostsByEnterprise(Long accountId) {
        return postInfoDao.findByAccountIdAndStatus(accountId, CheckStatus.AUTH_PASS)
                .stream().map(this::processVO).collect(Collectors.toList());
    }

    @Override
    public PostInfoVO passPostInfo(Long postId) {
        Optional<PostInfo> infoOptional = postInfoDao.findById(postId);
        if (!infoOptional.isPresent())
            throw new BadRequestException("招聘信息不存在");
        PostInfo postInfo = infoOptional.get();
        Optional<EnterpriseInfo> enterpriseInfoOptional = enterpriseInfoDao.findByAccountId(postInfo.getAccountId());
        if (!enterpriseInfoOptional.isPresent() || !enterpriseInfoOptional.get().getAuthStatus().equals(CheckStatus.AUTH_PASS))
            throw new BadRequestException("企业不存在或未通过认证，无法通过该企业的招聘信息");
        postInfo.setStatus(CheckStatus.AUTH_PASS);
        return processVO(postInfoDao.save(postInfo));
    }

    @Override
    public PostInfoVO rejectPostInfo(Long postId) {
        Optional<PostInfo> infoOptional = postInfoDao.findById(postId);
        if (!infoOptional.isPresent())
            throw new BadRequestException("招聘信息不存在");
        PostInfo postInfo = infoOptional.get();
        postInfo.setStatus(CheckStatus.AUTH_FAIL);
        return processVO(postInfoDao.save(postInfo));
    }

    @Override
    public List<PostInfoVO> searchPosts(SearchPostCommand command, String keyword) {
        List<PostInfo> allByStatus = postInfoDao.findAllByStatus(CheckStatus.AUTH_PASS);
        return allByStatus.stream().map(this::processVO).collect(Collectors.toList());
    }

    private PostInfoVO processVO(PostInfo postInfo){
        Optional<EnterpriseInfo> enterpriseInfoOptional = enterpriseInfoDao.findByAccountId(postInfo.getAccountId());
        PostInfoVO postInfoVO = new PostInfoVO();
        postInfoVO.setPostInfo(postInfo);
        enterpriseInfoOptional.ifPresent(postInfoVO::setEnterpriseInfo);
        return postInfoVO;
    }
}
