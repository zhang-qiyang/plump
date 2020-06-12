package com.jsonschang.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonschang.common.customenum.ResponseEnum;
import com.jsonschang.common.dto.ResponseResult;
import com.jsonschang.model.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhangqiyang
 * @date 2020/5/29 - 14:15
 * @description 自定义授权失败返回信息，注入spring security配置文件中
 */
@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        ResponseResult<User> result = new ResponseResult<>(ResponseEnum.FAIL, "权限不足，请联系管理员",null);
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
