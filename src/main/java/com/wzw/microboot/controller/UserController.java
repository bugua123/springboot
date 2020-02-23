package com.wzw.microboot.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.common.MD5Utils;
import com.wzw.microboot.common.UUIDUtils;
import com.wzw.microboot.constant.ErrorConstant;
import com.wzw.microboot.constant.WebConst;
import com.wzw.microboot.entity.User;
import com.wzw.microboot.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;
//
//    /**
//     * 查询所有
//     * @return
//     */
//    @RequestMapping("/selectall")
//    public List<User> selectAll(){
//       return userService.selectAll();
//    }
//
//    /**
//     * 根据ID获取用户信息
//     * @param id
//     * @return
//     */
////    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
////    public JsonResult<User> getOne(@PathVariable String id) {
////
////        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
////        queryWrapper.eq("id",id);
////
////        User user= userService.getOne(queryWrapper);
////
////        JsonResult<User> u=new JsonResult<>();
////        if(user!=null) {
////            u.setData(user);
////            u.setMsg("获取成功");
////            u.setCode("200");
////        }
////        else
////        {  u.setData(user);
////            u.setMsg("用户为空");
////            u.setCode("2001");}
////        return u;
////    }
//    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
//    public JsonResult<User> getOneByName(@PathVariable String name) {
//        User user= userService.getOneByName(name);
//
//        JsonResult<User> u=new JsonResult<>();
//        if(user!=null) {
//            u.setData(user);
//            u.setMsg("获取成功");
//            u.setCode("200");
//        }
//        else
//        {  u.setData(user);
//            u.setMsg("用户为空");
//            u.setCode("2001");}
//        return u;
//    }
//    /**
//     * 更新用户信息，参数User对象字符串
//     * @param user
//     */
//    @RequestMapping(value="/update",method=RequestMethod.PUT,produces = "application/json;charset=UTF-8")
//    public void update(@RequestBody User user){
//        userService.update(user);
//    }
//
//    /**
//     * 删除ID用户
//     * @param id
//     */
//    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
//    public void delete(@PathVariable String id){
//        userService.delete(id);
//    }
//
//    /**
//     * 添加用户信息 参数User json 对象
//     * @param user
//     * @throws Exception
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
////    @PostMapping(value="/add",produces = "application/json;charset=UTF-8")
//    public JsonResult<User> insert(@RequestBody User user) throws Exception {
//        user.setId(UUIDUtils.getUUID());
//        System.out.print(user);
//        user.setPassWord(MD5Utils.encode(user.getPassWord()));
//        userService.insert(user);
//        JsonResult<User> u=new JsonResult<>();
//        u.setData(user);
//        u.setMsg("添加成功");
//        u.setCode("200");
//        return u;
//    }
//
//    /**
//     * 登录验证 此方法采用了get 传参方式，明文显示，需要改成post 不显示
//     * @param userName
//     * @param passWord
//     * @param request
//     * @param session
//     * @return
//     */
//    @RequestMapping(value="/login")
//    public JsonResult login(@RequestParam String userName, @RequestParam String passWord, HttpServletRequest request, HttpSession session) {
//        User user=null;
//
//        if (!StringUtils.isNotBlank(userName) || !StringUtils.isNotBlank(passWord)) {
//            System.out.print("========"+userName);   System.out.print("========"+passWord);
//            return JsonResult.fail(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);
//        }
//        else
//        {
//            try {
//               user= userService.checkUser(userName,MD5Utils.encode(passWord) );
//                System.out.print("========"+userName);   System.out.print("========"+MD5Utils.encode(passWord));
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (user==null)
//            {
//                return JsonResult.fail(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);
//            }
//            else
//            {
//                session.setAttribute(WebConst.LOGIN_SESSION_KEY,user);
//                JsonResult<User> jsonResult =new JsonResult<>();
//                jsonResult.setMsg("登陆成功");
//                user.setPassWord("");//密码设置为空 保证安全性
//                jsonResult.setData(user);
//
//                return jsonResult;
//
//            }
//        }
//    }

    /**
     * 添加用户
     */
    //    @RequestMapping(value = "/add", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
////    @PostMapping(value="/add",produces = "application/json;charset=UTF-8")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JsonResult addUser(@RequestBody User user) {
        try {
            user.setType(1);//设置类型
            user.setName(user.getLoginname());
            user.setHiredate(new Date());
            String salt= IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);//设置盐
            user.setPwd(new Md5Hash("123456", salt, 2).toString());//设置密码
            this.userService.save(user);
            return JsonResult.success("添加用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("添加用户失败");
        }
    }
}
