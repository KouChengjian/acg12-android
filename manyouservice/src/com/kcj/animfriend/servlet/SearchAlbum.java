package com.kcj.animfriend.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kcj.animfriend.config.Constant;
import com.kcj.animfriend.http.HttpProxy;
import com.kcj.animfriend.listener.HttpRequestListener;
import com.kcj.animfriend.utils.StringUtil;

public class SearchAlbum extends HttpServlet {

	public void doGet(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		String key=new String(request.getParameter("key").getBytes("utf-8"), "utf-8");
		String page = request.getParameter("page");
		HttpProxy.getSearchAlbum(key, page, new HttpRequestListener<String>() {
			
			@Override
			public void onSuccess(String data) {
				StringUtil.weiteData(data,Constant.SUCCESS,response);
			}
			
			@Override
			public void onFailure(int code, String msg) {
				StringUtil.weiteData(msg,Constant.ERROR,response);
			}
		});
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
