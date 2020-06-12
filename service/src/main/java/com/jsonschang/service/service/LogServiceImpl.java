package com.jsonschang.service.service;

import cn.hutool.json.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsonschang.common.util.StringUtils;
import com.jsonschang.dao.dao.LogDao;
import com.jsonschang.model.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 系统日志(Log)表服务实现类
 *
 * @author JsonsChang
 * @since 2020-05-27 14:03:59
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Log queryById(Long id) {
        return this.logDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param pageNo 查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    @Override
    public PageInfo<Log> queryAllByLimit(int pageNo, int pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        List<Log> logs = logDao.queryAllByLimit();
        return new PageInfo<>(logs);
    }

    /**
     * 新增数据
     * @param username
     * @param browser
     * @param ip
     * @param joinPoint
     * @param log 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Log insert(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.jsonschang.service.annotation.Log aopLog = method.getAnnotation(com.jsonschang.service.annotation.Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

        String loginPath = "login";
        if(loginPath.equals(signature.getName())){
            try {
                assert argValues != null;
                username = new JSONObject(argValues[0]).get("username").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        this.logDao.insert(log);
        return log;
    }

    /**
     * 修改数据
     *
     * @param log 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Log update(Log log) {
        this.logDao.update(log);
        return this.queryById(log.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(Long id) {
        return this.logDao.deleteById(id) > 0;
    }
}