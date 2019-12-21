package com.cowbeer.offerpost.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.cowbeer.offerpost.config.OSSConfiguration;
import com.cowbeer.offerpost.constants.UploadType;
import com.cowbeer.offerpost.exception.BaseException;
import com.cowbeer.offerpost.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 10:25
 */
@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private OSSConfiguration ossConfig;

    @Override
    public String uploadImage(MultipartFile file) {
        String fileName = UploadType.IMAGE+file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+"_"
                +System.currentTimeMillis()+""
                +file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        return doUpload(fileName, file);
    }

    @Override
    public String uploadBase64(String data) {
        String fileType = data.split(";")[0].split("/")[1];
        String fileName = UploadType.IMAGE+System.currentTimeMillis()+"."+fileType;
        BASE64Decoder decoder = new BASE64Decoder();
        String url;
        try {
            byte[] bytes = decoder.decodeBuffer(data.split(",")[1]);
            url = doUpload(fileName, new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            log.error("OSS上传出错！", e);
            throw new BaseException("OSS上传出错");
        }
        return url;
    }

    @Override
    public String uploadGeneralFile(MultipartFile file){
        String fileName = UploadType.OTHERS+file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+"_"
                +System.currentTimeMillis()+""
                +file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        return doUpload(fileName, file);
    }

    private String doUpload(String fileName, MultipartFile file){
        OSS ossClient = ossConfig.ossClient();
        try {
            PutObjectResult result = ossClient.putObject(ossConfig.getBucketName(), fileName, file.getInputStream());
            return ossConfig.getBaseUrl()+fileName;
        } catch (IOException e) {
            log.error("OSS上传出错！", e);
            throw new BaseException(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }

    private String doUpload(String fileName, InputStream inputStream){
        OSS ossClient = ossConfig.ossClient();
        PutObjectResult result = ossClient.putObject(ossConfig.getBucketName(), fileName, inputStream);
        String url = ossConfig.getBaseUrl()+fileName;
        ossClient.shutdown();
        return url;
    }

}
