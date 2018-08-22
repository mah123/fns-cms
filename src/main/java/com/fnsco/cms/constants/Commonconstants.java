package com.fnsco.cms.constants;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Commonconstants {

    public static Map<String, Long> VALIDATE_CODE_MAP = new HashMap<String, Long>();

    public static Map<String, Long> TOKEN_CODE_MAP = new Hashtable<>();

    public static final String VERIFICATION_CODE = "VERIFICATION_CODE";

    public static final String USER_SESSION_KEY = "ADMIN_USER_BEAN";

    public static final String USER_ROLE_KEY = "USER_ROLE_KEY";

    public static final String CODE = "code";

    public static final String MESSAGE = "message";

    public static final String GET_TYPE_APPLICATION = "app";

    public static final String GET_TYPE_DICTIONARY = "dic";

    public static final String GET_TYPE_ROLE = "role";

    public static String FILE_TEMP_PATH = "";


    public static final String SMS_SEND_URL = "SMS_SEND_URL";

    public static final String SMS_SENT_ACCOUNT = "SMS_SENT_ACCOUNT";
    public static final String SMS_SENT_PASSWORD = "SMS_SENT_PASSWORD";
    public static final String SMS_SENT_SP_CODE = "SMS_SENT_SP_CODE";

    public static final String LOG_OPTION_ADD = "0";

    public static final String LOG_OPTION_UPDATE = "2";

    public static final String LOG_OPTION_DELETE = "1";

    public static final String TOKEN_TIME_OUT = "TOKEN_TIME_OUT";

    public static String WEB_REAL_PATH = "";

    public static String REQUEST_REAL_PATH = "";

    public static String CUSTOM_SERVICE_TYPE = "CUSTOM_SERVICE_TYPE";

    public static String MESSAGE_TYPE = "MESSAGE_TYPE";

    public static final String BLIND_EMAIL_BUTTON_NAME = "BLIND_EMAIL_BUTTON_NAME";

//    public static final String BLIND_EMAIL_BACK_URL = "BLIND_EMAIL_BACK_URL";

    public static final String EMAIL_BACK_URL = "/api/login/toBlindEmailAndResetPassword";

    public static final String BLIND_EMAIL_CONTENT = "BLIND_EMAIL_CONTENT";

    public static final String RESET_PASSWORD = "RESET_PASSWORD";

//    public static final String RESET_PASSWORD_BACK_URL = "RESET_PASSWORD_BACK_URL";

    public static final String RESET_PASSWORD_BUTTON_NAME = "RESET_PASSWORD_BUTTON_NAME";

    public static final int ALIPAY = 1;

    public static final ExecutorService cachedThreadPool = Executors.newFixedThreadPool(9);

    public static final int WXPAY = 0;

    public static final String SIGNATURE_TIME_START = "SIGNATURE_TIME_START";


    public static final String JPUSH_APP_KEY = "JPUSH_APP_KEY";


    public static final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(9);


}
