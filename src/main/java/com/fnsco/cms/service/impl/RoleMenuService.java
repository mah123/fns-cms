package com.fnsco.cms.service.impl;

import com.fnsco.cms.dao.RoleMenuDAO;
import com.fnsco.cms.model.RoleMenuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleMenuService extends BaseService {

    @Autowired
    private RoleMenuDAO roleMenuDAO;


    // 修改或者更新都走这个方法
    @Transactional
    public int saveOrUpdate(Integer roleId, List<Integer> menuIdList) {

        // 如果菜单集合为空，直接返回
        if (menuIdList.size() == 0) {
            return 0;
        }

        // 先删除角色与菜单对应关系，因为角色和菜单是一对多关系，更新不太好更新
        roleMenuDAO.deleteById(roleId);

        // 保存角色与菜单的关系
        for (Integer menuId : menuIdList) {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setRoleId(roleId);
            roleMenuDO.setMenuId(menuId);
            roleMenuDAO.insert(roleMenuDO);
        }

        return 0;
    }

    public void delete(Integer roleId) {

        // 通过角色ID删除角色与菜单的关系
        roleMenuDAO.deleteById(roleId);
    }

    public void deleteByMenuId(Integer integer) {

        // 通过菜单ID删除角色与菜单的关系
        roleMenuDAO.deleteByMenuId(integer);
    }

    //通过角色ID查询菜单ID(列表)
    public List<Integer> queryByRoleId(Integer roleId) {

        List<Integer> roleMenuList = roleMenuDAO.queryByRoleId(roleId);
        return roleMenuList;
    }
}
