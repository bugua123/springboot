package com.wzw.microboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.common.TreeNode;
import com.wzw.microboot.entity.Dept;
import com.wzw.microboot.service.DeptService;
import com.wzw.microboot.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
        queryWrapper.eq(deptVo.getId()!=null, "id", deptVo.getId()).or().eq(deptVo.getId()!=null,"pid", deptVo.getId());

        queryWrapper.orderByAsc("ordernum");
        deptService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 加载最大的排序码
     * @return
     */
    @RequestMapping("loadDeptMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();

        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Dept> page=new Page<>(1, 1);
        List<Dept> list = this.deptService.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getOrdernum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }
    /**
     * 添加
     * @param deptVo
     * @return
     */
    @RequestMapping("addDept")
    public JsonResult addDept(DeptVo deptVo) {
        try {
            deptVo.setCreatetime(new Date());
            this.deptService.save(deptVo);
            return JsonResult.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("添加失败");

        }
    }


    /**
     * 修改
     * @param deptVo
     * @return
     */
    @RequestMapping("updateDept")
    public JsonResult updateDept(DeptVo deptVo) {
        try {
            this.deptService.updateById(deptVo);
            return JsonResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("修改失败");
        }
    }


    /**
     * 查询当前的ID的部门有没有子部门
     */
    @RequestMapping("checkDeptHasChildrenNode")
    public Map<String,Object> checkDeptHasChildrenNode(DeptVo deptVo){
        Map<String, Object> map=new HashMap<String, Object>();

        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid", deptVo.getId());
        List<Dept> list = this.deptService.list(queryWrapper);
        if(list.size()>0) {
            map.put("value", true);
        }else {
            map.put("value", false);
        }
        return map;
    }

    /**
     * 删除
     * @param deptVo
     * @return
     */
    @RequestMapping("deleteDept")
    public JsonResult deleteDept(DeptVo deptVo) {
        try {
            this.deptService.removeById(deptVo.getId());
            return JsonResult.success("删除成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("删除失败");
        }
    }
}
