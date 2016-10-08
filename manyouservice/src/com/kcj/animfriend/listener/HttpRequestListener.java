package com.kcj.animfriend.listener;

/**
 * @ClassName: HttpRequestListener
 * @Description: 请求
 * @author: KCJ
 * @date: 2016-1-3
 */
public interface HttpRequestListener<T> {
	public void onSuccess(T data);
	public void onFailure(int code,String msg);
}
