package com.fnsco.cms.oauth;

import com.alibaba.fastjson.JSON;
import com.fnsco.cms.constants.WebConstants;
import com.fnsco.cms.entity.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class OAuth2Helper {
    private static Logger logger = LoggerFactory.getLogger(OAuth2Helper.class);

    public static void reLogin(ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String json = JSON.toJSONString(ResultDTO.fail(WebConstants.E_NOT_LOGIN));//"token不存在"
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            httpResponse.getWriter().print(json);
        } catch (IOException e) {
            logger.error("OAuth2Helper.reLogin()返回重登录页面出错", e);
        }
    }
}
