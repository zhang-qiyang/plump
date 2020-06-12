package com.jsonschang.common.customenum;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangqiyang
 * @date 2020/5/28 - 10:57
 * @description 响应枚举
 */
@Getter
public enum ResponseEnum {
    /**
     * 成功
     */
    SUCCESS("00","true"),
    /**
     * 失败
     */
    FAIL("01","false"),

    /**
     * 未登录 401
     */
    LOGIN(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED),"false");
    /**
     * 是否成功
     */
    private final String success;

    /**
     * 状态码
     */
    private final String code;

    ResponseEnum(String code, String success){
        this.code=code;
        this.success=success;
    }
}
