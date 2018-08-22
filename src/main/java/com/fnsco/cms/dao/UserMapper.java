package com.fnsco.cms.dao;

import com.fnsco.cms.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);


    User selectByPrimaryKey(Integer userId);


    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //逻辑删除
    int deleteUser(Integer userId);

    //查询所有用户信息
    List<User> selectAll();

    //登录（参数为账号和密码）
    User selectUserByAccountAndPassword(@Param("userAccount") String userAccount, @Param("userPassword") String userPassword);

    //验证用户身份身份存在
    User selectUserByAccount(String userAccount);


    /**
     * 查询某用户所有角色记录
     */
    List<User> queryListById(Integer userId);


    //通过用户id查询用户
    User getById(Integer userId);


    @Results({@Result(column = "perms", property = "perms")})
    @SelectProvider(type = UserProvider.class, method = "queryAllPerms")
    public List<String> queryAllPerms(@Param("userId") Integer userId);


    List<User> pageList(@Param("user") User user, @Param("start") Integer start, @Param("limt") Integer limt);

    int pageListCount(User user);

    int updateById(Integer userId);

    User getByName(String userAccount);
}