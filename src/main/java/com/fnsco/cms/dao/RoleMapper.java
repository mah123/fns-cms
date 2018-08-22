package com.fnsco.cms.dao;

import com.fnsco.cms.model.MenuDO;
import com.fnsco.cms.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);


    Role selectByPrimaryKey(Integer roleId);


    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectAll();

    //逻辑删除
    int deleteRole(Integer roleId);

    /**
     * 根据角色的id查询所有的菜单
     *
     * @param roleId
     * @return
     */
    List<MenuDO> selectMenusByRoleId(Integer roleId);


    List<Role> queryRoleByUserID(Integer userID);

    List<Role> pageList(@Param("role") Role role, @Param("start") Integer start, @Param("limt") Integer limt);

    Integer pageListCount(@Param("role") Role role);

    Integer selectMaxId();
}