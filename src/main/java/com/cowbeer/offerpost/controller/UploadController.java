package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 10:38
 */
@Slf4j
@RestController
public class UploadController {
    @Autowired
    UploadService uploadService;

    @RequestMapping(value = "/api/common/image", method = RequestMethod.POST)
    public String uploadImage(MultipartFile file){
        return uploadService.uploadImage(file);
    }

    @RequestMapping(value = "/api/common/general_file", method = RequestMethod.POST)
    public String uploadGeneralFile(MultipartFile file){
        return uploadService.uploadGeneralFile(file);
    }

    @RequestMapping(value = "/api/common/base64str", method = RequestMethod.POST)
    public String uploadBase64Image(String data){
        log.info(data);
        return uploadService.uploadBase64(data);
    }
}
