package com.wzw.microboot.controller;


import com.wzw.microboot.cache.CachePool;
import com.wzw.microboot.common.CacheBean;
import com.wzw.microboot.common.DataGridView;
import com.wzw.microboot.common.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


@RestController
@RequestMapping("cache")
public class CacheController {

	public static volatile Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;
	
	/**
	 * 查询所有缓存
	 */
	@RequestMapping("loadAllCache")
	public DataGridView loadAllCache() {
		List<CacheBean> list=new ArrayList<>();
		
		Set<Entry<String, Object>> entrySet = CACHE_CONTAINER.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			list.add(new CacheBean(entry.getKey(), entry.getValue()));
		}
		return new DataGridView(list);
	}
	
	/**
	 * 删除缓存
	 */
	@RequestMapping("deleteCache")
	public JsonResult deleteCache(String key) {
		CachePool.removeCacheByKey(key);
		return JsonResult.success("删除缓存");
	}
	
	/**
	 * 清空缓存
	 */
	@RequestMapping("removeAllCache")
	public JsonResult removeAllCache() {
		CachePool.removeAll();
		return  JsonResult.success("删除缓存");
	}
	/**
	 * 同步缓存
	 */
	@RequestMapping("syncCache")
	public JsonResult syncCache() {
		CachePool.syncData();
		return JsonResult.success("同步缓存");
	}

}
