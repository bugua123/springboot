package com.wzw.microboot.vo;

import com.wzw.microboot.entity.Dept;

import java.io.Serializable;

public class DeptVo extends Dept implements Serializable {
    private static final long serialVersionUID=1L;

    private Integer page=1;
    private Integer limit=10;

    private Integer[] ids;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }



}
