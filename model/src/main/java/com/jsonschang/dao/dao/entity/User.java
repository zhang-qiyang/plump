package com.jsonschang.dao.dao.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * user(User)实体类
 *
 * @author JsonsChang
 * @since 2020-05-31 16:21:23
 */
public class User implements Serializable {
    private static final long serialVersionUID = -97188945744336487L;
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
    
    private Date createTime;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}