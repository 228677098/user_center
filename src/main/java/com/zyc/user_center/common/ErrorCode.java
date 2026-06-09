package com.zyc.user_center.common;

public enum ErrorCode {
    SUCCESS(0, "ok", ""),
    PARAMS_ERROR(10001, "参数错误", ""),
    NULL_ERROR(10002, "空指针异常", ""),
    NOT_LOGIN(10003, "未登录", ""),
    NO_AUTH(10004, "无权限", ""),
    SYSTEM_ERROR(10005, "系统内部异常", "");

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误信息
     */
    private final String message;
    /**
     * 错误描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
