package com.cowbeer.offerpost.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 10:23
 */
public interface UploadService {
    String uploadGeneralFile(MultipartFile file);

    String uploadImage(MultipartFile file);
}
