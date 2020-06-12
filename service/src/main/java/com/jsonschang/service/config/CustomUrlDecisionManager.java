package com.jsonschang.service.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhangqiyang
 * @date 2020/5/31 - 18:23
 * @description 对当前登录用户所具有的权限和用户请求的资源所需权限做匹配
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new AccessDeniedException("尚未登录，请登录!");
        }
        //获取当前登录用户的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> collect = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        for (ConfigAttribute configAttribute : collection) {
            String needRole = configAttribute.getAttribute();
            //登录即可访问的资源
            if(Objects.equals(needRole,"ROLE_LOGIN")){
                return;
            }
            if(collect.contains(needRole)){
                return;
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
