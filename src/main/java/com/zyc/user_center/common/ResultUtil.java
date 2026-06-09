package com.zyc.user_center.common;

public class ResultUtil {

    /**
     * 成功
     * @param data 返回数据
     * @return 成功信息
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, "ok", data);
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @return 错误信息
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @param description 错误信息
     * @return 错误信息
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(), description);
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @param message 错误信息
     * @return 错误信息
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), message, null, description);
    }

    /**
     * 错误
     * @param code 错误码
     * @param message 错误信息
     * @return 错误信息
     */
    public static <T> BaseResponse<T> error(int code, String message, String description) {
        return new BaseResponse<>(code, message, null, description);
    }


}
