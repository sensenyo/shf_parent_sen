package com.atguigu.front.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.bo.LoginBo;
import com.atguigu.entity.bo.RegisterBo;
import com.atguigu.service.UserInfoService;
import org.springframework.web.bind.annotation.*;
import result.Result;
import result.ResultCodeEnum;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Reference
    private UserInfoService userInfoService;

    /**
     * 注册
     * @param registerBo
     * @param session
     * @return
     */
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo, HttpSession session) {
        String code = (String) session.getAttribute("CODE");
        //验证码判断
        if (!registerBo.getCode().equalsIgnoreCase(code)) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(registerBo.getNickName());
        userInfo.setPassword(registerBo.getPassword());
        userInfo.setPhone(registerBo.getPhone());
        userInfo.setStatus(1);
        //手机是否重复判断
        if (userInfoService.getByPhone(userInfo) != null) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        userInfoService.insert(userInfo);
        return Result.ok();
    }

    /**
     * 验证码
     * @param phone
     * @param session
     * @return
     */
    @RequestMapping("/sendCode/{phone}")
    public Result register(@PathVariable String phone, HttpSession session) {
        session.setAttribute("CODE", "1111");
        return Result.ok();
    }

    /**
     * 登录
     * @param loginBo
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public Result login(@RequestBody LoginBo loginBo,HttpSession session) {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(loginBo.getPhone());
        userInfo.setPassword(loginBo.getPassword());
        UserInfo userInfo1 = userInfoService.getByPhoneAndPassword(userInfo);
        if (userInfo1 == null)
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        session.setAttribute("USER",userInfo1);
        return Result.ok(userInfo1);
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public Result logout(HttpSession session)
    {
        session.invalidate();
        return Result.ok();
    }
}
