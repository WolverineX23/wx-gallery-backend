package com.wolf.wxgallerybackend.common;

import com.wolf.wxgallerybackend.exception.ErrorCode;
import lombok.Data;

/**
 * 全局响应封装类
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> {

    private int code;

    private String message;

    private T data;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(int code, T data) {
        this(code, "", data);
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
