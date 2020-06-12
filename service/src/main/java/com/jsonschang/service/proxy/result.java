package com.jsonschang.service.proxy;

import org.springframework.cglib.proxy.Enhancer;

/**
 * @author zhangqiyang
 * @date 2020/6/3 - 17:07
 * @description CGLIB动态代理
 */
public class result {
    public static void main(String[] args) {
        MyProxy myProxy = new MyProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        enhancer.setCallback(myProxy);
        Test test = (Test) enhancer.create();
    }
}
