package com.wzw.microboot.controller;

import com.wzw.microboot.common.MD5Utils;
import com.wzw.microboot.common.UUIDUtils;
import com.wzw.microboot.entity.User;
import com.wzw.microboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/selectall")
    public List<User> selectAll(){
       return userService.selectAll();
    }

    /**
     * 根据ID获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable String id) {
        return userService.getOne(id);
    }

    /**
     * 更新用户信息，参数User对象字符串
     * @param user
     */
    @RequestMapping(value="/update",method=RequestMethod.PUT,produces = "application/json;charset=UTF-8")
    public void update(@RequestBody User user){
        userService.update(user);
    }

    /**
     * 删除ID用户
     * @param id
     */
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public void delete(@PathVariable String id){
        userService.delete(id);
    }

    /**
     * 添加用户信息 参数User json 对象
     * @param user
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
//    @PostMapping(value="/add",produces = "application/json;charset=UTF-8")
    public void insert(@RequestBody User user) throws Exception {
        user.setId(UUIDUtils.getUUID());
        System.out.print(user);
        userService.insert(user);
    }
}
