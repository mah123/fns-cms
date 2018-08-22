package com.fnsco.cms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Role implements Serializable {
    private Integer roleId;

    private String roleName;

    private String roleDesc;

    private Integer roleState;

    private Date roleCreateTime;

    private String roleCreateUser;

    private Date roleUpdateTime;

    private String roleUpdateUser;

    private Date roleDelTime;

    /**
     * 角色和菜单ID对应列表
     */
    private List<Integer> menuIdList;

    public List<Integer> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Integer> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }

    public Date getRoleCreateTime() {
        return roleCreateTime;
    }

    public void setRoleCreateTime(Date roleCreateTime) {
        this.roleCreateTime = roleCreateTime;
    }

    public String getRoleCreateUser() {
        return roleCreateUser;
    }

    public void setRoleCreateUser(String roleCreateUser) {
        this.roleCreateUser = roleCreateUser;
    }

    public Date getRoleUpdateTime() {
        return roleUpdateTime;
    }

    public void setRoleUpdateTime(Date roleUpdateTime) {
        this.roleUpdateTime = roleUpdateTime;
    }

    public String getRoleUpdateUser() {
        return roleUpdateUser;
    }

    public void setRoleUpdateUser(String roleUpdateUser) {
        this.roleUpdateUser = roleUpdateUser;
    }

    public Date getRoleDelTime() {
        return roleDelTime;
    }

    public void setRoleDelTime(Date roleDelTime) {
        this.roleDelTime = roleDelTime;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", roleState=" + roleState +
                ", roleCreateTime=" + roleCreateTime +
                ", roleCreateUser='" + roleCreateUser + '\'' +
                ", roleUpdateTime=" + roleUpdateTime +
                ", roleUpdateUser='" + roleUpdateUser + '\'' +
                ", roleDelTime=" + roleDelTime +
                ", menuIdList=" + menuIdList +
                '}';
    }
}