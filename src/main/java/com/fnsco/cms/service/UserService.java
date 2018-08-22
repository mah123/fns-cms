package com.fnsco.cms.service;

import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.ResultPageDTO;
import com.fnsco.cms.model.User;

import java.util.List;

public interface UserService {

    Integer addUser(User user);

    Integer removeUser(Integer userId);

    Integer updateUser(User user);

    User selectOneUser(Integer userId);

    List<User> selectAllUser();


    User getUserByAccountAndPassword(String userAccount, String userPassword);

    User getUserByAccount(String userAccount);

    ResultDTO<User> doLogin(String username, String password);

    /**
     * 查询某用户所有角色记录
     */
    List<User> getRolesByUserId(Integer userId);


    User getUserById(Integer userId);

    //分页
    ResultPageDTO<User> queryList(User user, Integer pageNum, Integer pageSize);


    ResultDTO<String> deleteById(Integer[] id);

    void doAddUser(User user);

    User queryUserById(Integer id);

    boolean queryUserByName(String name);

    ResultDTO<String> toEditDept(User user);

}
