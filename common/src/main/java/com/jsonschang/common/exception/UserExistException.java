package com.jsonschang.common.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author zhangqiyang
 * @date 2020/5/31 - 16:53
 * @description
 */
public class UserExistException extends RuntimeException{
    private static final long serialVersionUID = -6478896573582954662L;

    public UserExistException(String msg){
        super(msg);
    }
}
