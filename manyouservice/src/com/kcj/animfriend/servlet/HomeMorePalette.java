package com.kcj.animfriend.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.kcj.animfriend.bean.RequestStatus;
import com.kcj.animfriend.config.Constant;
import com.kcj.animfriend.http.HttpProxy;
import com.kcj.animfriend.listener.HttpRequestListener;

public class HomeMorePalette extends HttpServlet {

	public void doGet(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		String max = request.getParameter("max");
		
		HttpProxy.getPaletteHtmlString(max , new HttpRequestListener<String>() {
			@Override
			public void onSuccess(String data) {
				weiteData(data,Constant.SUCCESS,response);
			}
			
			@Override
			public void onFailure(int code, String msg) {
				weiteData(msg,Constant.ERROR,response);
			}
		});
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	public void weiteData(String content , RequestStatus requestStatus ,HttpServletResponse response){
		try {
			JSONObject json = new JSONObject();
			OutputStream outputStream = response.getOutputStream();
			response.setHeader("content-type", "text/html;charset=UTF-8");
			json.put("result", requestStatus.getStatusCode()+"");
			json.put("desc",   requestStatus.getMsg());
			json.put("data",   content);
			byte[] dataByteArr = json.toString().getBytes("UTF-8");
			outputStream.write(dataByteArr);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
