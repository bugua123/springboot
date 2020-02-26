package com.wzw.microboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.common.WebUtils;
import com.wzw.microboot.entity.Notice;
import com.wzw.microboot.entity.User;
import com.wzw.microboot.service.NoticeService;
import com.wzw.microboot.vo.LoginfoVo;
import com.wzw.microboot.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping(value = "/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    /**
     * 查询
     */
    @RequestMapping(value = "loadAllNotice")
    public DataGridView loadAllNotice(NoticeVo noticeVo){
        IPage<Notice> page=new Page<>(noticeVo.getPage(),noticeVo.getLimit());
        QueryWrapper<Notice> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()),"opername",noticeVo.getOpername());
        queryWrapper.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        queryWrapper.le(noticeVo.getEndTime()!=null,"createtime",noticeVo.getEndTime());
        queryWrapper.orderByDesc("createtime");
        this.noticeService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 添加公告
     */
    @RequestMapping(value = "addNotice")
    public JsonResult addNotice(NoticeVo noticeVo){
        try{
            noticeVo.setCreatetime(new Date());
            User user = (User) WebUtils.getSession().getAttribute("user");
            noticeVo.setOpername(user.getName());
            noticeService.save(noticeVo);
            return JsonResult.success("添加公告成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return JsonResult.fail("添加公告失败");
        }
    }
    /**
     * 修改公告
     */
    @RequestMapping(value = "updateNotice")
    public JsonResult updateNotice(NoticeVo noticeVo){
        try{
            noticeVo.setCreatetime(new Date());
            User user = (User) WebUtils.getSession().getAttribute("user");
            noticeVo.setOpername(user.getName());
            noticeService.saveOrUpdate(noticeVo);
            return JsonResult.success("修改公告成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return JsonResult.fail("修改公告失败");
        }
    }
    /**
     * 删除
     */
    @RequestMapping(value = "deleteNotice")
    public JsonResult deleteNotice(Integer id) {
        try {

            noticeService.removeById(id);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("删除失败");
        }
    }
    /**
     * 批量删除
     */
    @RequestMapping(value = "batchDeleteNotice")
    public JsonResult batchDeleteNotice(LoginfoVo loginfoVo){
        try {
            Collection<Serializable> idList=new ArrayList<Serializable>();
            for(Integer id:loginfoVo.getIds()){
                idList.add(id);
            }
            noticeService.removeByIds(idList);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("删除失败");
        }
    }
}
