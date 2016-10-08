package com.kcj.animfriend.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.kcj.animfriend.bean.Album;
import com.kcj.animfriend.bean.Palette;
import com.kcj.animfriend.bean.Video;
import com.kcj.animfriend.config.Constant;
import com.kcj.animfriend.config.ErrorCode;
import com.kcj.animfriend.config.ErrorMsg;
import com.kcj.animfriend.listener.HttpRequestListener;
import com.kcj.animfriend.utils.StringUtil;
import com.kcj.animfriend.utils.TimeUtil;

public class HttpProxy {

	/**
	 * 获取新的画集
	 */
	public static synchronized String getAlbumHtmlString(String max ,HttpRequestListener<String> httpRequestListener) {
		String content = "";
		List<Album> albumList = new ArrayList<Album>();
		try {
			Document document = Jsoup.connect(Constant.URL_ALBUM + "&max=" + max)
					.data("jquery", "java").userAgent("Mozilla")
					.cookie("auth", "token").timeout(50000).get();
			if (document.toString() == null || document.toString().isEmpty()) {
				if(httpRequestListener != null){
					httpRequestListener.onFailure(ErrorCode.
							HTTP_JSOUP_GET_DATA_FAILUER, ErrorMsg.HTTP_JSOUP_GET_DATA_FAILUER_MSG);
				}
			}else{
				Elements var = document.body().select("script");
				for (Element div : var) {
					String str1 = "app.page[" + "\"" + "ads" + "\"" + "] = ";
					String str2 = "app.page[" + "\"" + "pins" + "\"" + "] = ";
					String str3 = StringUtils.substringBetween(div.toString(),str2, str1);
					if (str3 != null && !str3.isEmpty()) {
						JSONArray array = new JSONArray(str3);
						for (int i = 0; i < array.length(); i++) {
							JSONObject json = (JSONObject) array.get(i);
							JSONObject jsomUrl = (JSONObject) json.get("file");
							ArrayList<String> url = new ArrayList<String>();
							int width = 0, height = 0;
							width = 720 / 2 - 30;
							height = width * jsomUrl.getInt("height")
									/ jsomUrl.getInt("width");
							if (height > 500) {
								height = 500;
							}
							Album album = new Album();
							album.setContent(json.getString("raw_text"));
							album.setPinId(String.valueOf(json.getInt("pin_id")));
							url.add("http://img.hb.aicdn.com/"
									+ jsomUrl.getString("key") + "_fw658");
							album.setUrlList(url);
							album.setResWidth(width);
							album.setResHight(height);
							album.setLove(json.getInt("like_count"));
							album.setFavorites(json.getInt("repin_count"));
							albumList.add(album);
						}
					}
				}
				Gson gson = new Gson();
				content = gson.toJson(albumList);
				System.out.println(content);
				if(httpRequestListener != null){
					httpRequestListener.onSuccess(content);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_EXCEPTION, e.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_JSON_EXCEPTION, e.toString());
			}
		}
		return content;
	}

	/**
	 * 获取画板
	 */
	public static synchronized String getPaletteHtmlString(String max , HttpRequestListener<String> httpRequestListener) {
		String content = "";
		List<Palette> paletteList = new ArrayList<Palette>();
		try {
			Document document = Jsoup
					.connect(Constant.URL_PALETTE + "&max=" + max)
					.data("jquery", "java").userAgent("Mozilla")
					.cookie("auth", "token").timeout(50000).get();
			if (document.toString() == null || document.toString().isEmpty()) {
				if(httpRequestListener != null){
					httpRequestListener.onFailure(ErrorCode.
							HTTP_JSOUP_GET_DATA_FAILUER, ErrorMsg.HTTP_JSOUP_GET_DATA_FAILUER_MSG);
				}
			}else{
				Elements var = document.body().select("script");
				for (Element div : var) {
					String str1 = "app.page[" + "\"" + "boards" + "\"" + "] = ";
					String str2 = "app.page[" + "\"" + "promotions" + "\"" + "] = ";
					String str3 = StringUtils.substringBetween(div.toString(),str1, str2);
					if (str3 != null && !str3.isEmpty()) {
						JSONArray json = new JSONArray(str3);
						for (int i = 0; i < json.length(); i++) {
							Palette palette = new Palette();
							ArrayList<String> url = new ArrayList<String>();
							JSONObject boardjs = (JSONObject) json.get(i);
							JSONArray array = boardjs.getJSONArray("pins");
							for (int j = 0; j < array.length(); j++) {
								JSONObject js = (JSONObject) array.get(j);
								JSONObject jsfile = js.getJSONObject("file");
								url.add("http://img.hb.aicdn.com/"
										+ jsfile.getString("key") + "_fw658");
							}
							palette.setName(boardjs.getString("title"));
							palette.setNum(boardjs.getInt("pin_count"));
							palette.setUrlAlbum(url);
							palette.setBoardId(boardjs.getString("board_id"));
							paletteList.add(palette);
						}
					}
				}
				Gson gson = new Gson();
				content = gson.toJson(paletteList);
				System.out.println(content);
				if(httpRequestListener != null){
					httpRequestListener.onSuccess(content);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_EXCEPTION, e.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_JSON_EXCEPTION, e.toString());
			}
		}
		return content;
	}

	/**
	 * 获取画板中的画集
	 */
	public static synchronized void getPaletteAblumHtmlString(String boardId, String max , HttpRequestListener<String> httpRequestListener) {
		String content = "";
		List<Album> albumList = new ArrayList<Album>();
		try {
			Document document = Jsoup
					.connect(Constant.URL_PALETTE_ALBUM + boardId+ "/?iemf5hfr&limit=20&wfl=1&max=" + max)
					.data("jquery", "java").userAgent("Mozilla")
					.cookie("auth", "token").timeout(50000).get();
			if (document.toString() == null || document.toString().isEmpty()) {
				httpRequestListener.onFailure(ErrorCode.
						HTTP_JSOUP_GET_DATA_FAILUER, ErrorMsg.HTTP_JSOUP_GET_DATA_FAILUER_MSG);
			}else{
				Elements var = document.body().select("script");
				for (Element div : var) {
					String str1 = "app.page[" + "\"" + "board" + "\"" + "] = ";
					String str2 = "app._csr = true";
					String str3 = StringUtils.substringBetween(div.toString(),str1, str2);
					if (str3 != null && !str3.isEmpty()) {
						JSONObject json = new JSONObject(str3);
						JSONArray jsonpins = json.getJSONArray("pins");
						for (int i = 0; i < jsonpins.length(); i++) {
							JSONObject pins = jsonpins.getJSONObject(i);
							JSONObject jsomUrl = (JSONObject) pins.get("file");
							ArrayList<String> url = new ArrayList<String>();
							int width = 0, height = 0;
							width = 720 / 2 - 30;
							height = width * jsomUrl.getInt("height")
									/ jsomUrl.getInt("width");
							if (height > 500) {
								height = 500;
							}
							Album album = new Album();
							album.setContent(pins.getString("raw_text"));
							url.add("http://img.hb.aicdn.com/"
									+ jsomUrl.getString("key") + "_fw658");
							album.setPinId(String.valueOf(pins.getInt("pin_id")));
							album.setUrlList(url);
							album.setResWidth(width);
							album.setResHight(height);
							album.setLove(pins.getInt("like_count"));
							album.setFavorites(pins.getInt("repin_count"));
							albumList.add(album);
						}
					}
				}
				Gson gson = new Gson();
				content = gson.toJson(albumList);
				System.out.println(content);
				httpRequestListener.onSuccess(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
			httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_EXCEPTION, e.toString());
		} catch (JSONException e) {
			e.printStackTrace();
			httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_JSON_EXCEPTION, e.toString());
		}
	}
	

	/**
	 * 主页-横幅
	 */
	public static synchronized String getBanner() {
		String content = "";
		List<Video> bannerList = new ArrayList<Video>();
		try {
			URL url = new URL(Constant.URL_HOME_BRAND);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			InputStream inStream = conn.getInputStream();
			if ("gzip".equals(conn.getContentEncoding())) {
	        	content = StringUtil.readDataForZgip(inStream, "utf-8");
	        }else{
	        	content = StringUtil.readDataForZgip(inStream);
	        }
	        conn.disconnect();
	        if(content != null || !content.isEmpty()){
	        	JSONObject bannerjson = new JSONObject(content);
	        	JSONArray array = bannerjson.getJSONArray("list");
				for (int i=0;i<array.length();i++) {
					Video item = new Video();		
					item.setPic(array.getJSONObject(i).getString("img").toString());
					item.setTitle(array.getJSONObject(i).getString("title").toString());
					item.setUrlInfo(array.getJSONObject(i).getString("link").toString());
					bannerList.add(item);
				}
				Gson gson = new Gson();
				content = gson.toJson(bannerList);
				System.out.println(content);
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return content;
	}
	
	/**
	 * 主页-内容
	 */
	public static synchronized void getHomeHtmlString(HttpRequestListener<String> httpRequestListener){
		String content = "";
		List<List<Video>> videoLl = new ArrayList<List<Video>>();
		try {
			URL url = new URL(Constant.URL_HOME_CONTENT);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10 * 1000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() != 200)
	            throw new RuntimeException("请求url失败");
	        InputStream is = conn.getInputStream();//拿到输入流  
	        if ("gzip".equals(conn.getContentEncoding())) {
	        	content = StringUtil.readDataForZgip(is, "utf-8");
	        }else{
	        	content = StringUtil.readDataForZgip(is);
	        }
	        conn.disconnect();
	        if(content == null || content.isEmpty()){
	        	httpRequestListener.onFailure(ErrorCode.HTTP_GET_DATA_NULL, ErrorMsg.HTTP_GET_DATA_NULL_MSG);
	        }else{
	        	ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
	        	JSONObject bangumijson  = new JSONObject(content);
	        	JSONObject bangumiarray = bangumijson.getJSONObject("bangumi");// 新番
				JSONObject dougaarray   = bangumijson.getJSONObject("douga");//动画
//				JSONObject musicarray   = bangumijson.getJSONObject("music"); //音乐
//				JSONObject kichikuarray = bangumijson.getJSONObject("kichiku"); //鬼畜
//				JSONObject entarray     = bangumijson.getJSONObject("ent"); //娱乐
				jsonList.add(bangumiarray);
				jsonList.add(dougaarray);
//				jsonList.add(musicarray);
//				jsonList.add(kichikuarray);
//				jsonList.add(entarray);
				for (int j=0 ; j < jsonList.size() ; j++) {
					ArrayList<Video> videoList = new ArrayList<Video>();
					for (int i = 0 ; i < 10 ; i++) {
						Video item = new Video();		
						item.        setAid(jsonList.get(j).getJSONObject(i+"").getString("aid").toString());
						item.     setTypeid(jsonList.get(j).getJSONObject(i+"").getString("typeid").toString());
						item.      setTitle(jsonList.get(j).getJSONObject(i+"").getString("title").toString());
						item.   setSbutitle(jsonList.get(j).getJSONObject(i+"").optString("sbutitle").toString());
						item.       setPlay(jsonList.get(j).getJSONObject(i+"").getString("play").toString());
						item.     setReview(jsonList.get(j).getJSONObject(i+"").getString("review").toString());
						item.setVideoReview(jsonList.get(j).getJSONObject(i+"").getString("video_review").toString());
						item.  setFavorites(jsonList.get(j).getJSONObject(i+"").getString("favorites").toString());
						item.        setMid(jsonList.get(j).getJSONObject(i+"").getString("mid").toString());
						item.     setAuthor(jsonList.get(j).getJSONObject(i+"").getString("author").toString());
						item.setDescription(jsonList.get(j).getJSONObject(i+"").getString("description").toString());
						item.     setCreate(jsonList.get(j).getJSONObject(i+"").getString("create").toString());
						item.        setPic(jsonList.get(j).getJSONObject(i+"").getString("pic").toString());
						item.     setCredit(jsonList.get(j).getJSONObject(i+"").getString("credit").toString());
						item.      setCoins(jsonList.get(j).getJSONObject(i+"").getString("coins").toString());
						item.   setDuration(jsonList.get(j).getJSONObject(i+"").getString("duration").toString());	
						videoList.add(item);
					}
					videoLl.add(videoList);
				}
				Gson gson = new Gson();
				content = gson.toJson(videoLl);
				System.out.println(content);
				httpRequestListener.onSuccess(content);
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 主页 - 更多视频
	 */
	public static synchronized void getMoreVedio(String url,String page ,HttpRequestListener<String> httpRequestListener){
		String content = "";
		List<Video> videoList = new ArrayList<Video>();
		try {
			System.out.println(url + page+ "-" + TimeUtil.getTimeOld(System.currentTimeMillis())+".html");
			Document document = Jsoup.connect(url + page+ "-" + TimeUtil.getTimeOld(System.currentTimeMillis())+".html").data("jquery", "java")
					.userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			if (document.toString() == null || document.toString().isEmpty()) {
				httpRequestListener.onFailure(ErrorCode.
						HTTP_JSOUP_GET_DATA_FAILUER, ErrorMsg.HTTP_JSOUP_GET_DATA_FAILUER_MSG);
			}else{
				Elements divs = document.select("div.l-item");
				for (Element div : divs) {
					Video item = new Video();
					Element link = div.select("a[href]").get(0);
					Elements media = div.select("[data-img]");
					Elements info = div.select("div.v-desc");
					Elements v_info = div.select("div.v-info");
					//Elements gk = v_info.select("span");
					Elements up_info = div.select("div.up-info");
					Elements v_date = up_info.select("span");
					Element user = up_info.select("a[href]").get(0);
					
					String gk = "0";
					Elements gks = v_info.select("v-info-i,.gk");
					Elements gknum = gks.select("span");
					if(gknum.size() == 2){
						gk = gknum.get(1).attr("number");
					}
					String dm = "0";
					Elements dms = v_info.select("v-info-i,.dm");
					Elements dmnum = dms.select("span");
					if(dmnum.size() == 2){
						dm = dmnum.get(1).attr("number");
					}
					String sc = "0";
					Elements scs = v_info.select("v-info-i,.sc");
					Elements scnum = scs.select("span");
					if(scnum.size() == 2){
						sc = scnum.get(1).attr("number");
					}
					
					item.setAid(link.attr("href").split("av")[1].replace("/",""));
					item.setTitle(link.attr("title"));
					item.setPic(media.attr("abs:data-img"));
					item.setDescription(info.text());
					item.setPlay(gk); // 播放
					item.setVideoReview(dm); // 弹幕
					item.setFavorites(sc); // 收藏
					item.setAuthor(user.text());
					item.setCreate(v_date.text());
					videoList.add(item);
				}
				Gson gson = new Gson();
				content = gson.toJson(videoList);
				System.out.println(content);
				httpRequestListener.onSuccess(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
			httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_EXCEPTION, e.toString());
		} 
	}
	
	/**
	 * 发现 - 番剧
	 */
	public static synchronized void getBankunList(String page, HttpRequestListener<String> httpRequestListener){
		String content = "";
		final List<Video> videoList = new ArrayList<Video>();
		try {
			URL url = new URL(Constant.URL_FIND_BANKUN + page);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10 * 1000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() != 200)
	            throw new RuntimeException("请求url失败");
	        InputStream is = conn.getInputStream();//拿到输入流  
	        if ("gzip".equals(conn.getContentEncoding())) {
	        	content = StringUtil.readDataForZgip(is, "utf-8");
	        }else{
	        	content = StringUtil.readDataForZgip(is);
	        }
	        conn.disconnect();
	        if(content == null || content.isEmpty()){
	        	httpRequestListener.onFailure(ErrorCode.HTTP_GET_DATA_NULL, ErrorMsg.HTTP_GET_DATA_NULL_MSG);
	        }else{
	        	JSONObject json = new JSONObject(content);
				JSONObject result = json.getJSONObject("result");
				JSONArray list = result.getJSONArray("list");
				for(int i = 0 ; i < list.length() ; i++){
					JSONObject jsonObject =  list.getJSONObject(i);
					Video item = new Video();
					item.setUrlInfo(jsonObject.getString("url"));
					item.setPic(jsonObject.getString("cover"));
					item.setTitle(jsonObject.getString("title"));
					item.setUpdateContent("更新至"+jsonObject.getString("newest_ep_index")+"话");
					videoList.add(item);
				}
	        	Gson gson = new Gson();
				content = gson.toJson(videoList);
				System.out.println(content);
				httpRequestListener.onSuccess(content);
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 搜索-图片
	 */
	public static synchronized String getSearchAlbum(String key , String page ,HttpRequestListener<String> httpRequestListener){
		String content = "";
		List<Album> albumList = new ArrayList<Album>();
		try {
			Document document = Jsoup
					.connect(Constant.URL_SEARCH_ALBUM + URLEncoder.encode(key, "UTF-8") +"&page="+page)
					.data("jquery", "java").userAgent("Mozilla")
					.cookie("auth", "token").timeout(50000).get();
			if(document.toString() == null || document.toString().isEmpty()){
				if(httpRequestListener != null){
					httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_GET_DATA_FAILUER, ErrorMsg.HTTP_JSOUP_GET_DATA_FAILUER_MSG);
				}
			}else{
				Elements var = document.body().select("script");
				//System.out.println(var.toString());
				for (Element div : var) {
					String str1 = "app.page[" + "\"" + "pins" + "\"" + "] = ";
					//System.out.println(str1);
					String str2 = "app.page[" + "\"" + "page" + "\"" + "] = ";
					//System.out.println(str2);
					String str3 = StringUtils.substringBetween(div.toString(),str1, str2);
					//System.out.println(str3);
					if (str3 != null && !str3.isEmpty()) {
						JSONArray array = new JSONArray(str3);
						for (int i = 0; i < array.length(); i++) {
							JSONObject json = (JSONObject) array.get(i);
							JSONObject jsomUrl = (JSONObject) json.get("file");
							ArrayList<String> url = new ArrayList<String>();
							int width = 0, height = 0;
							width = 720 / 2 - 30;
							height = width * jsomUrl.getInt("height")
									/ jsomUrl.getInt("width");
							if (height > 500) {
								height = 500;
							}
							Album album = new Album();
							album.setContent(json.getString("raw_text"));
							album.setPinId(String.valueOf(json.getInt("pin_id")));
							url.add("http://img.hb.aicdn.com/"
									+ jsomUrl.getString("key") + "_fw658");
							album.setUrlList(url);
							album.setResWidth(width);
							album.setResHight(height);
							album.setLove(json.getInt("like_count"));
							album.setFavorites(json.getInt("repin_count"));
							albumList.add(album);
						}
					}
				}
				Gson gson = new Gson();
				content = gson.toJson(albumList);
				System.out.println(content);
				if(httpRequestListener != null){
					httpRequestListener.onSuccess(content);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_EXCEPTION, e.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 搜索-画板
	 */
	public static synchronized String getSearchPalette(String key , String page ,HttpRequestListener<String> httpRequestListener){
		String content = "";
		List<Palette> paletteList = new ArrayList<Palette>();
		try {
			Document document = Jsoup
					.connect(Constant.URL_SEARCH_PALETTE + URLEncoder.encode(key, "UTF-8") +"&page="+page)
					.data("jquery", "java").userAgent("Mozilla")
					.cookie("auth", "token").timeout(50000).get();
			if(document.toString() == null || document.toString().isEmpty()){
				if(httpRequestListener != null){
					httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_GET_DATA_FAILUER, ErrorMsg.HTTP_JSOUP_GET_DATA_FAILUER_MSG);
				}
			}else{
				Elements var = document.body().select("script");
				//System.out.println(var.toString());
				for (Element div : var) {
					String str1 = "app.page[" + "\"" + "boards" + "\"" + "] = ";
					String str2 = "app._csr = true";
					String str3 = StringUtils.substringBetween(div.toString(),str1, str2);
					if (str3 != null && !str3.isEmpty()) {
						JSONArray json = new JSONArray(str3);
						for (int i = 0; i < json.length(); i++) {
							Palette palette = new Palette();
							ArrayList<String> url = new ArrayList<String>();
							JSONObject boardjs = (JSONObject) json.get(i);
							JSONArray array = boardjs.getJSONArray("pins");
							for (int j = 0; j < array.length(); j++) {
								JSONObject js = (JSONObject) array.get(j);
								JSONObject jsfile = js.getJSONObject("file");
								url.add("http://img.hb.aicdn.com/"
										+ jsfile.getString("key") + "_fw658");
							}
							palette.setName(boardjs.getString("title"));
							palette.setNum(boardjs.getInt("pin_count"));
							palette.setUrlAlbum(url);
							palette.setBoardId(boardjs.getString("board_id"));
							paletteList.add(palette);
						}
					}
				}
				Gson gson = new Gson();
				content = gson.toJson(paletteList);
				System.out.println(content);
				if(httpRequestListener != null){
					httpRequestListener.onSuccess(content);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_EXCEPTION, e.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 搜索-视频
	 */
    public static synchronized String getSearchVideo(String key , String page ,HttpRequestListener<String> httpRequestListener){
    	String content = "";
		List<Video> videoList = new ArrayList<Video>();
		try {
			Document document = Jsoup.connect(Constant.URL_SEARCH_VIDEO + "&keyword="+URLEncoder.encode(key, "UTF-8")+"&page="+page).data("jquery", "java")
					.userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			if (document.toString() == null || document.toString().isEmpty()) {
				if(httpRequestListener != null){
					httpRequestListener.onFailure(ErrorCode.
							HTTP_JSOUP_GET_DATA_FAILUER, ErrorMsg.HTTP_JSOUP_GET_DATA_FAILUER_MSG);
				}
			}else{
//				System.out.println(document.toString());
				Element video_list = document.getElementById("video-list");
				Elements li = video_list.select("li");
				// System.out.println(li.size());
				for(int i = 0 ; i < li.size() ; i++){
					Element item = li.get(i); 
					Video video = new Video();
					Elements img = item.select("a[href]");
					Elements intro = item.select("p");
					Elements icon_wrap = item.select("div.icon-wrap");
					Elements span = icon_wrap.select("span");
					//System.out.println(span.size());
					
					video.setAid(img.attr("href").split("av")[1].replace("/",""));
					//System.out.println(img.attr("href").split("av")[1].replace("/",""));
					video.setTitle(img.attr("title"));
					//System.out.println(img.attr("title"));
					video.setPic(img.select("[src]").attr("abs:src"));
					//System.out.println(img.select("[src]").attr("abs:src"));
					video.setDescription(intro.text());
					//System.out.println(intro.text());
					video.setFavorites(span.get(1).text());
					//System.out.println(span.get(1).text());
					video.setPlay(span.get(2).text());
					//System.out.println(span.get(2).text());
					video.setVideoReview(span.get(3).text());
					//System.out.println(span.get(3).text());
					video.setCreate(span.get(4).text());
					//System.out.println(span.get(4).text());
				    video.setAuthor(span.get(5).text());
				    //System.out.println(span.get(5).text());
					videoList.add(video);
				}
				Gson gson = new Gson();
				content = gson.toJson(videoList);
				System.out.println(content);
				if(httpRequestListener != null){
					httpRequestListener.onSuccess(content);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_EXCEPTION, e.toString());
			}
		} 
		return content;
	}
	
	/**
	 * 搜索-番剧
	 */
    public static synchronized void getSearchBangunmi(String key ,String page , HttpRequestListener<String> httpRequestListener){
    	String content = "";
		List<Video> videoList = new ArrayList<Video>();
		try {
			Document document = Jsoup.connect(Constant.URL_SEARCH_SERIES + "&keyword="+URLEncoder.encode(key, "UTF-8")+"&page="+page).data("jquery", "java")
					.userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			if (document.toString() == null || document.toString().isEmpty()) {
				if(httpRequestListener != null){
					httpRequestListener.onFailure(ErrorCode.
							HTTP_JSOUP_GET_DATA_FAILUER, ErrorMsg.HTTP_JSOUP_GET_DATA_FAILUER_MSG);
				}
			}else{
				Elements ajax_render = document.select("div.ajax-render");
				Elements list = ajax_render.select("div.list"); 
				for(int i = 0 ; i < list.size() ; i++){
					Video video = new Video();
					Element item = list.get(i);
					Elements img = item.select("div.img");
					Elements h3 = item.select("h3");
					Elements title = h3.select("a[href]");
					Elements ul = item.select("div.list").select("ul");
					Elements li = ul.select("li");
					
					video.setUrlInfo(img.select("a[href]").attr("href"));
					System.out.println(img.select("a[href]").attr("href"));
					video.setPic(img.select("[src]").attr("abs:src"));
					System.out.println(img.select("[src]").attr("abs:src"));
					video.setTitle(title.text().replace(" ",""));
					System.out.println(title.text().replace(" ",""));
					video.setUpdateContent("更新至"+li.size()+"话");
					System.out.println("更新至"+li.size()+"话");
					
					videoList.add(video);
				}
				Gson gson = new Gson();
				content = gson.toJson(videoList);
				System.out.println(content);
				if(httpRequestListener != null){
					httpRequestListener.onSuccess(content);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(ErrorCode.HTTP_JSOUP_PARSE_EXCEPTION, e.toString());
			}
		} 
	}
}
