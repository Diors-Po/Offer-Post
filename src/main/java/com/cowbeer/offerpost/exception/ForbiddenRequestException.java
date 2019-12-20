package com.cowbeer.offerpost.exception;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 22:40
 */
public class ForbiddenRequestException extends BaseException{
    public ForbiddenRequestException(String msg){
        super(msg);
    }
    public ForbiddenRequestException(){
        super("缺少访问权限");
    }
}
