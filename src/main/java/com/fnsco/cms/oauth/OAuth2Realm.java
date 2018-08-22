package com.fnsco.cms.oauth;


import com.fnsco.cms.model.User;
import com.fnsco.cms.model.UserToken;
import com.fnsco.cms.service.impl.ShiroService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 * @author sxfei
 * @email songxianfei@gmail.com
 * @date 2017-05-20 14:00
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        System.out.println(user.toString() + "@@@@@@@@@@@@@@");
        Integer userId = user.getUserId();
        System.out.println(userId + "!!!!!!!!!!!!!!");

        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        logger.error("认证登录时调用返回的token为" + accessToken);
        //根据accessToken，查询用户信息
        UserToken tokenEntity = shiroService.queryByToken(accessToken);
        //token失效
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            logger.error("认证时返回的token错误或超时");
            throw new AuthenticationException("token失效");
        }

        //查询用户信息
        User user = shiroService.queryUser(tokenEntity.getUserId());
        //账号锁定
        if (user.getUserState() == 0) {
            logger.error("认证时,账号已被锁定,请联系管理员");
            throw new AuthenticationException("账号已被锁定,请联系管理员");
        }

        //参数一user对象，参数2数据库中的password，参数3当前realm的名称
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
