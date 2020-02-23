package com.wzw.microboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
@TableName("sys_user")
public class User  implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String loginname;

    private String address;

    private Integer sex;

    private String remark;

    private String pwd;

    private Integer deptid;

    private Date hiredate;

    private Integer mgr;

    private Integer available;

    private Integer ordernum;

    /**
     * 用户类型[0超级管理员1，管理员，2普通用户]
     */
    private Integer type;

    /**
     * 头像地址
     */
    private String imgpath;

    private String salt;
    /**
     * 领导名称
     */
    @TableField(exist=false)
    private String leadername;
    /**
     * 部门名称
     */
    @TableField(exist=false)
    private String deptname;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLeadername() {
        return leadername;
    }

    public void setLeadername(String leadername) {
        this.leadername = leadername;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginname='" + loginname + '\'' +
                ", address='" + address + '\'' +
                ", sex=" + sex +
                ", remark='" + remark + '\'' +
                ", pwd='" + pwd + '\'' +
                ", deptid=" + deptid +
                ", hiredate=" + hiredate +
                ", mgr=" + mgr +
                ", available=" + available +
                ", ordernum=" + ordernum +
                ", type=" + type +
                ", imgpath='" + imgpath + '\'' +
                ", salt='" + salt + '\'' +
                ", leadername='" + leadername + '\'' +
                ", deptname='" + deptname + '\'' +
                '}';
    }
}
