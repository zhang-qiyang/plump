package com.jsonschang.service.service.impl;

import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhangqiyang
 * @date 2020/6/3 - 16:20
 * @description
 */
@AllArgsConstructor
public class TestInvocationHandler implements InvocationHandler {
    private Object object;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(object, args);
    }
}
