package com.wzw.microboot.entity;

import java.io.Serializable;
import java.util.Date;

public class User  implements Serializable {

    private String id;
    private String userName;
    private String passWord;
    private Integer userType;
    private String userPhone;
    private String userEmail;
    private Integer state;
    private Date serCreateTime;
    private String userDetails;
    private String salt;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getSerCreateTime() {
        return serCreateTime;
    }

    public void setSerCreateTime(Date serCreateTime) {
        this.serCreateTime = serCreateTime;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", userType=" + userType +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", state=" + state +
                ", serCreateTime=" + serCreateTime +
                ", userDetails='" + userDetails + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
