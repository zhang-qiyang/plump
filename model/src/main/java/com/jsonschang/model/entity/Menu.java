package com.jsonschang.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (Menu)实体类
 *
 * @author JsonsChang
 * @since 2020-05-31 15:46:33
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = -70787137152561658L;
    
    private Integer id;
    
    private String url;
    
    private String path;
    
    private String component;
    
    private String name;
    
    private String iconcls;
    
    private Integer keepalive;
    
    private Integer requireauth;
    
    private Integer parentid;
    
    private Integer enabled;

    private List<Role> roles;

}