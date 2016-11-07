package com.kcj.animationfriend.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.db.BmobDB;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Area;
import com.kcj.animationfriend.bean.Collect;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.listener.FindCollectListener;
import com.kcj.animationfriend.listener.FindFavourListener;
import com.kcj.animationfriend.listener.FindUserPaletteListener;
import com.kcj.animationfriend.listener.HttpRequestListener1;
import com.kcj.animationfriend.listener.ILoginListener;
import com.kcj.animationfriend.listener.IResetPasswordListener;
import com.kcj.animationfriend.listener.ISignUpListener;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.RstAndLoginActivity;
import com.kcj.animationfriend.util.CollectionUtils;
import com.liteutil.http.HttpMethod;
import com.liteutil.http.LiteHttp;
import com.liteutil.http.listener.Callback;
import com.liteutil.http.listener.Callback.CommonCallback;
import com.liteutil.http.request.RequestParams;
import com.liteutil.util.Log;

/**
 * @ClassName: HttpProxy
 * @Description: Http请求
 * @author: KCJ
 * @date: 
 */
public class HttpProxy {
	
	/**
	 * 主页-内容
	 */
	public static List<Area> getHomeContent(final HttpRequestListener1<Area> listener) {
		final List<Area> areaList = new ArrayList<Area>();
		try {
			LiteHttp.http().get(new RequestParams(Constant.URL_HOME_CONTENT),new CommonCallback<String>() {
				@Override
				public void onSuccess(String html) {
					try {
						JSONObject json = new JSONObject(html);
						String result = json.getString("result");
						String desc = json.getString("desc");
						String data = json.getString("data");
						if(result.equals("0")){
							if(listener != null){
								listener.onFailure(desc);
							}
						}else{
							JSONObject array = new JSONObject(data);
							JSONArray bannerJson  = array.getJSONArray("banner");
							JSONArray albumJson   = array.getJSONArray("album");
							JSONArray paletteJson = array.getJSONArray("palette");
							JSONArray bangumiJson = array.getJSONArray("bangumi");
							JSONArray dougaJson   = array.getJSONArray("douga");
							Area area;
							Gson gson = new Gson();
							List<Video> bannerList = gson.fromJson(bannerJson.toString(), new TypeToken<List<Video>>(){}.getType());
							area  = new Area("横幅",bannerList);
							areaList.add(area);
							List<Album> albumList = gson.fromJson(albumJson.toString(), new TypeToken<List<Album>>(){}.getType());
							area  = new Area("图片",albumList);
							areaList.add(area);
							List<Palette> paletteList = gson.fromJson(paletteJson.toString(), new TypeToken<List<Palette>>(){}.getType());
							area  = new Area("图集",paletteList);
							areaList.add(area);
							List<Video> bangumiList = gson.fromJson(bangumiJson.toString(), new TypeToken<List<Video>>(){}.getType());
							area  = new Area("番剧",bangumiList);
							areaList.add(area);
							List<Video> dougaList = gson.fromJson(dougaJson.toString(), new TypeToken<List<Video>>(){}.getType());
							area  = new Area("动画",dougaList);
							areaList.add(area);
							if(listener != null){
								listener.onSuccess(areaList);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						if(listener != null){
							listener.onFailure(e.toString());
						}
					}
				}

				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					if(listener != null){
						listener.onFailure(ex.toString());
					}
				}

				@Override
				public void onCancelled(CancelledException cex) {}

				@Override
				public void onFinished() {}
			});
		} catch (Throwable e) {
			e.printStackTrace();
			if(listener != null){
				listener.onFailure(e.toString());
			}
		}
		return areaList;
	}
	
	/**
	 * 主页-更多内容-图片
	 */
	public static Callback.Cancelable getHomeMoreAlbum(String max,final HttpRequestListener1<Album> listener){
		RequestParams params = new RequestParams(Constant.URL_HOME_MORE_ALBUM + max);
		Callback.Cancelable cancelable = LiteHttp.http().get(params,new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if(listener != null){
					listener.onFailure(ex.toString());
				}
			}

			@Override
			public void onSuccess(String html) {
				try {
					List<Album> albumList = new ArrayList<Album>();
					JSONObject json = new JSONObject(html);
					String result = json.getString("result");
					String desc = json.getString("desc");
					String data = json.getString("data");
					if(result.equals("0")){
						if(listener != null){
							listener.onFailure(desc);
						}
					}else{
						Gson gson = new Gson();
						List<Album> list = gson.fromJson(data, new TypeToken<List<Album>>(){}.getType());
						albumList.addAll(list);
						if(listener != null){
							listener.onSuccess(list);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if(listener != null){
						listener.onFailure(e.toString());
					}
				}
			}
			
			@Override
			public void onFinished() {}
		});	
		return cancelable;
	}
	
	/**
	 * 主页-更多内容-画板
	 */
	public static Callback.Cancelable getHomeMorePalette(String max,final HttpRequestListener1<Palette> listener){
		RequestParams params = new RequestParams(Constant.URL_HOME_MORE_PALETTE+max);
		Callback.Cancelable cancelable = LiteHttp.http().get(params,new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if(listener != null){
					listener.onFailure(ex.toString());
				}
			}

			@Override
			public void onSuccess(String html) {
				try {
					List<Palette> paletteList = new ArrayList<Palette>();
					JSONObject json = new JSONObject(html);
					String result = json.getString("result");
					String desc = json.getString("desc");
					String data = json.getString("data");
					if(result.equals("0")){
						if(listener != null){
							listener.onFailure(desc);
						}
					}else{
						Gson gson = new Gson();
						List<Palette> list = gson.fromJson(data, new TypeToken<List<Palette>>(){}.getType());
						paletteList.addAll(list);
						if(listener != null){
							listener.onSuccess(paletteList);
						}
					}
				}catch (JSONException e) {
					e.printStackTrace();
					if(listener != null){
						listener.onFailure(e.toString());
					}
				}
			}
			
			@Override
			public void onFinished() {}
			
		});	
		return cancelable;
	}
	
	/**
	 * 主页-更多内容-画板-图片
	 */
	public static Callback.Cancelable getHomeMorePaletteAlbum(String max,String boardId ,final HttpRequestListener1<Album> listener){
		final List<Album> albumList = new ArrayList<Album>();
		RequestParams params = new RequestParams(Constant.URL_HOME_MORE_PALETTEALBUM +max+"&boardId="+boardId);
		Callback.Cancelable cancelable = LiteHttp.http().get(params,new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if(listener != null){
					listener.onFailure(ex.toString());
				}
			}

			@Override
			public void onSuccess(String html) {
				try {
					JSONObject json = new JSONObject(html);
					String result = json.getString("result");
					String desc = json.getString("desc");
					String data = json.getString("data");
					if(result.equals("0")){
						if(listener != null){
							listener.onFailure(desc);
						}
					}else{
						Gson gson = new Gson();
						List<Album> list = gson.fromJson(data, new TypeToken<List<Album>>(){}.getType());
						albumList.addAll(list);
						if(listener != null){
							listener.onSuccess(list);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if(listener != null){
						listener.onFailure(e.toString());
					}
				}
			}
			
			@Override
			public void onFinished() {}
		});	
		return cancelable;
	}
	
	/**
	 * 主页-更多内容-视频
	 */
	public static synchronized List<Video> getHomeMoreVedio(String url,int page,final HttpRequestListener1<Video> listener){
		final List<Video> videoList = new ArrayList<Video>();
		try {
			LiteHttp.http().requestSync(HttpMethod.GET,new RequestParams(url + page),new CommonCallback<String>() {

				@Override
				public void onCancelled(CancelledException arg0) {}

				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					if(listener != null){
						listener.onFailure(ex.toString());
					}
				}

				@Override
				public void onSuccess(String html) {
					try {
						JSONObject json = new JSONObject(html);
						String result = json.getString("result");
						String desc = json.getString("desc");
						String data = json.getString("data");
						if(result.equals("0")){
							if(listener != null){
								listener.onFailure(desc);
							}
						}else{
							Gson gson = new Gson();
							List<Video> list = gson.fromJson(data, new TypeToken<List<Video>>(){}.getType());
							videoList.addAll(list);
							if(listener != null){
								listener.onSuccess(videoList);
							}
						}
					}catch (JSONException e) {
						e.printStackTrace();
						if(listener != null){
							listener.onFailure(e.toString());
						}
					}
				}
				
				@Override
				public void onFinished() {}
				
			});
		} catch (Throwable e) {
			e.printStackTrace();
			if(listener != null){
				listener.onFailure(e.toString());
			}
		}
		return videoList;
	}
	
	/**
	 * 主页-视频详细信息 
	 */
	public static Callback.Cancelable getHomeVideoInfo(String av , final HttpRequestListener<Video> listener){
		RequestParams params = new RequestParams(Constant.URL_HOME_VIDEO_INFO + av);
		Callback.Cancelable cancelable = LiteHttp.http().get(params,new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if(listener != null){
					listener.onFailure(ex.toString());
				}
			}

			@Override
			public void onSuccess(String html) {
				try {
					JSONObject json = new JSONObject(html);
					String result = json.getString("result");
					String desc = json.getString("desc");
					String data = json.getString("data");
					if(result.equals("0")){
						if(listener != null){
							listener.onFailure(desc);
						}
					}else{
						Gson gson = new Gson();
						Video video = gson.fromJson(data, new TypeToken<Video>(){}.getType());
						if(listener != null){
							listener.onSuccess(video);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if(listener != null){
						listener.onFailure(e.toString());
					}
				}
			}
			
			@Override
			public void onFinished() {}
		});	
		return cancelable;
	}
	
	/**
	 * 发现 - 番剧
	 */
	public static List<Video> getBankunList(int page,final HttpRequestListener1<Video> listener){
		final List<Video> videoList = new ArrayList<Video>();
		try {
			LiteHttp.http().get(new RequestParams(Constant.URL_FIND_BANKUN+String.valueOf(page).toString()), new CommonCallback<String>() {
				@Override
				public void onSuccess(String html) {
					try {
						JSONObject json = new JSONObject(html);
						String result = json.getString("result");
						String desc = json.getString("desc");
						String data = json.getString("data");
						if(result.equals("0")){
							if(listener != null){
								listener.onFailure(desc);
							}
						}else{
							JSONArray jsonArray = new JSONArray(data);
							for(int i = 0 ; i < jsonArray.length() ; i++){
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								Video item = new Video();
								item.setUrlInfo(jsonObject.getString("urlInfo"));
								item.setPic(jsonObject.getString("pic"));
								item.setTitle(jsonObject.getString("title"));
								item.setUpdateContent(jsonObject.getString("updateContent"));
								videoList.add(item);
							}
							if(listener != null){
								listener.onSuccess(videoList);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						if(listener != null){
							listener.onFailure(e.toString());
						}
					}
				}
				
				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					if(listener != null){
						listener.onFailure(ex.toString());
					}
				}

				@Override
				public void onCancelled(CancelledException cex) {}

				@Override
				public void onFinished() {}
				
			});
		} catch (Throwable e) {
			e.printStackTrace();
			if(listener != null){
				listener.onFailure(e.toString());
			}
		}
		return videoList;
	}
	
	/**
	 * 发现 - 番剧详情
	 */
	public static Callback.Cancelable getBankunInfo(String av,final HttpRequestListener<Video> listener){
		RequestParams params = new RequestParams(Constant.URL_FIND_BANKUN_INFO + av);
		Callback.Cancelable cancelable = LiteHttp.http().get(params,new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if(listener != null){
					listener.onFailure(ex.toString());
				}
			}

			@Override
			public void onSuccess(String html) {
				try {
					Log.i("tag", html);
					Video video = new Video();
					JSONObject json = new JSONObject(html);
					String result = json.getString("result");
					String desc = json.getString("desc");
					String data = json.getString("data");
					if(result.equals("0")){
						if(listener != null){
							listener.onFailure(desc);
						}
					}else{
						Gson gson = new Gson();
						video = gson.fromJson(data, new TypeToken<Video>(){}.getType());
						if(listener != null){
							listener.onSuccess(video);
						}
					}
				}catch (JSONException e) {
					e.printStackTrace();
					if(listener != null){
						listener.onFailure(e.toString());
					}
				}
			}
			
			@Override
			public void onFinished() {}
			
		});	
		return cancelable;
	}
	
	/**
	 * 发现 - 获取视频av号
	 */
	public static Callback.Cancelable getBankunInfoAV(String av,final HttpRequestListener<String> listener){
		RequestParams params = new RequestParams(Constant.URL_FIND_BANKUN_INFO_AV + av);
		Callback.Cancelable cancelable = LiteHttp.http().get(params,new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				if(listener != null){
					listener.onFailure(ex.toString());
				}
			}

			@Override
			public void onSuccess(String html) {
				try {
					JSONObject json = new JSONObject(html);
					String result = json.getString("result");
					String desc = json.getString("desc");
					String data = json.getString("data");
					if(result.equals("0")){
						if(listener != null){
							listener.onFailure(desc);
						}
					}else{
						if(listener != null){
							listener.onSuccess(data);
						}
					}
				}catch (JSONException e) {
					e.printStackTrace();
					if(listener != null){
						listener.onFailure(e.toString());
					}
				}
			}
			
			@Override
			public void onFinished() {}
			
		});	
		return cancelable;
	}
	
	
	/**
	 * 搜索 - 图片
	 */
	public static List<Album> getSearchAlbum(String key , int page ,final HttpRequestListener1<Album> httpRequestListener){
		final List<Album> albumList = new ArrayList<Album>();
		try {
			LiteHttp.http().get(new RequestParams(Constant.URL_SEARCH_ALBUM + 
					URLEncoder.encode(key, "UTF-8") + "&page=" + String.valueOf(page).toString()),new CommonCallback<String>() {

				@Override
				public void onCancelled(CancelledException arg0) {}

				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					if(httpRequestListener != null){
						httpRequestListener.onFailure(ex.toString());
					}
				}

				@Override
				public void onSuccess(String html) {
					try {
						JSONObject json = new JSONObject(html);
						String result = json.getString("result");
						String desc = json.getString("desc");
						String data = json.getString("data");
						if(result.equals("0")){
							if(httpRequestListener != null){
								httpRequestListener.onFailure(desc);
							}
						}else{
							Gson gson = new Gson();
							List<Album> list = gson.fromJson(data, new TypeToken<List<Album>>(){}.getType());
							albumList.addAll(list);
							if(httpRequestListener != null){
								httpRequestListener.onSuccess(list);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
						if(httpRequestListener != null){
							httpRequestListener.onFailure(e.toString());
						}
					}
				}
				
				@Override
				public void onFinished() {}
			});
		} catch (Throwable e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(e.toString());
			}
		}
		return albumList;
	}
	
	/**
	 * 搜索 - 图集
	 */
	public static List<Palette> getSearchPalette(String key , int page ,final HttpRequestListener1<Palette> listener){
		final List<Palette> paletteList = new ArrayList<Palette>();
		try {
			LiteHttp.http().get(new RequestParams(Constant.URL_SEARCH_PALETTE + 
					URLEncoder.encode(key, "UTF-8") + "&page=" + String.valueOf(page).toString()),new CommonCallback<String>() {

				@Override
				public void onCancelled(CancelledException arg0) {}

				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					if(listener != null){
						listener.onFailure(ex.toString());
					}
				}

				@Override
				public void onSuccess(String html) {
					try {
						JSONObject json = new JSONObject(html);
						String result = json.getString("result");
						String desc = json.getString("desc");
						String data = json.getString("data");
						if(result.equals("0")){
							if(listener != null){
								listener.onFailure(desc);
							}
						}else{
							Gson gson = new Gson();
							List<Palette> list = gson.fromJson(data, new TypeToken<List<Palette>>(){}.getType());
							paletteList.addAll(list);
							if(listener != null){
								listener.onSuccess(paletteList);
							}
						}
					}catch (JSONException e) {
						e.printStackTrace();
						if(listener != null){
							listener.onFailure(e.toString());
						}
					}
				}
				
				@Override
				public void onFinished() {}
				
			});
		} catch (Throwable e) {
			e.printStackTrace();
			if(listener != null){
				listener.onFailure(e.toString());
			}
		}
		return paletteList;
	}
	
	/**
	 * 搜索 - 视频
	 */
    public static List<Video> getSearchVideo(String key , int page ,final HttpRequestListener1<Video> httpRequestListener){
    	final List<Video> videoList = new ArrayList<Video>();
    	try {
			LiteHttp.http().get(new RequestParams(Constant.URL_SEARCH_VIDEO + 
					URLEncoder.encode(key, "UTF-8") + "&page=" + String.valueOf(page).toString()), new CommonCallback<String>() {
				@Override
				public void onSuccess(String html) {
					try {
						JSONObject json = new JSONObject(html);
						String result = json.getString("result");
						String desc = json.getString("desc");
						String data = json.getString("data");
						if(result.equals("0")){
							if(httpRequestListener != null){
								httpRequestListener.onFailure(desc);
							}
						}else{
							Gson gson = new Gson();
							List<Video> list = gson.fromJson(data, new TypeToken<List<Video>>(){}.getType());
							videoList.addAll(list);
							if(httpRequestListener != null){
								httpRequestListener.onSuccess(list);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						if(httpRequestListener != null){
							httpRequestListener.onFailure(e.toString());
						}
					}
				}
				
				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					if(httpRequestListener != null){
						httpRequestListener.onFailure(ex.toString());
					}
				}

				@Override
				public void onCancelled(CancelledException cex) {}

				@Override
				public void onFinished() {}
				
			});
		} catch (Throwable e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(e.toString());
			}
		}
    	return videoList;
    }
	
	/**
	 * 搜索 - 番剧
	 */
    public static List<Video> getSearchBangunmi(String key , int page ,final HttpRequestListener1<Video> httpRequestListener){
    	final List<Video> videoList = new ArrayList<Video>();
    	try {
    		Log.e("TAG", Constant.URL_SEARCH_SERIES + 
					URLEncoder.encode(key, "UTF-8") + "&page=" + String.valueOf(page).toString());
			LiteHttp.http().get(new RequestParams(Constant.URL_SEARCH_SERIES + 
					URLEncoder.encode(key, "UTF-8") + "&page=" + String.valueOf(page).toString()), new CommonCallback<String>() {
				@Override
				public void onSuccess(String html) {
					try {
						Log.e("TAG", html);
						JSONObject json = new JSONObject(html);
						String result = json.getString("result");
						String desc = json.getString("desc");
						String data = json.getString("data");
						if(result.equals("0")){
							if(httpRequestListener != null){
								httpRequestListener.onFailure(desc);
							}
						}else{
							Gson gson = new Gson();
							List<Video> list = gson.fromJson(data, new TypeToken<List<Video>>(){}.getType());
							videoList.addAll(list);
							if(httpRequestListener != null){
								httpRequestListener.onSuccess(list);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						if(httpRequestListener != null){
							httpRequestListener.onFailure(e.toString());
						}
					}
				}
				
				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					if(httpRequestListener != null){
						httpRequestListener.onFailure(ex.toString());
					}
				}

				@Override
				public void onCancelled(CancelledException cex) {}

				@Override
				public void onFinished() {}
				
			});
		} catch (Throwable e) {
			e.printStackTrace();
			if(httpRequestListener != null){
				httpRequestListener.onFailure(e.toString());
			}
		}
    	return videoList;
    }
	
	
	/**
	 * 返回数据 --视频播放
	 */
	@SuppressWarnings("deprecation")
	public static String getHtmlString(String urlString) {  
	    try {
	        URL url = new URL(urlString);  
	        URLConnection ucon = url.openConnection();  
	        HttpURLConnection httpURLConnection = (HttpURLConnection)ucon;
	        InputStream inputStream = null;
	        InputStreamReader inputStreamReader = null;
	        BufferedReader reader = null;
	        StringBuffer resultBuffer = new StringBuffer();
	        String tempLine = null;
	        if (httpURLConnection.getResponseCode() >= 300) {
	            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
	        }
	        try {
	            inputStream = httpURLConnection.getInputStream();
	            inputStreamReader = new InputStreamReader(inputStream);
	            reader = new BufferedReader(inputStreamReader);
	            
	            while ((tempLine = reader.readLine()) != null) {
	                resultBuffer.append(tempLine);
	            }
	            
	        } finally {
	            
	            if (reader != null) {
	                reader.close();
	            }
	            
	            if (inputStreamReader != null) {
	                inputStreamReader.close();
	            }
	            
	            if (inputStream != null) {
	                inputStream.close();
	            }
	            
	        }
	        return resultBuffer.toString();
	        
//	        InputStream instr = ucon.getInputStream();  
//	        BufferedInputStream bis = new BufferedInputStream(instr); 
//	        ByteArrayBuffer baf = new ByteArrayBuffer(500);  
//	        int current = 0;  
//	        while ((current = bis.read()) != -1) {  
//	            baf.append((byte) current);  
//	        }  
//	        return EncodingUtils.getString(baf.toByteArray(), "utf-8");  
	    } catch (Exception e) {
	        return "";  
	    }  
	} 
	
	/**
	 * 获取相关视频
	 */
	public static void getVideoRecommendString(Context mContext,String urlRecommend,final HttpRequestListener1<Video> listener) {
		Log.e("url", urlRecommend+"=====");
		final List<Video> list = new ArrayList<Video>();
		try {
			LiteHttp.http().requestSync(HttpMethod.GET,new RequestParams(urlRecommend), new CommonCallback<String>() {
				@Override
				public void onSuccess(String result) {
					try {
						JSONArray arrayList = new JSONArray(result);
						Log.e("arrayList", arrayList.length()+"=====");
						for(int i = 0 ; i < arrayList.length() ;i++){
							JSONObject jsonObject = arrayList.getJSONObject(i);
							Video item = new Video();
							item.setAid(jsonObject.getString("id"));
							item.setTitle(jsonObject.getString("title"));
							item.setPic(jsonObject.getString("pic"));
							item.setDescription(jsonObject.getString("description"));
							item.setPlay(jsonObject.getString("click"));
							item.setFavorites(jsonObject.getString("stow"));
							item.setVideoReview(jsonObject.getString("dm_count"));
							item.setAuthor(jsonObject.getString("author_name"));
							item.setCreate(jsonObject.getString("pubdate"));
							list.add(item);
						}
						listener.onSuccess(list);
					} catch (JSONException e) {
						e.printStackTrace();
						listener.onFailure(e.toString());
					}
				}

				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					listener.onFailure(ex.toString());
				}

				@Override
				public void onCancelled(CancelledException cex) {}

				@Override
				public void onFinished() {}
				
			});
		} catch (Throwable e) {
			e.printStackTrace();
			listener.onFailure(e.toString());
		}
	}
	
	/**
	 *  注册
	 */
	public static void signUp(Context mContext,String userName, String password, String nick) {
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		user.setSex(Constant.SEX_FEMALE);
		user.setNick(nick);
		user.setSignature("这个家伙很懒，什么也不说。。。");
		user.setDeviceType("android");
		user.setInstallId(BmobInstallation.getInstallationId(mContext));
		user.signUp(mContext, new SaveListener() {

			@Override
			public void onSuccess() {
				if (signUpLister != null) {
					signUpLister.onSignUpSuccess();
				} else {
					// LogUtils.i(TAG,"signup listener is null,you must set one!");
				}
			}

			@Override
			public void onFailure(int arg0, String msg) {
				if (signUpLister != null) {
					signUpLister.onSignUpFailure(msg);
				} else {
					// LogUtils.i(TAG,"signup listener is null,you must set one!");
				}
			}
		});
	}

	private static ISignUpListener signUpLister;

	public static void setOnSignUpListener(ISignUpListener signUpListe) {
		signUpLister = signUpListe;
	}
	
	/**
	 *  登入
	 */
	private static ILoginListener loginListener;

	public static void setOnLoginListener(ILoginListener loginListene) {
		loginListener = loginListene;
	}
	
	public static void login(Context mContext,String userName, String password) {
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		BmobUserManager.getInstance(mContext).login(user,new SaveListener() {

			@Override
			public void onFailure(int arg0, String arg1) {
				if (loginListener != null) {
					loginListener.onLoginFailure(arg1);
				}
			}

			@Override
			public void onSuccess() {
				if (loginListener != null) {
					loginListener.onLoginSuccess();
				}
			}
			
		});
	}

	/**
	 *  重置密码
	 */
	private static IResetPasswordListener resetPasswordListener;

	public static void setOnResetPasswordListener(
			IResetPasswordListener resetPasswordListene) {
		resetPasswordListener = resetPasswordListene;
	}
	
	public static void resetPassword(String email) {
//		BmobUser.resetPassword(mContext, email, new ResetPasswordListener() {
//
//			@Override
//			public void onSuccess() {
//				if (resetPasswordListener != null) {
//					resetPasswordListener.onResetSuccess();
//				} else {
//					// LogUtils.i(TAG,"reset listener is null,you must set one!");
//				}
//			}
//
//			@Override
//			public void onFailure(int arg0, String msg) {
//				if (resetPasswordListener != null) {
//					resetPasswordListener.onResetFailure(msg);
//				} else {
//					// LogUtils.i(TAG,"reset listener is null,you must set one!");
//				}
//			}
//		});
	}
	
	/**
	 * 获取指定用户的画板
	 */
	private static List<Palette> palettelist = new ArrayList<Palette>();
	
	private static FindUserPaletteListener findUserPaletteListener;

	public static void setOnFindUserPaletteListener(FindUserPaletteListener findUserPaletteListener1) {
		findUserPaletteListener = findUserPaletteListener1;
	}
	
	public static void fetchUserPalette(final Context mContext,int page ,User user){
		if(page == 0){
			palettelist.clear();
		}
		BmobQuery<Palette> query = new BmobQuery<Palette>();
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE * (page));
		query.order("-createdAt");
		query.include("user");
		query.addWhereEqualTo("user", user);
		query.findObjects(mContext, new FindListener<Palette>() {

			@Override
			public void onSuccess(List<Palette> list) {
				for(Palette palette:list){ // 初始化全部没关注
					palettelist.add(palette);
				}
				if(list.size() > 0){
					setPaletteUrl(mContext,0);
				}else{
					if (findUserPaletteListener != null) {
						findUserPaletteListener.onFindUserPaletteSuccess(palettelist);
					}
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				if (findUserPaletteListener != null) {
					findUserPaletteListener.onFindUserPaletteFailure(arg1);
				}
			}

		});
	}
	
	/**
	 * 获取指定用户的画板中的图片前5张
	 */
	static int page ;
	
	public static void setPaletteUrl(final Context mContext,int pagenum){
		page = pagenum;
		final Palette palette = palettelist.get(page);
		BmobQuery<Collect> query = new BmobQuery<Collect>(); // 查询当前画板下的第一页图片
		query.addWhereRelatedTo("collectBR", new BmobPointer(palette));
		query.include("user,album,palette");
		query.order("createdAt");
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(0));
		query.findObjects(mContext, new FindListener<Collect>() {

			@Override
			public void onSuccess(List<Collect> data) {
				ArrayList<String> url = new ArrayList<String>();
				int i = 0;
				for(Collect collect:data){
					if(i < 5){
						i++;
						if(collect.getAlbum().getProFileList() != null && !collect.getAlbum().getProFileList().isEmpty()){
							url.add(collect.getAlbum().getProFileList().get(0).getFileUrl(mContext));
						}else{
							if(collect.getAlbum().getUrlList() != null && !collect.getAlbum().getUrlList().isEmpty()){
								url.add(collect.getAlbum().getUrlList().get(0));
							}
						}
						
					}
				}
				palette.setUrlAlbum(url);
                palette.update(mContext, new UpdateListener() {
					
					@Override
					public void onSuccess() {
						++page;
						if(page < palettelist.size()){
							setPaletteUrl( mContext,page);
						}else{
							if (findUserPaletteListener != null) {
								findUserPaletteListener.onFindUserPaletteSuccess(palettelist);
							}
						}
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						--page;
						if (findUserPaletteListener != null) {
							findUserPaletteListener.onFindUserPaletteFailure(arg1);
						}
					}
				});
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				if (findUserPaletteListener != null) {
					findUserPaletteListener.onFindUserPaletteFailure(arg1);
				}
			}
			
		});
	}
	
	/**
	 * 获取指定用户采集的图片
	 */
	private static FindCollectListener findCollectListener;

	public static void setOnFindCollectListener(FindCollectListener findCollectListene) {
		findCollectListener = findCollectListene;
	}
	
	public static void fetchCollectAlbum(Context mContext,int pageNum ,User user){
		BmobQuery<Collect> query = new BmobQuery<Collect>();
		BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
		query.order("-updatedAt");
		query.addWhereLessThan("updatedAt", date);
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE * (pageNum));
		query.include("user,palette,album.user,album.palette");
		query.addWhereEqualTo("type", 1); // 1-采集   0-自己上传
		query.addWhereEqualTo("user", user); 
		query.findObjects(mContext, new FindListener<Collect>() {

			@Override
			public void onSuccess(List<Collect> list) {
				List<Album> listCollect = new ArrayList<Album>();
				if (list.size() != 0 && list.get(list.size() - 1) != null) {
					for(Collect collect:list){
						Album album = collect.getAlbum();
						album.setUser(collect.getAlbum().getUser());
						album.setPalette(collect.getAlbum().getPalette());
						album.setContent(collect.getContent());
						album.setMyLove(false);
						listCollect.add(album);
					}
				}
				if (findCollectListener != null) {
					findCollectListener.onFindCollectSuccess(listCollect);;
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				if (findCollectListener != null) {
					findCollectListener.onFindCollectFailure(arg1);
				}
			}
		});
	}
	
	/**
	 * 点赞
	 */
	private static FindFavourListener findFavourListener;

	public void setOnFindFavourListener(FindFavourListener findFavourListene) {
		findFavourListener = findFavourListene;
	}
	
	public static void fetchUserFavour(Context mContext,int pageNum ,final User user) {
		BmobQuery<Album> query = new BmobQuery<Album>();
		query.addWhereRelatedTo("favourBR", new BmobPointer(user));
		query.include("user");
		query.order("createdAt");
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum));
		query.findObjects(mContext, new FindListener<Album>() {

			@Override
			public void onSuccess(List<Album> list) {
				List<Album> listFavour = new ArrayList<Album>();
				for(Album album : list){
					album.setMyLove(false);
					listFavour.add(album);
				}
				if (findFavourListener != null) {
					findFavourListener.onFindFavourSuccess(listFavour);
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				if (findFavourListener != null) {
					findFavourListener.onFindFavourFailure(arg1);
				}
			}

		});
	}
	
	
	/**
	 *  注销
	 */
	public static void logout() {
		BmobUser.logOut(MyApplication.getInstance());
	}
	
	/**
	 *  获取当前用户
	 */
	public static User getCurrentUser(Context mContext) {
		User user = BmobUser.getCurrentUser(mContext, User.class);
		if (user != null) {
			// LogUtils.i(TAG,"本地用户信息" + user.getObjectId() + "-"
			// + user.getUsername() + "-"
			// + user.getSessionToken() + "-"
			// + user.getCreatedAt() + "-"
			// + user.getUpdatedAt() + "-"
			// + user.getSignature() + "-"
			// + user.getSex());
			return user;
		} else {
			// LogUtils.i(TAG,"本地用户为null,请登录。");
		}
		return null;
	}
	
	/**
	 * 判断当前User是不是用户
	 */
	public static boolean isCurUser(Context mContext,User user){
		User curuser = getCurrentUser( mContext);
		if(user.getObjectId().equals(curuser.getObjectId())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断当前User是不是好友
	 */
	public static boolean userIsFriend(Context mContext,User user){
		MyApplication.getInstance().setContactList(
				CollectionUtils.list2map(BmobDB.create(mContext)
						.getContactList()));
		List<BmobChatUser> userList = CollectionUtils.map2list(MyApplication.getInstance().getContactList());
		if(userList != null || !userList.isEmpty()){
			for(BmobChatUser curuser :userList){
				if(user.getObjectId().equals(curuser.getObjectId())){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否登入
	 */
	public static boolean isUserEnter(Context mContext,ParameCallBack parameCallBack){
		if(getCurrentUser(mContext) == null){
			Intent intent = new Intent(mContext , RstAndLoginActivity.class);
			mContext.startActivity(intent);
			RstAndLoginActivity.setParameCallBack(parameCallBack);
			return false;
		}
		return true;
	}
	
}
