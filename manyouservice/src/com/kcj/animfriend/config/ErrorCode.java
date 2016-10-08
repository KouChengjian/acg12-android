package com.kcj.animfriend.config;


/**
 * @ClassName: Code
 * @Description: 错误编码
 * @author: KCJ
 * @date: 2016-1-3
 */
public class ErrorCode {

	/**
	 * 数据请求
	 */
	// 失败
	public static final int DATA_REQUEST_FAILURE = 0;
	// 成功
	public static final int DATA_REQUEST_SUCCESS = 1;
	
	
	
	
	
	
	
	/**
	 * Http请求错误编号
	 */
	// jsoup解析失败
	public static final int HTTP_JSOUP_PARSE_EXCEPTION = 1001;
	// json解析失败
	public static final int HTTP_JSOUP_PARSE_JSON_EXCEPTION = 1002;
	// jsoup获取数据失败为空
	public static final int HTTP_JSOUP_GET_DATA_FAILUER = 1003;
	//  http获取数据失败为空
	public static final int HTTP_GET_DATA_NULL = 1004;
	
	
}
