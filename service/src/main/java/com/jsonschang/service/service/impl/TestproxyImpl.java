package com.jsonschang.service.service.impl;

import com.jsonschang.service.service.TestProxy;
import org.springframework.stereotype.Service;

/**
 * @author zhangqiyang
 * @date 2020/6/3 - 16:19
 * @description
 */
@Service
public class TestproxyImpl implements TestProxy {
    @Override
    public String getString() {
        return "执行了动态代理";
    }
}
