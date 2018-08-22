package com.fnsco.cms.constants;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * web 基础常量
 *
 * @Author Bob.zhu
 * @Date 2018-05-31 11:08
 **/
public class WebConstants {
    public static final String COOKIE_USER_KEY = "cookie_user_key";
    public static final String SESSION_USERID = "session_userid";
    public static final String SESSION_USER_KEY = "session_user_key";
    public static final String E_COMM_BUSSICSS = "5100";
    public static final String OK = "2000";
    public static final String WEB_SAVE_OK = "2001";            //web保存成功

    public static Map<String, String> ERROR_MESSGE_MAP = Maps.newHashMap();
    public static String E_TOKEN_EMPTY = "4010";
    public static String E_TOKEN_ERROR = "4011";
    public static String E_NOT_LOGIN = "4012";
    public static String E_FORCED_LOGIN_OUT = "4014";
    public static String E_NOT_AUTHORIZED = "4015";
    public static String E_PARAMETER_NOT_NULL = "4016";
    public static String E_SYSTEM_EXCEPTION = "5000";

    static {
        ERROR_MESSGE_MAP.put(E_TOKEN_EMPTY, "非法请求,没有token");
        ERROR_MESSGE_MAP.put(E_TOKEN_ERROR, "非法请求,token不正确");
        ERROR_MESSGE_MAP.put(E_NOT_LOGIN, "session已失效，请重新登录");
        ERROR_MESSGE_MAP.put(E_SYSTEM_EXCEPTION, "系统异常，请联系管理员");
        ERROR_MESSGE_MAP.put(E_FORCED_LOGIN_OUT, "当前用户需要强制退出");
        ERROR_MESSGE_MAP.put(E_NOT_AUTHORIZED, "没有权限，请联系管理员授权");
        ERROR_MESSGE_MAP.put(E_PARAMETER_NOT_NULL, "输入参数有误");
    }
}
