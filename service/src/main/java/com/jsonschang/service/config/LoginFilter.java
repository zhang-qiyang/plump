package com.jsonschang.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonschang.common.customenum.ResponseEnum;
import com.jsonschang.common.dto.ResponseResult;
import com.jsonschang.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhangqiyang
 * @date 2020/5/31 - 21:49
 * @description
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!Objects.equals("POST", request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().contains(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            Map loginData = new HashMap<>(16);
            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
            }finally {
                String code = (String) loginData.get("code");
                checkCode(response, code, verifyCode);
            }
            String username = (String) loginData.get(getUsernameParameter());
            String password = (String) loginData.get(getPasswordParameter());
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);
            setDetails(request, authRequest);
            User principal = new User();
            principal.setUserName(username);
            sessionRegistry.registerNewSession(request.getSession(true).getId(), principal);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            checkCode(response, request.getParameter("code"), verifyCode);
            return super.attemptAuthentication(request, response);
        }
    }
    public void checkCode(HttpServletResponse resp, String code, String verifyCode) {
        if (code == null || verifyCode == null || "".equals(code) || !verifyCode.toLowerCase().equals(code.toLowerCase())) {
            //验证码不正确
            resp.setContentType("application/json;charset=utf-8");
            try {
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString(new ResponseResult<>(ResponseEnum.FAIL, "验证码不正确", "验证码不正确")));
                out.flush();
                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
