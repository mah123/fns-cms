package com.fnsco.cms.service.impl;


import com.fnsco.cms.dao.UserMapper;
import com.fnsco.cms.dao.UserRoleMapper;
import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.ResultPageDTO;
import com.fnsco.cms.model.Role;
import com.fnsco.cms.model.User;
import com.fnsco.cms.model.UserRole;
import com.fnsco.cms.service.UserService;
import com.fnsco.cms.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Integer addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public Integer removeUser(Integer userId) {
        return userMapper.deleteUser(userId);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User selectOneUser(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAll();
    }

    @Override
    public User getUserByAccountAndPassword(String userAccount, String userPassword) {
        return userMapper.selectUserByAccountAndPassword(userAccount, userPassword);
    }

    @Override
    public User getUserByAccount(String userAccount) {
        return userMapper.selectUserByAccount(userAccount);
    }


    //查询用户角色信息
    @Override
    public List<User> getRolesByUserId(Integer userId) {
        return userMapper.queryListById(userId);
    }


    @Override
    public ResultDTO<User> doLogin(String username, String password) {
        String pwd = Md5Util.getInstance().md5(password);
        User auser = userMapper.selectUserByAccountAndPassword(username, pwd);
        if (auser == null) {
            return ResultDTO.fail();
        }
        // 写到缓存
        return ResultDTO.success(auser);
    }


    @Override
    public User getUserById(Integer id) {
        User userDO = userMapper.getById(id);
        if (userDO != null) {
            return userDO;
        }
        return null;
    }

    //分页
    @Override
    public ResultPageDTO<User> queryList(User user, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 20;
        }
        int start = (pageNum - 1) * pageSize;
        int limit = pageSize;
        List<User> data = userMapper.pageList(user, start, limit);
        /*for (User time : data) {
            Date li = time.getUserBirthday();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(li);
            time.setUserBirthday(li);
        }*/
        // 返回根据条件查询的所有记录条数
        int totalNum = userMapper.pageListCount(user);
        // 返回给页面总条数和每页查询的数据
        ResultPageDTO<User> result = new ResultPageDTO<User>(totalNum, data);
        return result;
    }


    //批量删除修改用户状态为0
    @Override
    public ResultDTO<String> deleteById(Integer[] id) {
        for (int i = 0; i < id.length; i++) {
            int res = userMapper.updateById(id[i]);
        }
        return ResultDTO.success();
    }

    //添加用户信息
    @Override
    public void doAddUser(User user) {
        String psw = Md5Util.getInstance().md5(user.getUserPassword());
        user.setUserPassword(psw);
        //user.setModifyTime(new Date());
        userMapper.insert(user);
        List<Role> list = user.getRoleList();
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        if (list != null) {
            for (Role role : list) {
                userRole.setRoleId(role.getRoleId());
                userRoleMapper.insert(userRole);
            }
        }
    }

    //修改的用户信息查询
    @Override
    public User queryUserById(Integer userId) {
        //System.out.println(userId+"@@@@");
        User user = userMapper.getById(userId);
        List<Role> list = userRoleMapper.getByUserId(userId);//zzzz
        user.setRoleList(list);
        return user;
    }


    //通过用户名查询是否存在该用户
    @Override
    public boolean queryUserByName(String name) {
        User userDO = userMapper.getByName(name);
        if (userDO != null) {
            return false;
        }
        return true;
    }

    /**
     * 用户信息修改
     *
     * @param user
     * @param
     * @return
     */
    @Override
    public ResultDTO<String> toEditDept(User user) {

        List<Integer> idList = user.getRoleIdList();
        UserRole userRole = new UserRole();

        Integer userId = user.getUserId();
        userRoleMapper.deleteByUserId(userId);
        userRole.setUserId(userId);

        if (idList != null) {
            for (Integer id : idList) {
                userRole.setRoleId(id);
                userRoleMapper.insert(userRole);
            }
        }

        //user.setModifyTime(new Date());
        int res = userMapper.updateByPrimaryKeySelective(user);
        if (res < 1) {
            return ResultDTO.fail();
        }
        return ResultDTO.success();
    }

}
