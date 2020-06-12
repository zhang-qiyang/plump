package com.jsonschang.common.util;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * @author zhangqiyang
 * @date 2020/4/27 - 15:42
 * @description
 */
public class PasswordEncryption implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return Pbkdf2Encryption.sha1((String) charSequence, "zhangqiyang");
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return Objects.equals(s,encode(charSequence));
    }
}
