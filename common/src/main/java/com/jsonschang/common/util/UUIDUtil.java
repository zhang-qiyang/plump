package com.jsonschang.common.util;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author zhangqiyang
 * @date 2020/5/31 - 16:29
 * @description
 */
public class UUIDUtil implements Serializable {

    private static final long serialVersionUID = -3074026967060488050L;

    public static String getuuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
