package com.liteutil.exception;

import java.io.IOException;

/**
 * Author: wyouflf Date: 13-7-24 Time: 下午3:00
 */
public class BaseException extends IOException {
	private static final long serialVersionUID = 1L;

	public BaseException() {
	}

	public BaseException(String detailMessage) {
		super(detailMessage);
	}

	public BaseException(String detailMessage, Throwable throwable) {
		super(detailMessage);
		this.initCause(throwable);
	}

	public BaseException(Throwable throwable) {
		super(throwable.getMessage());
		this.initCause(throwable);
	}
}
