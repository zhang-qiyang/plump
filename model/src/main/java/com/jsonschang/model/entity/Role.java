package com.jsonschang.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Role)实体类
 *
 * @author JsonsChang
 * @since 2020-05-31 15:05:10
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 102370061930604021L;
    
    private Integer id;
    
    private String name;
    /**
    * 角色名称
    */
    private String namezh;


}