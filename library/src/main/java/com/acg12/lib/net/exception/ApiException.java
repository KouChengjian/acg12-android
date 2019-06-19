package com.acg12.lib.net.exception;

/**
 * Create by AItsuki on 2018/7/16.
 * 根据服务器返回的错误码封装成异常抛出。
 */
public class ApiException extends RuntimeException {

    private int code;
    private String message;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
