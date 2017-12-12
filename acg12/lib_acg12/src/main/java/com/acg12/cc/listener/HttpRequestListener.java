package com.acg12.cc.listener;


public interface HttpRequestListener<T> {

	void onSuccess(T result);
	void onFailure(int errorcode, String msg);
	
}

