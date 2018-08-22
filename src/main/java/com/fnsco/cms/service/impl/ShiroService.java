package com.fnsco.cms.service.impl;

import com.fnsco.cms.constants.AuthConstant;
import com.fnsco.cms.dao.MenuMapper;
import com.fnsco.cms.dao.UserMapper;
import com.fnsco.cms.dao.UserTokenMapper;
import com.fnsco.cms.model.MenuDO;
import com.fnsco.cms.model.User;
import com.fnsco.cms.model.UserToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: hzh
 * @Date: 2018/8/13 0013 下午 2:24
 * @Description: shiro业务类
 */
@Service
public class ShiroService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTokenMapper userTokenMapper;


    public Set<String> getUserPermissions(Integer userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == AuthConstant.SUPER_ADMIN) {
            List<MenuDO> menuList = menuMapper.queryList(new MenuDO());
            permsList = new ArrayList<>(menuList.size());
            for (MenuDO menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = userMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    public UserToken queryByToken(String token) {
        return userTokenMapper.queryByToken(token);

    }

    public User queryUser(Integer userId) {
        return userMapper.getById(userId);

    }

}
