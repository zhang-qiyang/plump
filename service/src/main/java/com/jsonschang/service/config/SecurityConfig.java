package com.jsonschang.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonschang.common.customenum.ResponseEnum;
import com.jsonschang.common.dto.ResponseResult;
import com.jsonschang.common.util.PasswordEncryption;
import com.jsonschang.model.entity.User;
import com.jsonschang.service.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author zhangqiyang
 * @date 2020/5/27 - 11:09
 * @description SpringSecurity配置文件
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义权限不足返回信息
     */
    @Autowired
    private AuthenticationAccessDeniedHandler handler;

    @Resource
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    @Resource
    CustomUrlDecisionManager customUrlDecisionManager;
    @Resource
    UserServiceImpl userService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new PasswordEncryption();
    }

    @Bean
    SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    User user = (User) authentication.getPrincipal();
                    ResponseResult<User> result = new ResponseResult<>(ResponseEnum.SUCCESS, "登录成功", user);
                    writer.write(new ObjectMapper().writeValueAsString(result));
                    writer.flush();
                    writer.close();
                }
        );
        loginFilter.setAuthenticationFailureHandler((request, response, e) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    String message=null;
                    if (e instanceof LockedException) {
                        message="账户被锁定，请联系管理员!";
                    } else if (e instanceof CredentialsExpiredException) {
                        message="密码过期，请联系管理员!";
                    } else if (e instanceof AccountExpiredException) {
                        message="账户过期，请联系管理员!";
                    } else if (e instanceof DisabledException) {
                        message="账户被禁用，请联系管理员!";
                    } else if (e instanceof BadCredentialsException) {
                        message="用户名或密码错误，请重新输入!";
                    }
                    ResponseResult<User> result = new ResponseResult<>(ResponseEnum.FAIL, message, null);
                    writer.write(new ObjectMapper().writeValueAsString(result));
                    writer.flush();
                    writer.close();
                }
        );
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/login");
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        sessionStrategy.setMaximumSessions(1);
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
        return loginFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**","/user/verifyCode");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/user/insert").anonymous()
//                .and()
//                .formLogin().loginProcessingUrl("/login")
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        return o;
                    }
                })
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.write(new ObjectMapper().writeValueAsString(new ResponseResult<>(ResponseEnum.SUCCESS, "登出成功", null)));
                            out.flush();
                            out.close();
                        }
                )
                .permitAll()
                .and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((req,resp,e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    ResponseResult<String> result = new ResponseResult<>(ResponseEnum.LOGIN, "请登录后访问", null);
                    writer.write(new ObjectMapper().writeValueAsString(result));
                    writer.flush();
                    writer.close();
                })
                .accessDeniedHandler(handler);
        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(), event -> {
            HttpServletResponse resp = event.getResponse();
            resp.setContentType("application/json;charset=utf-8");
            resp.setStatus(401);
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(new ResponseResult<>(ResponseEnum.FAIL, "您已在另一台设备登录，本次登录已下线!", null)));
            out.flush();
            out.close();
        }), ConcurrentSessionFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
