package com.fnsco.cms.constants;

import com.google.common.collect.Maps;

import java.util.Map;

public class AuthConstant {
    public static int SUPER_ADMIN = 1;
    public static String WEB_LOGIN_NULL = "5101"; //用户名或密码为空
    public static String WEB_LOGIN_FAIL = "5102"; //用户名或密码错误
    public static String WEB_LOGIN_USER_NOT_EXIST = "5103"; //用户名不存在
    public static String WEB_LOGIN_PASSWORD_FAIL = "5104"; //密码错误
    public static String WEB_NOT_LOGIN = "5105"; //SESSION失效，请重新登录
    public static String WEB_OLD_PASSWORD_NULL = "5106"; //用户名或密码为空
    public static String OLD_PASSWORD = "5107";
    public static String WEB_NEW_PASSWORD_NULL = "5108";
    public static String WEB_OLD_PASSWORD_FAIL = "5108";
    public static String WEB_BANK_CARD_NULL = "5109";
    public static String WEB_TIME_ERROR = "5110";
    public static String E_PEPT_EXIST = "5011";            //存在子部门
    public static String E_MENU_EXIST = "5021";            //存在子目录或菜单
    public static String E_NOT_DELEET_ONESELF = "5022";            //无法删除自己和禁用自己
    public static String E_NOT_DELEET_ADMIN = "5023";            //无法删除和禁用admin系统管理员
    public static Map<String, String> ERROR_MESSGE_MAP = Maps.newHashMap();

    static {
        ERROR_MESSGE_MAP.put(WEB_LOGIN_NULL, "用户名或密码为空");
        ERROR_MESSGE_MAP.put(WEB_LOGIN_FAIL, "用户名或密码错误");
        ERROR_MESSGE_MAP.put(WEB_LOGIN_USER_NOT_EXIST, "用户名不存在");
        ERROR_MESSGE_MAP.put(WEB_LOGIN_PASSWORD_FAIL, "密码错误");
        ERROR_MESSGE_MAP.put(WEB_NOT_LOGIN, "SESSION失效，请重新登录");
        ERROR_MESSGE_MAP.put(WEB_OLD_PASSWORD_NULL, "原密码不能为空");
        ERROR_MESSGE_MAP.put(WEB_NEW_PASSWORD_NULL, "新密码不能为空");
        ERROR_MESSGE_MAP.put(WEB_OLD_PASSWORD_FAIL, "原密码不正确");
        ERROR_MESSGE_MAP.put(WEB_BANK_CARD_NULL, "不支持该银行卡类型");
        ERROR_MESSGE_MAP.put(WEB_TIME_ERROR, "时间错误");
        ERROR_MESSGE_MAP.put(E_PEPT_EXIST, "存在子部门，无法删除");
        ERROR_MESSGE_MAP.put(E_MENU_EXIST, "存在子目录或菜单，无法删除");
        ERROR_MESSGE_MAP.put(E_NOT_DELEET_ONESELF, "无法删除和禁用自己");
        ERROR_MESSGE_MAP.put(E_NOT_DELEET_ADMIN, "无法删除和禁用admin系统管理员");
    }
}
