package com.kcj.animationfriend.config;


public interface HttpRequestListener<T> {

	public void onSuccess(T result);
	public void onFailure(String msg);
	
}

