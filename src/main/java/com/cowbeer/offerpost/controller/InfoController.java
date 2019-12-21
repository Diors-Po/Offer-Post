package com.cowbeer.offerpost.controller;

import com.cowbeer.offerpost.dao.PostInfoDao;
import com.cowbeer.offerpost.model.PostInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 14:29
 */
@RestController
public class InfoController {

    @Autowired
    PostInfoDao postInfoDao;

    @GetMapping(value = "/api/test")
    public Long test(){
        return postInfoDao.count();
    }

}
