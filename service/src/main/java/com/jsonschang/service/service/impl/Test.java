package com.jsonschang.service.service.impl;

import com.jsonschang.model.entity.User;
import com.jsonschang.service.service.TestProxy;

import java.lang.reflect.Proxy;

/**
 * @author zhangqiyang
 * @date 2020/6/3 - 16:27
 */
public class Test {
    public static void main(String[] args) {
        TestproxyImpl testproxy = new TestproxyImpl();
        TestInvocationHandler testInvocationHandler = new TestInvocationHandler(testproxy);
        TestProxy testProxy = (TestProxy) Proxy.newProxyInstance(testproxy.getClass().getClassLoader(), testproxy.getClass().getInterfaces(), testInvocationHandler);
        String string = testProxy.getString();
    }
}
