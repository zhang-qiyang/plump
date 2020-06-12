package com.jsonschang.common.dto;

import com.jsonschang.common.customenum.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhangqiyang
 * @date 2020/5/28 - 10:50
 * @description 统一响应实体类
 */
@Data
public class ResponseResult <T> implements Serializable {
    private static final long serialVersionUID = 8779908115165080602L;
    private String code;
    private String success;
    private String message;
    private T data;

    public ResponseResult(ResponseEnum responseEnum,String message,T data){
        this.code=responseEnum.getCode();
        this.success=responseEnum.getSuccess();
        this.message=message;
        this.data=data;
    }
}
