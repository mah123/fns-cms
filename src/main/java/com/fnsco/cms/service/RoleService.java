package com.fnsco.cms.service;

import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.ResultPageDTO;
import com.fnsco.cms.model.Role;

import java.util.List;

public interface RoleService {

    /*Integer addRole(Role role);

    Integer removeRole(Integer roleId);

    Integer changeRole(Role role);

    Role getOneRole(Integer roleId);

    List<Role> getAllRole();*/


    List<Role> queryRole();

    List<Role> queryRoleByUserID(Integer userID);


    ResultPageDTO<Role> pageList(Role role, Integer page, Integer rows);

    ResultDTO doAdd(Role role);

    ResultDTO doUpdate(Role role);

    ResultDTO doDelete(Role role);
}
