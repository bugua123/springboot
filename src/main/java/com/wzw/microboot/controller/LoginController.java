package com.wzw.microboot.controller;

import com.wzw.microboot.common.ActiverUser;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.common.MD5Utils;
import com.wzw.microboot.common.WebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆前端控制器
 *
 */
@RestController
@RequestMapping(value = "login")
public class LoginController {

    /**
     * 登陆前端控制器
     */
    @RequestMapping(value = "login")
    public JsonResult login(String loginname,String pwd) {
        Subject subject= SecurityUtils.getSubject();
        System.out.print("loginname:"+loginname);
        System.out.print("pwd:"+pwd);
        AuthenticationToken token= new UsernamePasswordToken(loginname,pwd);
        System.out.print("token测试:"+token+"结束");
        try {
            subject.login(token);
            ActiverUser activerUser= (ActiverUser) subject.getPrincipal();
            WebUtils.getSession().setAttribute("user",activerUser.getUser());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return  JsonResult.fail("用戶名密码不正确");
        }
        return  JsonResult.success("登陆成功");
    }
}