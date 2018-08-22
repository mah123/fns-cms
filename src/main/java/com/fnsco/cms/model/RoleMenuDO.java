package com.fnsco.cms.model;

public class RoleMenuDO {

    /**
     *
     */
    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId2) {
        this.roleId = roleId2;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId2) {
        this.menuId = menuId2;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", roleId=" + roleId + ", menuId=" + menuId + "]";
    }
}