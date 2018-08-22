package com.fnsco.cms.dao;

import com.fnsco.cms.model.Role;
import com.fnsco.cms.model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    int deleteByPrimaryKey(Integer Id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer Id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);


    // List<Role> selectRolesWithUser(Integer userId);

    /**
     * 新增用户角色
     */
    int insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 删除用户角色
     */
    int deleteUserRoleById(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 更新用户角色
     */
    int updateUserRole(UserRole userRole);


    List<Role> getByUserId(Integer userId);

    void deleteByUserId(Integer userId);
}