package com.fnsco.cms.dao;


import com.fnsco.cms.model.RoleMenuDO;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface RoleMenuDAO {

    @Insert("INSERT into fns_cms_role_menu(id,role_id,menu_id) VALUES (#{id},#{roleId},#{menuId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insert(RoleMenuDO roleMenu);

    @Delete("DELETE FROM fns_cms_role_menu WHERE role_id = #{id}")
    public int deleteById(@Param("id") Integer roleId);

    @Delete("DELETE FROM fns_cms_role_menu WHERE menu_id = #{id}")
    public int deleteByMenuId(@Param("id") Integer MenuId);

    @UpdateProvider(type = RoleMenuProvider.class, method = "update")
    public int update(@Param("roleMenu") RoleMenuDO roleMenu);

    @Results({@Result(column = "role_id", property = "roleId"), @Result(column = "menu_id", property = "menuId")})
    @SelectProvider(type = RoleMenuProvider.class, method = "pageList")
    public List<RoleMenuDO> pageList(@Param("roleMenu") RoleMenuDO roleMenu, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    @SelectProvider(type = RoleMenuProvider.class, method = "pageListCount")
    public Integer pageListCount(@Param("roleMenu") RoleMenuDO roleMenu);

    /**
     * 通过角色ID查找对应的菜单ID(一对多，列表)
     *
     * @param roleId
     * @return
     */
    @Select("select menu_id from fns_cms_role_menu WHERE role_id = #{roleId}")
    public List<Integer> queryByRoleId(@Param("roleId") Integer roleId);
}