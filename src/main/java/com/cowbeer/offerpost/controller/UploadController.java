package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 10:38
 */
@RestController
public class UploadController {
    @Autowired
    UploadService uploadService;

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String uploadImage(MultipartFile file){
        return uploadService.uploadImage(file);
    }

    @RequestMapping(value = "/generalFile", method = RequestMethod.POST)
    public String uploadGeneralFile(MultipartFile file){
        return uploadService.uploadGeneralFile(file);
    }
}
