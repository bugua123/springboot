package com.wzw.microboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.common.TreeNode;
import com.wzw.microboot.entity.Permission;
import com.wzw.microboot.service.PermissionService;
import com.wzw.microboot.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {
    
    @Autowired
    PermissionService permissionService;
    /***********权限管理开始***********/

    /**
     * 加载权限管理左边树json
     */
    @RequestMapping(value = "loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(PermissionVo permissionVo){

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
    @RequestMapping(value = "loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo){
        IPage<Permission> page=new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","permission");
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPercode()),"percode",permissionVo.getPercode());
        queryWrapper.eq(permissionVo.getId()!=null,"pid", permissionVo.getId());
        queryWrapper.orderByAsc("ordernum");
        permissionService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 加载最大的排序码
     * @return
     */
    @RequestMapping("loadPermissionMaxOrderNum")
    public Map<String,Object> loadPermissionMaxOrderNum(){
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
    @RequestMapping("addPermission")
    public JsonResult addPermission(PermissionVo permissionVo) {
        try {
            permissionVo.setType("Permission");
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
    @RequestMapping("updatePermission")
    public JsonResult updatePermission(PermissionVo permissionVo) {
        try {
            this.permissionService.updateById(permissionVo);
            return JsonResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("修改失败");
        }
    }
//    /**
//     * 查询当前的ID的权限有没有子权限
//     */
//    @RequestMapping("checkPermissionHasChildrenNode")
//    public Map<String,Object> checkPermissionHasChildrenNode(PermissionVo permissionVo){
//        Map<String, Object> map=new HashMap<String, Object>();
//
//        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("pid", permissionVo.getId());
//        List<Permission> list = this.permissionService.list(queryWrapper);
//        if(list.size()>0) {
//            map.put("value", true);
//        }else {
//            map.put("value", false);
//        }
//        return map;
//    }

    /**
     * 删除
     * @param permissionVo
     * @return
     */
    @RequestMapping("deletePermission")
    public JsonResult deletePermission(PermissionVo permissionVo) {
        try {
            this.permissionService.removeById(permissionVo.getId());
            return JsonResult.success("删除成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("删除失败");
        }
    }

    /***********权限管理结束***********/
}

