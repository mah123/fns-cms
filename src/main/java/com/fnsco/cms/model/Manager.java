package com.fnsco.cms.model;

import java.io.Serializable;
import java.util.Date;

public class Manager implements Serializable {
    private Integer managerId;

    private String managerName;

    private String managerAccount;

    private String managerPassword;

    private Date managerCreateTime;

    private String managerCreateUser;

    private Date managerUpdateTime;

    private String managerUpdateUser;

    private Date managerDelTime;

    private Integer managerState;

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerAccount() {
        return managerAccount;
    }

    public void setManagerAccount(String managerAccount) {
        this.managerAccount = managerAccount;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public Date getManagerCreateTime() {
        return managerCreateTime;
    }

    public void setManagerCreateTime(Date managerCreateTime) {
        this.managerCreateTime = managerCreateTime;
    }

    public String getManagerCreateUser() {
        return managerCreateUser;
    }

    public void setManagerCreateUser(String managerCreateUser) {
        this.managerCreateUser = managerCreateUser;
    }

    public Date getManagerUpdateTime() {
        return managerUpdateTime;
    }

    public void setManagerUpdateTime(Date managerUpdateTime) {
        this.managerUpdateTime = managerUpdateTime;
    }

    public String getManagerUpdateUser() {
        return managerUpdateUser;
    }

    public void setManagerUpdateUser(String managerUpdateUser) {
        this.managerUpdateUser = managerUpdateUser;
    }

    public Date getManagerDelTime() {
        return managerDelTime;
    }

    public void setManagerDelTime(Date managerDelTime) {
        this.managerDelTime = managerDelTime;
    }

    public Integer getManagerState() {
        return managerState;
    }

    public void setManagerState(Integer managerState) {
        this.managerState = managerState;
    }
}