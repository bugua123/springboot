package com.wzw.microboot.controller;

import com.wzw.microboot.common.ActiverUser;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.common.MD5Utils;
import com.wzw.microboot.common.WebUtils;
import com.wzw.microboot.entity.Loginfo;
import com.wzw.microboot.service.LoginfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 登陆前端控制器
 *
 */
@RestController
@RequestMapping(value = "login")
public class LoginController {

    @Autowired
    private LoginfoService loginfoService;
    /**
     * 登陆前端控制器
     */
    @RequestMapping(value = "login")
    public JsonResult login(String loginname,String pwd) {
        Subject subject= SecurityUtils.getSubject();
        AuthenticationToken token= new UsernamePasswordToken(loginname,pwd);
        try {
            subject.login(token);
            ActiverUser activerUser= (ActiverUser) subject.getPrincipal();
            WebUtils.getSession().setAttribute("user",activerUser.getUser());

            //记录登陆日志
            Loginfo entity=new Loginfo();
            entity.setLoginname(activerUser.getUser().getName()+"-"+activerUser.getUser().getLoginname());
            entity.setLoginip(WebUtils.getRequest().getRemoteAddr());
            entity.setLogintime(new Date());
            loginfoService.save(entity);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return  JsonResult.fail("用戶名密码不正确");
        }
        return  JsonResult.success("登陆成功");
    }
}