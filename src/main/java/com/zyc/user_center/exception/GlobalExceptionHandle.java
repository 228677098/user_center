package com.zyc.user_center.exception;

import com.zyc.user_center.common.BaseResponse;
import com.zyc.user_center.common.ErrorCode;
import com.zyc.user_center.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle<T> {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<T> handleException(BusinessException e){
        log.error("BusinessException:" + e.getMessage(), e);
        return  ResultUtil.error(e.getCode(),e.getMessage(),e.getDescription());
    }


    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<T> handleException(RuntimeException e){
        log.error("RuntimeException", e);
        return  ResultUtil.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }



}
