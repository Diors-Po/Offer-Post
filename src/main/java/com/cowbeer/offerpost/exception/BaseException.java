package com.cowbeer.offerpost.exception;

import lombok.NoArgsConstructor;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-15 01:43
 */
@NoArgsConstructor
public class BaseException extends RuntimeException {
    public BaseException(String msg){
        super(msg);
    }
}
