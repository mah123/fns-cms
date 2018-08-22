package com.fnsco.cms.service.impl;

import com.fnsco.cms.dao.UserRoleMapper;
import com.fnsco.cms.model.UserRole;
import com.fnsco.cms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public Integer add(UserRole userRole) {
        return userRoleMapper.insert(userRole);
    }

    @Override
    public Integer remove(Integer id) {
        return userRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer change(UserRole userRole) {
        return userRoleMapper.updateByPrimaryKey(userRole);
    }


    @Override
    public Integer addUserRole(Integer userId, Integer roleId) {
        return null;
    }

    @Override
    public Integer removeUserRoleById(Integer userId, Integer roleId) {
        return null;
    }

    @Override
    public int changeUserRole(UserRole userRole) {
        return 0;
    }
}
