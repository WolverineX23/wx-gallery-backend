package com.wolf.wxgallerybackend.exception;

import com.wolf.wxgallerybackend.common.BaseResponse;
import com.wolf.wxgallerybackend.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice   // 设置全局切面，捕获异常并处理
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常处理
     *
     * @param e     BusinessException
     * @return      BaseResponse
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 运行时异常处理 - 防止未被 BusinessException 覆盖的系统错误
     *
     * @param e     RuntimeException
     * @return      BaseResponse
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }
}

