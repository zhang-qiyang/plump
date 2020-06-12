package com.jsonschang.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * user(User)实体类
 *
 * @author JsonsChang
 * @since 2020-05-27 14:09:13
 */
@Data
public class User implements Serializable , UserDetails {
    private static final long serialVersionUID = -88734957560977532L;
    /**
    * uid
    */
    private String uid;
    /**
    * userName
    */
    private String userName;
    /**
    * age
    */
    private String age;
    /**
    * phone
    */
    private String phone;
    /**
    * address
    */
    private String address;
    /**
    * email
    */
    private String email;
    /**
    * 员工编号
    */
    private String empId;
    /**
    * 用户密码
    */
    private String password;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;

    private List<Role> roles;

    private List<Menu> menus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return userName;
    }
    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}