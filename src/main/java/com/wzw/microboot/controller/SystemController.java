package com.wzw.microboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sys")
public class SystemController {

    /**
     * 跳转到登录页面
     *
     */
    @RequestMapping(value = "/toLogin")
    public String login(){
        System.out.print("进入跳转");
        return "/system/index/login";
    }
}
