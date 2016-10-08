package com.kcj.animfriend;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

import com.kcj.animfriend.config.Constant;
import com.kcj.animfriend.http.HttpProxy;
import com.kcj.animfriend.listener.HttpRequestListener;
import com.kcj.animfriend.utils.StringUtil;


public class main {

	public static void main(String[] args) {
		
		
		
		String url = StringUtil.getMoreVideoUrl("default-33");
		HttpProxy.getMoreVedio(url , 1+"" , new HttpRequestListener<String>() {
			
			@Override
			public void onSuccess(String data) {
				//StringUtil.weiteData(data,Constant.SUCCESS,response);
				System.out.println(data);
			}
			
			@Override
			public void onFailure(int code, String msg) {
				//StringUtil.weiteData(msg,Constant.ERROR,response);
			}
		});
//		System.out.println(bannerContent);
//		try {
//			System.out.println(URLEncoder.encode("点兔", "UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//			HttpProxy.getSearchVideo("点兔", 1+"" , new HttpRequestListener<String>() {
//				
//				@Override
//				public void onSuccess(String data) {
//					System.out.println(data);
//					
//				}
//				
//				@Override
//				public void onFailure(int code, String msg) {
//					System.out.println(msg);
//				}
//			});
		      
	}
}
