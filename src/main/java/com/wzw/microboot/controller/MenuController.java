package com.wzw.microboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.TreeNode;
import com.wzw.microboot.common.TreeNodeBuilder;
import com.wzw.microboot.common.WebUtils;
import com.wzw.microboot.entity.Permission;
import com.wzw.microboot.entity.User;
import com.wzw.microboot.service.PermissionService;
import com.wzw.microboot.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo){
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","menu");
        queryWrapper.eq("available","1");
        User user= (User) WebUtils.getSession().getAttribute("user");
        List<Permission> list=null;
        if(user.getType()==0) {
            list = permissionService.list(queryWrapper);
        }
        else {
            list = permissionService.list(queryWrapper);
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
}
