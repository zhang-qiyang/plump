package com.jsonschang.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 系统日志(Log)实体类
 *
 * @author JsonsChang
 * @since 2020-05-27 13:40:40
 */
@Data
public class Log implements Serializable {
    private static final long serialVersionUID = 684335173692823561L;
    
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date createTime;
    
    private String description;
    
    private String exceptionDetail;
    
    private String logType;
    
    private String method;
    
    private String params;
    
    private String requestIp;
    
    private Long time;
    
    private String username;
    
    private String address;
    
    private String browser;
    public Log(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }

}