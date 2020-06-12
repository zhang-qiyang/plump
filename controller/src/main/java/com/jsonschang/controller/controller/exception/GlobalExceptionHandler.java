package com.jsonschang.controller.controller.exception;

import com.jsonschang.common.customenum.ResponseEnum;
import com.jsonschang.common.dto.ResponseResult;
import com.jsonschang.common.exception.UserExistException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangqiyang
 * @date 2020/5/31 - 16:56
 * @description
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    public ResponseResult<String> userException(UserExistException e){
        return new ResponseResult<String>(ResponseEnum.FAIL, e.getMessage(), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public  ResponseResult<String> userException(AccessDeniedException e){
        return new ResponseResult<String>(ResponseEnum.FAIL, e.getMessage(), e.getMessage());
    }

}
