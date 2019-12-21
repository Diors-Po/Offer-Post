package com.cowbeer.offerpost.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 10:26
 */
@Data
@Configuration
public class OSSConfiguration {
    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value(("${oss.accessKeySecret}"))
    private String accessKeySecret;

    @Value("${oss.endPoint}")
    private String endPoint;

    @Value("${oss.bucketName}")
    private String bucketName;

    public String getBaseUrl(){
        return "http://"+bucketName+"."+endPoint+"/";
    }

    public OSS ossClient(){
        return new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
    }
}
