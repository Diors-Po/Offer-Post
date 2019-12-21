package com.cowbeer.offerpost.data;

import com.cowbeer.offerpost.data.vo.PostInfoVO;
import lombok.Data;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 15:09
 */
@Data
public class PostsAuthDTO {

    List<PostInfoVO> authedPosts;

    List<PostInfoVO> unauthPosts;
}
