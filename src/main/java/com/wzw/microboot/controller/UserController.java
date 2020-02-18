package com.wzw.microboot.controller;

import com.wzw.microboot.entity.User;
import com.wzw.microboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/selectall")
    public List<User> selectAll(){
       return userService.selectAll();
    }


}
