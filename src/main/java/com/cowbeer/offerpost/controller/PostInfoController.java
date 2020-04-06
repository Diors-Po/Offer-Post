package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.annotation.LoginRequired;
import com.cowbeer.offerpost.constants.CheckStatus;
import com.cowbeer.offerpost.data.PostsAuthDTO;
import com.cowbeer.offerpost.data.command.CheckCommand;
import com.cowbeer.offerpost.data.command.PostCommand;
import com.cowbeer.offerpost.data.command.SearchPostCommand;
import com.cowbeer.offerpost.data.vo.PostInfoVO;
import com.cowbeer.offerpost.data.vo.UserVO;
import com.cowbeer.offerpost.exception.BadRequestException;
import com.cowbeer.offerpost.exception.ForbiddenRequestException;
import com.cowbeer.offerpost.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 10:53
 */
@Slf4j
@RestController
public class PostInfoController {

    @Autowired
    PostService postService;

    @LoginRequired
    @PostMapping(value = "/api/enterprise/{accountId}/post")
    public PostInfoVO addPostInfo(@Validated @RequestBody PostCommand command, @PathVariable("accountId") Long accountId
            , BindingResult result, HttpSession session){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        Long currentAId = (Long)session.getAttribute("accountId");
        if (!currentAId.equals(accountId))
            throw new ForbiddenRequestException("无权操作他人账号");
        return postService.addPostInfo(accountId, command);
    }

    @GetMapping(value = "/api/enterprise/{accountId}/posts")
    public List<PostInfoVO> getPostsByEnterprise(@PathVariable("accountId") Long accountId){
        return postService.getPostsByEnterprise(accountId);
    }

    @GetMapping(value = "/api/enterprise/post/{postId}")
    public PostInfoVO getPostById(@PathVariable("postId") Long postId){
        return postService.getPostInfoById(postId);
    }

    @GetMapping(value = "/api/enterprise/posts/auth_pass")
    public List<PostInfoVO> getPostInfos(){
        return postService.getPosts(CheckStatus.AUTH_PASS);
    }

    @GetMapping(value = "/api/enterprise/posts/status/all")
    public PostsAuthDTO getAllPostsInAuthPage(){
        PostsAuthDTO postsAuthDTO = new PostsAuthDTO();
        List<PostInfoVO> allPosts = postService.getPosts();
        postsAuthDTO.setAuthedPosts(allPosts.stream().filter(postInfoVO -> !postInfoVO.getPostInfo().getStatus().equals(CheckStatus.AUTH_ING)).collect(Collectors.toList()));
        allPosts.removeAll(postsAuthDTO.getAuthedPosts());
        postsAuthDTO.setUnauthPosts(allPosts);
        return postsAuthDTO;
    }

    @PutMapping(value = "/api/enterprise/post/{postId}/status/pass")
    public PostInfoVO passPostInfo(@PathVariable("postId")Long postId){
        return postService.passPostInfo(postId);
    }

    @PutMapping(value = "/api/enterprise/post/{postId}/status/fail")
    public PostInfoVO rejectPostInfo(@PathVariable("postId")Long postId, @Validated @RequestBody CheckCommand checkCommand
            , BindingResult result){
        if (result.hasErrors())
            throw new BadRequestException(result.getFieldErrors().get(0).getDefaultMessage());
        return postService.rejectPostInfo(postId, checkCommand);
    }

    @PostMapping(value = "/api/square/posts")
    public List<PostInfoVO> getSquarePosts(@RequestBody(required = false) SearchPostCommand command, @RequestParam(required = false,name = "search") String keyword){
        return postService.searchPosts(command, keyword);
    }

    @GetMapping(value = "/api/enterprise/post/{postId}/star/user")
    public List<UserVO> rejectPostInfo(@PathVariable("postId")Long postId){
        return postService.staredPeople(postId);
    }
}
