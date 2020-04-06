package com.cowbeer.offerpost.data.vo;

import com.cowbeer.offerpost.model.Post2Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2020-04-07 01:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO extends Post2Comment{
    private UserVO userVO;

    public CommentVO(Post2Comment post2Comment) {
        BeanUtils.copyProperties(post2Comment, this);
    }
}
