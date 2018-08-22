package com.fnsco.cms.service;

import com.fnsco.cms.model.UserRole;

public interface UserRoleService {

    Integer add(UserRole userRole);

    Integer remove(Integer id);

    Integer change(UserRole userRole);


    Integer addUserRole(Integer userId, Integer roleId);

    Integer removeUserRoleById(Integer userId, Integer roleId);

    int changeUserRole(UserRole userRole);
}
