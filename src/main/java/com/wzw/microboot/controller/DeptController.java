package com.wzw.microboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.TreeNode;
import com.wzw.microboot.entity.Dept;
import com.wzw.microboot.entity.Notice;
import com.wzw.microboot.service.DeptService;
import com.wzw.microboot.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "dept")
public class DeptController {

    @Autowired
    private DeptService deptService;
/**
 * 加载部门管理左边树json
 */
@RequestMapping(value = "loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo){
        List<Dept> list = deptService.list();
        List<TreeNode> treeNodes=new ArrayList<>();
        for (Dept dept:list){
            Boolean spreed=dept.getOpen()==1? true:false;
            treeNodes.add(new TreeNode(dept.getId(),dept.getPid(),dept.getTitle(),spreed));
        }
        return new DataGridView(treeNodes);
    }
    /**
     * 查询
     */
    @RequestMapping(value = "loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo){
        IPage<Dept> page=new Page<>(deptVo.getPage(),deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()),"remark",deptVo.getRemark());
        queryWrapper.orderByAsc("ordernum");
        deptService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
}
