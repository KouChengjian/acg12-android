package com.kcj.animationfriend.listener;

import java.util.List;


public interface HttpRequestListener<T> {

	public void onSuccess(List<T> list);
	public void onFailure(String msg);
	
}