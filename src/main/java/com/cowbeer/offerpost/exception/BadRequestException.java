package com.cowbeer.offerpost.exception;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 10:54
 */
public class BadRequestException extends BaseException {
    public BadRequestException(String msg){
        super(msg);
    }
    public BadRequestException(){
        super("访问请求出错");
    }
}
