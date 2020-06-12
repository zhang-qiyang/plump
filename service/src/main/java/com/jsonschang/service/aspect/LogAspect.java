package com.jsonschang.service.aspect;

import com.jsonschang.common.util.RequestHolder;
import com.jsonschang.common.util.SecurityUtil;
import com.jsonschang.common.util.StringUtils;
import com.jsonschang.common.util.ThrowableUtil;
import com.jsonschang.model.entity.Log;
import com.jsonschang.model.entity.User;
import com.jsonschang.service.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqiyang
 * @date 2020/5/29 - 9:44
 * @description
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    private LogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();


    @Pointcut("@annotation(com.jsonschang.service.annotation.Log)")
    public void logPointcut(){
    }
    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Log log = new Log("INFO",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.insert(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request),joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log("ERROR",System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.insert(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), (ProceedingJoinPoint)joinPoint, log);
    }

    public String getUsername(){
        User bean = (User) SecurityUtil.getBean();
        return bean.getUsername();
    }
}
