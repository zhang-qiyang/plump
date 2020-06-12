package com.jsonschang.service.service;

import com.github.pagehelper.PageInfo;
import com.jsonschang.model.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 系统日志(Log)表服务接口
 *
 * @author JsonsChang
 * @since 2020-05-27 14:03:58
 */
public interface LogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Log queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param pageNo 查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    PageInfo<Log> queryAllByLimit(int pageNo, int pageSize);

    /**
     * 新增数据
     *
     *
     * @param username
     * @param browser
     * @param ip
     * @param joinPoint
     * @param log 实例对象
     * @return 实例对象
     */
    Log insert(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log);

    /**
     * 修改数据
     *
     * @param log 实例对象
     * @return 实例对象
     */
    Log update(Log log);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}