package com.jsonschang.common.util;


import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author zhangqiyang
 * @date 2020/5/27 - 10:03
 * @description 获取登录用户信息
 */
public class SecurityUtil {

    public static Object getBean(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
