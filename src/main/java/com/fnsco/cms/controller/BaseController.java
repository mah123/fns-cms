package com.fnsco.cms.controller;

import com.fnsco.cms.constants.WebConstants;
import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.ResultPageDTO;
import com.fnsco.cms.util.CookieUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base controller
 *
 * @Author Bob.zhu
 * @Date 2018-05-31 11:58
 **/
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    public Map<String, Integer> copyParamsToInteger(String[] params) {
        Map<String, Integer> map = Maps.newHashMap();
        for (String key : params) {
            String val = request.getParameter(key);
            if (!Strings.isNullOrEmpty(val)) {
                val = val.trim();
            }
            Integer vaule = 0;
            if (!Strings.isNullOrEmpty(val)) {
                vaule = Integer.parseInt(val);
            }
            map.put(key, vaule);
        }
        return map;
    }

    public Map<String, String> getParameterMap() {
        Map<String, String[]> paramsMap = request.getParameterMap();
        Map<String, String> subMap = new HashMap<>();
        for (Map.Entry<String, String[]> item : paramsMap.entrySet()) {
            subMap.put(item.getKey(), item.getValue()[0]);
        }
        return subMap;
    }

    public Map<String, String> copyParams(String[] params) {
        Map<String, String> map = new HashMap<String, String>();
        for (String key : params) {
            String val = request.getParameter(key);
            if (!Strings.isNullOrEmpty(val)) {
                val = val.trim();
            }
            map.put(key, val);
        }
        return map;
    }

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    public Integer getUserId() {
        Integer userId = (Integer) this.session.getAttribute(WebConstants.SESSION_USERID);
        if (null == userId) {
            userId = 0;
        }
        return userId;
    }

    public String getIp() {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String ip1 = "";
        try {
            if (!Strings.isNullOrEmpty(ip)) {// 返回发出请求的IP地址
                String[] ips = ip.split(",");
                for (int i = 0; i < ips.length; i++) {

                    ip1 = ips[i];
                    if (!"unknown".equalsIgnoreCase(ip1)) {
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("获取IP地址出错", ex);
        }
        return ip1;
    }

    public String getUserAgent() {
        String agent = request.getHeader("User-Agent");
        return agent + request.getHeader("Accept");
    }

    public Map<String, String> getCookie() {
        Map<String, String> map = Maps.newHashMap();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (!Strings.isNullOrEmpty(name)) {
                    map.put(name, cookie.getValue());
                }
            }
        }
        return map;
    }

    /**
     * 获取cookie中的用户
     *
     * @return
     */
    public Object getCookieUser() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (!Strings.isNullOrEmpty(name) && name.equalsIgnoreCase(WebConstants.COOKIE_USER_KEY)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static Object getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (null == session) {
            return null;
        }
        return session.getAttribute(WebConstants.SESSION_USER_KEY);
    }

    public void setSessionUser(Object userDO, Object userId) {
        HttpSession session = request.getSession();
        session.setAttribute(WebConstants.SESSION_USER_KEY, userDO);
        session.setAttribute(WebConstants.SESSION_USERID, userId);
    }

    /**
     * 删除session中保存的用户信息
     */
    public void removeSessionUser() {
        HttpSession session = request.getSession(false);
        if (null == session) {
            return;
        }
        session.removeAttribute(WebConstants.SESSION_USER_KEY);
    }

    /**
     * 删除cookie中保存的用户信息
     */
    public void removeCookieUser() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (!Strings.isNullOrEmpty(name) && name.equalsIgnoreCase(WebConstants.COOKIE_USER_KEY)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    public void addCookieUser(String value) {
        CookieUtils.addCookie(response, WebConstants.COOKIE_USER_KEY, value, 60 * 60 * 24 * 7);
    }


    public ResultDTO success(String data) {
        Map map = Maps.newHashMap();
        map.put("result", data);
        return this.success(map, "");
    }

    public ResultDTO success(Object data) {
        return this.success(data, "");
    }

    public ResultDTO success(Object data, String total) {
        return this.success(data, total, "");
    }

    public ResultDTO success(Object data, String total, String pageId) {
        if (Strings.isNullOrEmpty(total)) {
            return ResultDTO.success(data);
        }
        List temp = (List) data;
        ResultPageDTO pageDTO = new ResultPageDTO();
        pageDTO.setTotal(Integer.parseInt(total));
        if (!Strings.isNullOrEmpty(pageId)) {
            pageDTO.setCurrentPage(Integer.parseInt(pageId));
        }
        pageDTO.setList(temp);
        return ResultDTO.success(pageDTO);
    }

}
