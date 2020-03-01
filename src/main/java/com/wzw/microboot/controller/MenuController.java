package com.wzw.microboot.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.common.*;
import com.wzw.microboot.constant.Constast;
import com.wzw.microboot.entity.Dept;
import com.wzw.microboot.entity.Permission;
import com.wzw.microboot.entity.User;
import com.wzw.microboot.service.PermissionService;
import com.wzw.microboot.service.RoleService;
import com.wzw.microboot.vo.DeptVo;
import com.wzw.microboot.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo){
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constast.TYPE_MNEU);
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        User user= (User) WebUtils.getSession().getAttribute("user");
        List<Permission> list=null;
        if(user.getType()==0) {
            //管理员
            list = permissionService.list(queryWrapper);
        }
        else {
            //根据用户ID+角色+权限查询
           Integer userId=user.getId();
           //根据用户ID查询角色
            List<Integer> currentUserRoleIds=roleService.queryUserRoleIdsByUid(userId);
           //根据角色ID取权限和菜单ID
            Set<Integer> pids=new HashSet<>();//Set 不能重复
            for (Integer rid:currentUserRoleIds) {
                List<Integer> permissionIds=roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            //根据角色ID查询权限
            if(pids.size()>0){
                queryWrapper.in("id",pids);
                list=permissionService.list(queryWrapper);
            }else {
                list=new ArrayList<>();
            }
        }
        List<TreeNode> treeNodes=new ArrayList<>();
        for (Permission permission : list) {
            Integer id=permission.getId();
            Integer pid=permission.getPid();
            String title=permission.getTitle();
            String icon=permission.getIcon();
            String href=permission.getHref();
            Boolean spread=permission.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(id, pid, title, icon, href, spread));
        }
        //构造层级关系
        List<TreeNode> list2 = TreeNodeBuilder.build(treeNodes, 1);
        return new DataGridView(list2);
    }
    /***********菜单管理开始***********/

/**
        * 加载菜单管理左边树json
 */
    @RequestMapping(value = "loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(PermissionVo permissionVo){

        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","menu");
        List<Permission> list = permissionService.list(queryWrapper);
        List<TreeNode> treeNodes=new ArrayList<>();
        for (Permission permission:list){
            Boolean spreed=permission.getOpen()==1? true:false;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spreed));
        }
        return new DataGridView(treeNodes);
    }
    /**
     * 查询
     */
    @RequestMapping(value = "loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo){
        IPage<Permission> page=new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(permissionVo.getId()!=null, "id", permissionVo.getId()).or().eq(permissionVo.getId()!=null,"pid", permissionVo.getId());
        queryWrapper.eq("type","menu");
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.orderByAsc("ordernum");
        permissionService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 加载最大的排序码
     * @return
     */
    @RequestMapping("loadMenuMaxOrderNum")
    public Map<String,Object> loadMenuMaxOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();

        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Permission> page=new Page<>(1, 1);
        List<Permission> list = this.permissionService.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getOrdernum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }
    /**
     * 添加
     * @param permissionVo
     * @return
     */
    @RequestMapping("addMenu")
    public JsonResult addMenu(PermissionVo permissionVo) {
        try {
            permissionVo.setType("menu");
            this.permissionService.save(permissionVo);
            return JsonResult.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("添加失败");

        }
    }
    /**
     * 修改
     * @param permissionVo
     * @return
     */
    @RequestMapping("updateMenu")
    public JsonResult updateMenu(PermissionVo permissionVo) {
        try {
            this.permissionService.updateById(permissionVo);
            return JsonResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("修改失败");
        }
    }
    /**
     * 查询当前的ID的菜单有没有子菜单
     */
    @RequestMapping("checkMenuHasChildrenNode")
    public Map<String,Object> checkMenuHasChildrenNode(PermissionVo permissionVo){
        Map<String, Object> map=new HashMap<String, Object>();

        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid", permissionVo.getId());
        List<Permission> list = this.permissionService.list(queryWrapper);
        if(list.size()>0) {
            map.put("value", true);
        }else {
            map.put("value", false);
        }
        return map;
    }

    /**
     * 删除
     * @param permissionVo
     * @return
     */
    @RequestMapping("deleteMenu")
    public JsonResult deleteMenu(PermissionVo permissionVo) {
        try {
            this.permissionService.removeById(permissionVo.getId());
            return JsonResult.success("删除成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("删除失败");
        }
    }

    /***********菜单管理结束***********/
}
