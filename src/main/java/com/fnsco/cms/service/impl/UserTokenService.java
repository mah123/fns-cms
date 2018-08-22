package com.fnsco.cms.service.impl;

import com.fnsco.cms.dao.UserTokenMapper;
import com.fnsco.cms.model.UserToken;
import com.fnsco.cms.oauth.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserTokenService extends BaseService {

    @Autowired
    private UserTokenMapper sysUserTokenDao;
    //12小时后过期
    private final static int EXPIRE = 3600 * 12 * 7;

    public UserToken queryByUserId(Integer userId) {
        return sysUserTokenDao.selectByPrimaryKey(userId);
    }

    public UserToken queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    public void save(UserToken token) {
        sysUserTokenDao.insert(token);
    }

    public void update(UserToken token) {
        sysUserTokenDao.updateByPrimaryKey(token);
    }

    public String createToken(Integer userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        UserToken tokenEntity = queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new UserToken();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            update(tokenEntity);
        }
        return token;
    }
}
