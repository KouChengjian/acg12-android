package com.acg12.kk.net.factory;

/**
 * Created by DELL on 2016/12/6.
 */
public class ApiException extends RuntimeException {
    private int errorCode;

    public ApiException(int code, String msg) {
        super(msg);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
