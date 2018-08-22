package com.fnsco.cms.util;

import com.fnsco.cms.constants.WebConstants;
import com.google.common.base.Strings;
import org.slf4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtils {

    private final static String COOKIE_SEPARATOR = "#SAP#";
    private final static String ENCRYPT_KEY = "asdfwerxcv";
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(CookieUtils.class);

    public static void addCookie(HttpServletResponse response, String key, String value) {
        addCookie(response, key, value, 60 * 60 * 24 * 7);
    }

    public static void addCookie(HttpServletResponse response, String key, String value, int maxAgeInSeconds) {
        String encrypt_key = ENCRYPT_KEY;
        String saveTime = System.currentTimeMillis() + "";
        //签名
        String encrypt_value = encrypt(encrypt_key, saveTime, maxAgeInSeconds + "", value);
        //组合保存
        String cookieValue = encrypt_value + COOKIE_SEPARATOR + saveTime + COOKIE_SEPARATOR + maxAgeInSeconds + COOKIE_SEPARATOR + value;
        setCookie(response, key, cookieValue, maxAgeInSeconds, null, null, null);
    }

    private static void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds, String path, String domain, Boolean isHttpOnly) {
        try {
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(maxAgeInSeconds);

            if (path == null) {
                path = "/";
            }
            cookie.setPath(path);

            if (domain != null) {
                cookie.setDomain(domain);
            }
            if (isHttpOnly != null) {
                cookie.setHttpOnly(isHttpOnly.booleanValue());
            }
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error("保存cookie出错", e);
        }
    }

    private static String encrypt(String encrypt_key, String saveTime, String maxAgeInSeconds, String value) {
        return HashUtils.md5(encrypt_key + saveTime + maxAgeInSeconds + value);
    }

    public static void removeCookie(HttpServletResponse response) {
        String key = WebConstants.SESSION_USER_KEY;
        setCookie(response, key, null, 0, null, null, null);
    }

    public static String getCookStr(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        String cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        return cookieValue;
                    }
                }
            } catch (Exception ex) {
                logger.error("获取cookie值出错", ex);
            }
        }
        return null;

    }

    public static String getCookieValue(String key, HttpServletRequest request) {
        String encrypt_key = ENCRYPT_KEY;
        String cookieValue = getCookStr(request, key);
        logger.error("获取到cookeie的值为" + cookieValue);
        if (!Strings.isNullOrEmpty(cookieValue)) {
            String cookieStrings[] = cookieValue.split(COOKIE_SEPARATOR);
            if (null != cookieStrings && 4 == cookieStrings.length) {
                String encrypt_value = cookieStrings[0];
                String saveTime = cookieStrings[1];
                String maxAgeInSeconds = cookieStrings[2];
                String value = cookieStrings[3];

                String encrypt = encrypt(encrypt_key, saveTime, maxAgeInSeconds, value);

                // 保证 cookie 不被人为修改
                if (encrypt_value != null && encrypt_value.equals(encrypt)) {
                    long stime = Long.parseLong(saveTime);
                    long maxtime = Long.parseLong(maxAgeInSeconds) * 1000;
                    // 查看是否过时
                    if ((stime + maxtime) - System.currentTimeMillis() > 0) {
                        return value;
                    }
                }
            }
        }
        return null;
    }
}
