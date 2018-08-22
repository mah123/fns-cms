package com.fnsco.cms.dao;

import com.fnsco.cms.model.UserToken;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserTokenMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserToken record);

    int insertSelective(UserToken record);


    UserToken selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(UserToken record);

    int updateByPrimaryKey(UserToken record);

    @Results({@Result(column = "user_id", property = "userId"), @Result(column = "expire_time", property = "expireTime"), @Result(column = "update_time", property = "updateTime")})
    @Select("SELECT * FROM fns_cms_user_token WHERE token = #{token}")
    public UserToken queryByToken(@Param("token") String token);

}