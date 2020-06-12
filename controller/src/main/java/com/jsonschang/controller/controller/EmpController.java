package com.jsonschang.controller.controller;

import com.jsonschang.common.customenum.ResponseEnum;
import com.jsonschang.common.dto.ResponseResult;
import com.jsonschang.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqiyang
 * @date 2020/5/31 - 18:47
 * @description
 */
@RestController
@RequestMapping("/employee")
public class EmpController {

    @GetMapping("/basic/string")
    public ResponseResult<String> getString(){
        return new ResponseResult<>(ResponseEnum.SUCCESS,"成功","String");
    }
}
