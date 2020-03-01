package com.wzw.microboot.bus.vo;


import com.wzw.microboot.bus.domain.Provider;

import java.io.Serializable;
import java.util.Arrays;

public class ProviderVo extends Provider implements Serializable {


	private static final long serialVersionUID = 1L;

	private Integer page = 1;
	private Integer limit = 10;

	private Integer[] ids;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "ProviderVo{" +
				"page=" + page +
				", limit=" + limit +
				", ids=" + Arrays.toString(ids) +
				'}';
	}
}
