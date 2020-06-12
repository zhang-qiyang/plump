package com.jsonschang.controller.controller;

import com.jsonschang.common.customenum.ResponseEnum;
import com.jsonschang.common.dto.ResponseResult;
import com.jsonschang.common.util.VerificationCode;
import com.jsonschang.model.entity.User;
import com.jsonschang.service.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * user(User)表控制层
 *
 * @author JsonsChang
 * @since 2020-05-27 14:08:57
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public User selectOne(String id) {
        User user = this.userService.queryById(id);
        return user;
    }

    @GetMapping("/string")
    public ResponseResult<User> getString(){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseResult<>(ResponseEnum.SUCCESS,"成功",principal);
    }

    @PostMapping("/insert")
    public ResponseResult<Void> insertUser(@RequestBody User user){
        User insert = userService.insert(user);
        if(insert!=null){
            return new ResponseResult<Void>(ResponseEnum.SUCCESS,"注册成功",null);
        }
        return new ResponseResult<Void>(ResponseEnum.FAIL,"注册失败",null);
    }

    /**验证码工具
     * @param request
     * @param resp
     * @throws IOException
     */
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verifyCode", text);
        VerificationCode.output(image,resp.getOutputStream());
    }
}