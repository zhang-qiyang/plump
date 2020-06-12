package com.jsonschang.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author zhangqiyang
 * @date 2020/5/27 - 9:42
 * @description
 */
@Getter
public class BadRequestException extends RuntimeException{

    private static final long serialVersionUID = 6862366345130678214L;
    private Integer status = BAD_REQUEST.value();

    public BadRequestException(String msg){
        super(msg);
    }

    public BadRequestException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}
