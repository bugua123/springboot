package com.wzw.microboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.entity.Loginfo;
import com.wzw.microboot.service.LoginfoService;
import com.wzw.microboot.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping(value = "/loginfo")
public class LoginfoController {
    @Autowired
    LoginfoService loginfoService;

    /**
     * 全查询
     */
@RequestMapping(value = "loadAllLoginfo")
    public DataGridView loadAllLoginfo(LoginfoVo loginfoVo){
        IPage<Loginfo> page=new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
        QueryWrapper<Loginfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        queryWrapper.ge(loginfoVo.getStartTime()!=null,"logintime",loginfoVo.getStartTime());
        queryWrapper.le(loginfoVo.getEndTime()!=null,"logintime",loginfoVo.getEndTime());
        queryWrapper.orderByDesc("logintime");
        loginfoService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 删除
     */
    @RequestMapping(value = "deleteLoginfo")
    public JsonResult deleteLoginfo(Integer id) {
        try {
            loginfoService.removeById(id);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("删除失败");
        }
    }
    /**
     * 批量删除
     */
    @RequestMapping(value = "batchDeleteLoginfo")
    public JsonResult batchDeleteLoginfo(LoginfoVo loginfoVo){
        try {
            Collection<Serializable> idList=new ArrayList<Serializable>();
            for(Integer id:loginfoVo.getIds()){
                idList.add(id);
            }
            loginfoService.removeByIds(idList);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("删除失败");
        }
    }

}
