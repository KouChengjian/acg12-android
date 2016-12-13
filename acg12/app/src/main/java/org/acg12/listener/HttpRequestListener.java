package org.acg12.listener;


public interface HttpRequestListener<T> {

	void onSuccess(T result);
	void onFailure(int errorcode, String msg);
	
}

