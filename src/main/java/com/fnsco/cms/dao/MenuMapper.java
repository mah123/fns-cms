package com.fnsco.cms.dao;

import com.fnsco.cms.model.MenuDO;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface MenuMapper {

    @Delete("DELETE FROM fns_cms_sys_menu WHERE parent_id = #{id}")
    public int deleteByParentId(@Param("id") int id);


    @Results({@Result(column = "id", property = "id"), @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name"), @Result(column = "url", property = "url"),
            @Result(column = "perms", property = "perms"), @Result(column = "type", property = "type"),
            @Result(column = "icon", property = "icon"), @Result(column = "order_num", property = "orderNum")})
    @Select("SELECT * FROM fns_cms_sys_menu WHERE id = #{id}")
    public MenuDO getById(@Param("id") int id);


    @Insert("INSERT into fns_cms_sys_menu(id,parent_id,name,url,perms,type,icon,order_num) VALUES (#{id},#{parentId},#{name},#{url},#{perms},#{type},#{icon},#{orderNum})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insert(MenuDO menu);


    @Delete("DELETE FROM fns_cms_sys_menu WHERE id = #{id}")
    public int deleteById(@Param("id") int id);


    @UpdateProvider(type = MenuProvider.class, method = "update")
    public int update(@Param("menu") MenuDO menu);


    @Results({@Result(column = "id", property = "id"), @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name"), @Result(column = "url", property = "url"),
            @Result(column = "perms", property = "perms"), @Result(column = "type", property = "type"),
            @Result(column = "icon", property = "icon"), @Result(column = "order_num", property = "orderNum")})
    @SelectProvider(type = MenuProvider.class, method = "pageList")
    public List<MenuDO> pageList(@Param("menu") MenuDO menu, @Param("pageNum") Integer pageNum,
                                 @Param("pageSize") Integer pageSize);


    @SelectProvider(type = MenuProvider.class, method = "pageListCount")
    public Integer pageListCount(@Param("menu") MenuDO menu);


    @Results({@Result(column = "id", property = "id"), @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name"), @Result(column = "url", property = "url"),
            @Result(column = "perms", property = "perms"), @Result(column = "type", property = "type"),
            @Result(column = "icon", property = "icon"), @Result(column = "order_num", property = "orderNum")})
    @Select("select * from fns_cms_sys_menu where type != 2 order by order_num asc")
    public List<MenuDO> queryNotButtonList();


    @Results({@Result(column = "id", property = "id"), @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name"), @Result(column = "url", property = "url"),
            @Result(column = "perms", property = "perms"), @Result(column = "type", property = "type"),
            @Result(column = "icon", property = "icon"), @Result(column = "order_num", property = "orderNum")})
    @SelectProvider(type = MenuProvider.class, method = "queryList")
    public List<MenuDO> queryList(@Param("menu") MenuDO menu);


    /**
     * 获取用户登录的菜单界面的所有菜单ID列表
     *
     * @param id
     * @return
     */
    @Select("select distinct rm.menu_id from fns_cms_user_role ur LEFT JOIN fns_cms_role_menu rm on ur.role_id = rm.role_id where ur.user_id=#{id}")
    public List<Integer> queryAllMenuId(@Param("id") int id);


    /**
     * 通过菜单ID列表查询菜单Obj
     *
     * @param id
     * @return
     */
    @Results({@Result(column = "id", property = "id"), @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name"), @Result(column = "url", property = "url"),
            @Result(column = "perms", property = "perms"), @Result(column = "type", property = "type"),
            @Result(column = "icon", property = "icon"), @Result(column = "order_num", property = "orderNum")})
    @Select("<script>SELECT * FROM fns_cms_sys_menu WHERE id IN "
            + "<foreach item='item' index='index' collection='MenuIdList' open='(' separator=',' close=')'> #{item}"
            + "</foreach> ORDER BY type, order_num</script>")
    public List<MenuDO> queryAllMenuById(@Param("MenuIdList") List<Integer> MenuId);
}