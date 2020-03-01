package com.wzw.microboot.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.common.*;
import com.wzw.microboot.constant.Constast;
import com.wzw.microboot.constant.ErrorConstant;
import com.wzw.microboot.constant.WebConst;
import com.wzw.microboot.entity.Dept;
import com.wzw.microboot.entity.Role;
import com.wzw.microboot.entity.User;
import com.wzw.microboot.service.DeptService;
import com.wzw.microboot.service.RoleService;
import com.wzw.microboot.service.UserService;
import com.wzw.microboot.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;
    @Autowired
    private RoleService roleService;
//
//    /**
//     * 查询所有
//     * @return
//     */
//    @RequestMapping("/selectall")
//    public List<User> selectAll(){
//       return userService.selectAll();
//    }

  @RequestMapping(value = "loadAllUser")
  public DataGridView loadAllUser(UserVo userVo){
      IPage<User> page=new Page<>(userVo.getPage(),userVo.getLimit());
      QueryWrapper<User> queryWrapper=new QueryWrapper<>();
      queryWrapper.eq(StringUtils.isNotBlank(userVo.getName()), "loginname", userVo.getName()).or().eq(StringUtils.isNotBlank(userVo.getName()), "name", userVo.getName());
      queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()), "address", userVo.getAddress());
      queryWrapper.eq("type", 1);//查询系统用户
      queryWrapper.eq(userVo.getDeptid()!=null, "deptid",userVo.getDeptid());
      this.userService.page(page, queryWrapper);
      List<User> list = page.getRecords();
      for (User user : list) {
          Integer deptid = user.getDeptid();
          if(deptid!=null) {
              Dept one =deptService.getById(deptid);
              user.setDeptname(one.getTitle());
          }
          Integer mgr = user.getMgr();
          if(mgr!=null) {
              User one = this.userService.getById(mgr);
              user.setLeadername(one.getName());
          }
      }
      return new DataGridView(page.getTotal(), list);
  }

    /**
     * 加载最大的排序码
     * @return
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String,Object> loadUserMaxOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<User> page=new Page<>(1, 1);
        List<User> list = this.userService.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getOrdernum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }
    /**
     * 根据部门ID查询用户
     */
  @RequestMapping("loadUserByDeptId")
    public DataGridView loadUserByDeptId(Integer deptid){
      QueryWrapper<User> queryWrapper=new QueryWrapper<>();
      queryWrapper.eq(deptid!=null,"deptid",deptid);
      queryWrapper.eq("available",1);//可用
      queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);
      List<User> list = userService.list(queryWrapper);
      return new  DataGridView(list);
  }

    /**
     * 把用户名转成拼音
     */
    @RequestMapping("changeChineseToPinyin")
    public Map<String,Object> changeChineseToPinyin(String username){
        Map<String,Object> map=new HashMap<>();
        if(null!=username) {
            map.put("value", PinyinUtils.getPingYin(username));
        }else {
            map.put("value", "");
        }
        return map;
    }

    /**
     * 添加用户
     */
    @RequestMapping(value = "addUser")
    public JsonResult addUser(UserVo userVo){
        try {
            userVo.setType(Constast.USER_TYPE_NORMAL);//设置用户类型
            userVo.setHiredate(new Date());
            String salt= IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            userService.save(userVo);
            return JsonResult.success("添加用户成功");
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.fail("添加用户失败");
        }
    }
    /**
     * 修改用户
     */
    @RequestMapping(value = "updateUser")
    public JsonResult updateUser(UserVo userVo){
        try {
            userService.updateById(userVo);
            return JsonResult.success("修改用户成功");
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.fail("修改用户失败");
        }
    }
    /**
     * 根据用户ID查询用户
     */
    @RequestMapping("loadUserById")
    public DataGridView loadUserById(Integer id){
        return new DataGridView(userService.getById(id));
    }
    @RequestMapping(value = "deleteUser")
    public JsonResult deleteUser( Integer id){
        try {
            this.userService.removeById(id);

            return JsonResult.success("删除用户成功");
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.fail("修改用户失败");
        }
    }

    @RequestMapping(value = "resetPwd")
    public JsonResult resetPwd(Integer id){
        try {
            User user=new User();
            user.setId(id);
            String salt=IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            userService.updateById(user);
            return JsonResult.success("重置密码成功");
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.fail("重置密码失败");
        }

    }

    /**
     * 根据用户ID查询角色病选择已经拥有的角色
     * @param id
     * @return
     */
    @RequestMapping(value = "initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id){
        //1,查询所有可用的角色
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> listMaps = this.roleService.listMaps(queryWrapper);

        //2,查询当前用户拥有的角色ID集合
        List<Integer> currentUserRoleIds=this.roleService.queryUserRoleIdsByUid(id);
        for (Map<String, Object> map : listMaps) {
            Boolean LAY_CHECKED=false;
            Integer roleId=(Integer) map.get("id");
            for (Integer rid : currentUserRoleIds) {
                if(rid==roleId) {
                    LAY_CHECKED=true;
                    break;
                }
            }
            map.put("LAY_CHECKED", LAY_CHECKED);
        }
        return new DataGridView(Long.valueOf(listMaps.size()), listMaps);
    }
    /**
     * 保存用户和角色之间关系
     */
    @RequestMapping(value = "saveUserRole")
    public JsonResult saveUserRole(Integer uid,Integer[] ids){
        try {
          userService.saveUserRole(uid,ids);
            return JsonResult.success("分配权限成功");
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.fail("分配权限失败");
        }
    }
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
