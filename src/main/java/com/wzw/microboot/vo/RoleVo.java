package com.wzw.microboot.vo;

import com.wzw.microboot.entity.Notice;
import com.wzw.microboot.entity.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class RoleVo extends Role implements Serializable {
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
