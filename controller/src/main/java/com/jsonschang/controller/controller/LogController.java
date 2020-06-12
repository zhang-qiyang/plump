package com.jsonschang.controller.controller;

import com.github.pagehelper.PageInfo;
import com.jsonschang.common.customenum.ResponseEnum;
import com.jsonschang.common.dto.ResponseResult;
import com.jsonschang.model.entity.Log;
import com.jsonschang.service.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 系统日志(Log)表控制层
 *
 * @author JsonsChang
 * @since 2020-05-27 14:04:12
 */
@RestController
@RequestMapping("/log")
public class LogController {
    /**
     * 服务对象
     */
    @Autowired
    private LogService logService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Log selectOne(Long id) {
        return this.logService.queryById(id);
    }

    @GetMapping("/getLogsByPage")
//    @PreAuthorize("hasRole('user') or hasAuthority('admin')")
    public ResponseResult<PageInfo<Log>> getLogsByPage(Integer pageNo,Integer pageSize){
        PageInfo<Log> logPageInfo = logService.queryAllByLimit(pageNo, pageSize);
        return new ResponseResult<>(ResponseEnum.SUCCESS,"查询成功",logPageInfo);
    }


}