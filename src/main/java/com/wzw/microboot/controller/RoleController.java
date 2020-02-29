package com.wzw.microboot.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.common.TreeNode;
import com.wzw.microboot.common.WebUtils;
import com.wzw.microboot.entity.Permission;
import com.wzw.microboot.entity.Role;
import com.wzw.microboot.entity.Role;
import com.wzw.microboot.entity.User;
import com.wzw.microboot.service.PermissionService;
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
import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;
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
    /**
     * 根据角色ID加载菜单和权限的树的json串
     */
    @RequestMapping("initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(Integer roleId) {
        //查询所有可用的菜单和权限
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", 1);//可用菜单
        List<Permission> allPermissions = permissionService.list(queryWrapper);
        /**
         * 1,根据角色ID查询当前角色拥有的所有的权限或菜单ID
         * 2,根据查询出来的菜单ID查询权限和菜单数据
         */
        List<Integer> currentRolePermissions=this.roleService.queryRolePermissionIdsByRid(roleId);
        List<Permission> carrentPermissions=null;
        if(currentRolePermissions.size()>0) { //如果有ID就去查询
            queryWrapper.in("id", currentRolePermissions);
            carrentPermissions = permissionService.list(queryWrapper);
        }else {
            carrentPermissions=new ArrayList<>();
        }
        //构造 List<TreeNode>
        List<TreeNode> nodes=new ArrayList<>();
        for (Permission p1 : allPermissions) {
            String checkArr="0";
            for (Permission p2 : carrentPermissions) {
                if(p1.getId()==p2.getId()) {
                    checkArr="1";
                    break;
                }
            }
            Boolean spread=(p1.getOpen()==null||p1.getOpen()==1)?true:false;
            nodes.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), spread, checkArr));
        }
        return new DataGridView(nodes);
    }
    /**
     * 保存角色和菜单权限之间的关系
     */
    @RequestMapping("saveRolePermission")
    public JsonResult saveRolePermission(Integer rid,Integer[] ids) {
        try {
            this.roleService.saveRolePermission(rid,ids);
            return JsonResult.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("保存失败");
        }
    }
}
