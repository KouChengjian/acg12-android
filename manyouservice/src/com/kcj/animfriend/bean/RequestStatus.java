package com.kcj.animfriend.bean;

public class RequestStatus {
	private String msg;
	private int statusCode;

	public RequestStatus(int statusCode, String msg) {
		this.msg = msg;
		this.statusCode = statusCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
