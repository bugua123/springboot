package com.wzw.microboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "sys")
public class SystemController {

    /**
     * 跳转到登录页面
     *
     */
    @RequestMapping(value = "login")
    public String toLogin(){
        return "/system/index/login";
    }
    /**
     * 跳转到首页
     */
    @RequestMapping(value = "index")
    public String index(){
        return "system/index/index";
    }
    /**
     * 跳转到控制台
     */
    @RequestMapping(value = "toDeskManager")
    public String toDeskManager(){
        return "system/index/deskManager";
    }
}
