package com.wzw.microboot.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wzw.microboot.bus.domain.Customer;
import com.wzw.microboot.bus.service.CustomerService;
import com.wzw.microboot.bus.vo.CustomerVo;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 老雷
 * @since 2019-09-27
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * 查询
	 */
	@RequestMapping("loadAllCustomer")
	public DataGridView loadAllCustomer(CustomerVo customerVo) {
		IPage<Customer> page = new Page<>(customerVo.getPage(), customerVo.getLimit());
		QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StringUtils.isNotBlank(customerVo.getCustomername()), "customername",
				customerVo.getCustomername());
		queryWrapper.like(StringUtils.isNotBlank(customerVo.getPhone()), "phone", customerVo.getPhone());
		queryWrapper.like(StringUtils.isNotBlank(customerVo.getConnectionperson()), "connectionperson",
				customerVo.getConnectionperson());
		this.customerService.page(page, queryWrapper);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	/**
	 * 添加
	 */
	@RequestMapping("addCustomer")
	public JsonResult addCustomer(CustomerVo customerVo) {
		try {
			this.customerService.save(customerVo);
			return JsonResult.success("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("添加失败");

		}
	}

	/**
	 * 修改
	 */
	@RequestMapping("updateCustomer")
	public JsonResult updateCustomer(CustomerVo customerVo) {
		try {
			this.customerService.updateById(customerVo);
			return JsonResult.success("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("修改失败");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("deleteCustomer")
	public JsonResult deleteCustomer(Integer id) {
		try {
			this.customerService.removeById(id);
			return JsonResult.success("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("添加失败");
		}
	}

	/**
	 * 批量删除
	 */
	@RequestMapping("batchDeleteCustomer")
	public JsonResult batchDeleteCustomer(CustomerVo customerVo) {
		try {
			Collection<Serializable> idList = new ArrayList<Serializable>();
			for (Integer id : customerVo.getIds()) {
				idList.add(id);
			}
			this.customerService.removeByIds(idList);
			return JsonResult.success("批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.fail("批量删除失败");

		}
	}
}
