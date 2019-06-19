package com.acg12.lib.net.result;

/**
 * 返回结果处理基类
 */

public class HttpResult<T> {
    public int code;
    public String msg;
    public T data;
}
