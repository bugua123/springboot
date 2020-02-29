package com.wzw.microboot.vo;

import com.wzw.microboot.entity.User;

import java.io.Serializable;

public class UserVo extends User implements Serializable {
    private static final long serialVersionUID=1L;

    private Integer page=1;
    private Integer limit=10;

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
}
