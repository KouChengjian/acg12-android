package com.acg12.lib.net.factory;

/**
 * Created by DELL on 2016/12/6.
 */
public class ApiException extends RuntimeException {
    private int errorCode;
    private String msg;

    public ApiException(int code, String msg) {
        super(msg);
        this.errorCode = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
