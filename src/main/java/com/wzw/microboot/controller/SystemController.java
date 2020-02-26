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
    /**
     * 跳转到日志管理
     */
    @RequestMapping(value = "toLoginfoManager")
    public String toLoginfoManager(){
        return "system/loginfo/loginfoManager";
    }

    /**
     * 跳转到公告
     */
    @RequestMapping(value = "toNoticeManager")
    public String toNoticeManager(){
        return "system/notice/noticeManager";
    }

    /**
     * 跳转到部门管理
     */
    @RequestMapping(value = "toDeptManager")
    public String toDeptManager(){
        return "system/dept/deptManager";
    }
    /**
     * 跳转到部门管理左边
     */
    @RequestMapping(value = "toDeptLeft")
    public String toDeptLeft(){
        return "system/dept/deptLeft";
    }
    /**
     * 跳转到部门管理右边
     */
    @RequestMapping(value = "toDeptRight")
    public String toDeptRight(){
        return "system/dept/deptRight";
    }
}
