package com.wzw.microboot.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wzw.microboot.bus.domain.Provider;
import com.wzw.microboot.bus.service.ProviderService;
import com.wzw.microboot.bus.vo.ProviderVo;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import com.wzw.microboot.constant.Constast;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/provider")
public class ProviderController {

	@Autowired
	private ProviderService providerService;

	/**
	 * 查询
	 */
	@RequestMapping("loadAllProvider")
	public DataGridView loadAllProvider(ProviderVo providerVo) {
		IPage<Provider> page = new Page<>(providerVo.getPage(), providerVo.getLimit());
		QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StringUtils.isNotBlank(providerVo.getProvidername()), "providername",
				providerVo.getProvidername());
		queryWrapper.like(StringUtils.isNotBlank(providerVo.getPhone()), "phone", providerVo.getPhone());
		queryWrapper.like(StringUtils.isNotBlank(providerVo.getConnectionperson()), "connectionperson",
				providerVo.getConnectionperson());
		this.providerService.page(page, queryWrapper);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	/**
	 * 添加
	 */
	@RequestMapping("addProvider")
	public JsonResult addProvider(ProviderVo providerVo) {
		try {
			this.providerService.save(providerVo);
			return JsonResult.success("供应商添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("供应商添加失败");

		}
	}

	/**
	 * 修改
	 */
	@RequestMapping("updateProvider")
	public JsonResult updateProvider(ProviderVo providerVo) {
		try {
			this.providerService.updateById(providerVo);
			return JsonResult.success("供应商修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("供应商修改失败");

		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("deleteProvider")
	public JsonResult deleteProvider(Integer id) {
		try {
			this.providerService.removeById(id);
			return JsonResult.success("供应商删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("供应商删除失败");

		}
	}

	/**
	 * 批量删除
	 */
	@RequestMapping("batchDeleteProvider")
	public JsonResult batchDeleteProvider(ProviderVo providerVo) {
		try {
			Collection<Serializable> idList = new ArrayList<Serializable>();
			for (Integer id : providerVo.getIds()) {
				idList.add(id);
			}
			this.providerService.removeByIds(idList);
			return JsonResult.success("供应商批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("供应商批量删除失败");

		}
	}
	
	
	/**
	 * 加载所有可用的供应商
	 */
	@RequestMapping("loadAllProviderForSelect")
	public DataGridView loadAllProviderForSelect() {
		QueryWrapper<Provider> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
		List<Provider> list = this.providerService.list(queryWrapper);
		return new DataGridView(list);
	}
}

