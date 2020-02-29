package com.wzw.microboot.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.common.WebUtils;
import com.wzw.microboot.entity.Role;
import com.wzw.microboot.entity.Role;
import com.wzw.microboot.entity.User;
import com.wzw.microboot.service.RoleService;
import com.wzw.microboot.service.UserService;
import com.wzw.microboot.vo.LoginfoVo;
import com.wzw.microboot.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 查询
     */
    @RequestMapping(value = "loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo){
        IPage<Role> page=new Page<>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()),"remark",roleVo.getRemark());
        queryWrapper.eq(roleVo.getAvailable()!=null,"available",roleVo.getAvailable());
        queryWrapper.orderByDesc("createtime");
        this.roleService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 添加角色
     */
    @RequestMapping(value = "addRole")
    public JsonResult addRole(RoleVo roleVo){
        try{
            roleVo.setCreatetime(new Date());
            roleService.save(roleVo);
            return JsonResult.success("添加角色成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return JsonResult.fail("添加角色失败");
        }
    }
    /**
     * 修改角色
     */
    @RequestMapping(value = "updateRole")
    public JsonResult updateRole(RoleVo roleVo){
        try{
            roleVo.setCreatetime(new Date());
            roleService.saveOrUpdate(roleVo);
            return JsonResult.success("修改角色成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return JsonResult.fail("修改角色失败");
        }
    }
    /**
     * 删除
     */
    @RequestMapping(value = "deleteRole")
    public JsonResult deleteRole(Integer id) {
        try {
            roleService.removeById(id);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("删除失败");
        }
    }

}
