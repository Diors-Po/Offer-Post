package com.cowbeer.offerpost.data.vo;

import com.cowbeer.offerpost.model.EnterpriseInfo;
import com.cowbeer.offerpost.model.PostInfo;
import com.cowbeer.offerpost.model.Resume;
import com.cowbeer.offerpost.model.Resume2Post;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 19:38
 */
@Data
@NoArgsConstructor
public class Resume2PostVO {
    private Long resumeId;
    private String resumeName;
    private Timestamp createTime;
    private Long postId;
    private String postTitle;
    private String annexUrl;
    private Long accountId;
    //此处的enterpriseId是指enterprise的accountId,
    private Long enterpriseId;
    private String enterpriseName;

    public Resume2PostVO(Resume2Post resume2Post){
        BeanUtils.copyProperties(resume2Post, this);
    }

    public Resume2PostVO(Resume2Post resume2Post, Resume resume, PostInfo postInfo, EnterpriseInfo enterpriseInfo){
        BeanUtils.copyProperties(resume2Post, this);
        System.out.println(resume2Post.getCreateTime());
        this.enterpriseId = enterpriseInfo.getAccountId();
        this.enterpriseName = enterpriseInfo.getEnterpriseName();
        this.resumeName = resume.getName();
        this.postTitle = postInfo.getTitle();
        this.annexUrl = resume.getAnnexUrl();
    }

}
