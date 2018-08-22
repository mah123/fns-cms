package com.fnsco.cms.entity;

import com.fnsco.cms.constants.WebConstants;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.Map;

@SuppressWarnings("unchecked")
public class ResultDTO<T extends Object> extends DTO {
    private static final long serialVersionUID = -7387542509934814087L;
    private boolean success;

    private String code;
    private String message;
    private T data;

    public ResultDTO() {
        this.code = "";
        this.message = "";
    }

    public ResultDTO(boolean success, Object data, String code, String message) {
        this.data = (T) data;
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static ResultDTO success() {
        return success(null);
    }

    public static ResultDTO success(String data) {
        Map map = Maps.newHashMap();
        map.put("result", data);
        return success(map);
    }

    public static ResultDTO success(Object data) {
        ResultDTO result = new ResultDTO(true, data, WebConstants.OK,
                WebConstants.ERROR_MESSGE_MAP.get(WebConstants.OK));
        return result;
    }

    public static ResultDTO success(Object data, String messageCode) {
        String message = WebConstants.ERROR_MESSGE_MAP.get(messageCode);
        if (Strings.isNullOrEmpty(message)) {
            message = messageCode;
            messageCode = WebConstants.OK;
        }
        ResultDTO result = new ResultDTO(true, data, messageCode, message);
        return result;
    }


    public static ResultDTO fail(Object data, String messageCode) {
        String message = WebConstants.ERROR_MESSGE_MAP.get(messageCode);
        if (Strings.isNullOrEmpty(message)) {
            message = messageCode;
        }
        ResultDTO result = new ResultDTO(false, data, messageCode, message);
        return result;
    }

    public static ResultDTO fail(String code) {
        String msg = WebConstants.ERROR_MESSGE_MAP.get(code);
        if (Strings.isNullOrEmpty(msg)) {
            msg = code;
            code = WebConstants.E_COMM_BUSSICSS;
        }
        ResultDTO result = new ResultDTO(false, null, code, msg);
        return result;
    }

    public static ResultDTO fail(String code, String msg) {
        ResultDTO result = new ResultDTO(false, null, code, msg);
        return result;
    }

    public static ResultDTO fail() {
        return fail(WebConstants.E_COMM_BUSSICSS);
    }

    /**
     * success
     *
     * @return the success
     * @since CodingExample Ver 1.0
     */

    public boolean isSuccess() {
        return success;
    }

    /**
     * data
     *
     * @return the data
     * @since CodingExample Ver 1.0
     */

    public T getData() {
        return data;
    }

    /**
     * code
     *
     * @return the code
     * @since CodingExample Ver 1.0
     */

    public String getCode() {
        return code;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * message
     *
     * @return the message
     * @since CodingExample Ver 1.0
     */

    public String getMessage() {
        return message;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}