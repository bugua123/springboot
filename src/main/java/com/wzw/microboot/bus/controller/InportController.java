package com.wzw.microboot.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzw.microboot.bus.domain.Goods;
import com.wzw.microboot.bus.domain.Inport;
import com.wzw.microboot.bus.domain.Provider;
import com.wzw.microboot.bus.service.GoodsService;
import com.wzw.microboot.bus.service.InportService;
import com.wzw.microboot.bus.service.ProviderService;
import com.wzw.microboot.bus.vo.InportVo;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.common.WebUtils;
import com.wzw.microboot.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "inport")
public class InportController {

    @Autowired
    private InportService inportService;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("loadAllInport")
    public DataGridView loadAllInport(InportVo inportVo) {
        IPage<Inport> page = new Page<>(inportVo.getPage(), inportVo.getLimit());
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(inportVo.getProviderid()!=null&&inportVo.getProviderid()!=0,"providerid",inportVo.getProviderid());
        queryWrapper.eq(inportVo.getGoodsid()!=null&&inportVo.getGoodsid()!=0,"goodsid",inportVo.getGoodsid());
        queryWrapper.ge(inportVo.getStartTime()!=null, "inporttime", inportVo.getStartTime());
        queryWrapper.le(inportVo.getEndTime()!=null, "inporttime", inportVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getOperateperson()), "operateperson", inportVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getRemark()), "remark", inportVo.getRemark());
        queryWrapper.orderByDesc("inporttime");
        this.inportService.page(page, queryWrapper);
        List<Inport> records = page.getRecords();
        for (Inport inport : records) {
            Provider provider = this.providerService.getById(inport.getProviderid());
            if(null!=provider) {
                inport.setProvidername(provider.getProvidername());
            }
            Goods goods = this.goodsService.getById(inport.getGoodsid());
            if(null!=goods) {
                inport.setGoodsname(goods.getGoodsname());
                inport.setSize(goods.getSize());
            }
        }
        return new DataGridView(page.getTotal(), records);
    }
    /**
     * 添加
     */
    @RequestMapping("addInport")
    public JsonResult addInport(InportVo inportVo) {
        try {
            inportVo.setInporttime(new Date());
            User user=(User) WebUtils.getSession().getAttribute("user");
            inportVo.setOperateperson(user.getName());
            this.inportService.save(inportVo);
            return JsonResult.success("添加商品成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("添加商品失败");
        }
    }
    /**
     * 修改
     */
    @RequestMapping("updateInport")
    public JsonResult updateInport(InportVo inportVo) {
        try {
            this.inportService.updateById(inportVo);
            return JsonResult.success("修改商品成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("修改商品失败");

        }
    }

    /**
     * 删除
     */
    @RequestMapping("deleteInport")
    public JsonResult deleteInport(Integer id) {
        try {
            this.inportService.removeById(id);
            return JsonResult.success("删除商品成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail("删除商品失败");

        }
    }
}
