package com.cowbeer.offerpost.exception;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-19 10:58
 */
public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String msg){
        super(msg);
    }
    public UnauthorizedException(){
        super("请认证后操作");
    }
}
