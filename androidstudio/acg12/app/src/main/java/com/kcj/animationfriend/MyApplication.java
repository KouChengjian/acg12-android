package com.kcj.animationfriend;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import cn.bmob.im.BmobChat;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.db.BmobDB;
import cn.bmob.v3.datatype.BmobGeoPoint;

import com.android.material.view.ThemeManager;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.util.CollectionUtils;
import com.kcj.animationfriend.util.SharePreferenceUtil;
import com.liteutil.util.Log;
import com.liteutil.util.Toastor;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * @ClassName: MyApplication
 * @Description: 全局
 * @author: KCJ
 * @date: 2014-11-12
 */
public class MyApplication extends Application {

	private static MyApplication myApplication = null;
	private MediaPlayer mMediaPlayer;
	private NotificationManager mNotificationManager;
	// 单例模式，才能及时返回数据
	private SharePreferenceUtil mSpUtil;
	public static final String PREFERENCE_NAME = "_sharedinfo";
	private DisplayMetrics     displayMetrics = null;
	
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public static BmobGeoPoint lastPoint = null;// 上一次定位到的经纬度
	private Map<String, BmobChatUser> contactList = new HashMap<String, BmobChatUser>();

	@Override
	public void onCreate() {
		super.onCreate();
		myApplication = this;
		BmobChat.DEBUG_MODE = true;
		init();
		initImageLoader();
		initBaidu();
	}

	public static MyApplication getInstance() {
		return myApplication;
	}

	public void init() {
		// bmob
		BmobChat.DEBUG_MODE = false;
		BmobChat.getInstance(this).init(Constant.BMOB_APP_ID);
		new Toastor(this);
		new UserProxy(this);
		ThemeManager.init(this, 2, 0, null);
		mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		// 若用户登陆过，则先从好友数据库中取出好友list存入内存中
		getBmobDBContactList();
	}
	
	/**
	 * 初始化imageLoader
	 */
	public void initImageLoader() {
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.memoryCache(new LruMemoryCache(5 * 1024 * 1024))
				.memoryCacheSize(10 * 1024 * 1024)          
				.discCache(new UnlimitedDiskCache(cacheDir))
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.build();
		ImageLoader.getInstance().init(config);
		@SuppressWarnings("unused")
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		    .cacheInMemory(false).imageScaleType(ImageScaleType.EXACTLY)
		    .cacheOnDisk(true).build();
	}
	
	/**
	 * @Title: initBaidumap
	 * @Description: 初始化百度相关sdk initBaidumap
	 * @param
	 * @return void
	 * @throws
	 */
	private void initBaidu() {
		// 初始化地图Sdk
		SDKInitializer.initialize(this);
		// 初始化定位sdk
		initBaiduLocClient();
	}
	
	/**
	 * @Title: initBaiduLocClient
	 * @Description: 初始化百度定位sdk
	 * @param
	 * @return void
	 * @throws
	 */
	private void initBaiduLocClient() {
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
	}
	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			double latitude = location.getLatitude();
			double longtitude = location.getLongitude();
			Log.i("LatLog", latitude+"======="+longtitude);
//			if (lastPoint != null) {
//				if (lastPoint.getLatitude() == location.getLatitude()
//						&& lastPoint.getLongitude() == location.getLongitude()) {
////					BmobLog.i("两次获取坐标相同");// 若两次请求获取到的地理位置坐标是相同的，则不再定位
//					mLocationClient.stop();
//					return;
//				}
//			}
			mLocationClient.stop();
			lastPoint = new BmobGeoPoint(longtitude, latitude);
		}
	}
	
	/**
	 * 初始化通知对话框
	 */
	public NotificationManager getNotificationManager() {
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}
	
	/**
	 * 初始化声音
	 */
	public synchronized MediaPlayer getMediaPlayer() {
		if (mMediaPlayer == null)
			mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
		return mMediaPlayer;
	}

	/**
	 * 获取数据库中的好友列表
	 */
	public void getBmobDBContactList() {
		if (BmobUserManager.getInstance(getApplicationContext()).getCurrentUser() != null) {
			// 获取本地好友user list到内存,方便以后获取好友list
			contactList = CollectionUtils.list2map(BmobDB.create(getApplicationContext()).getContactList());
		}
	}
	
	/**
	 * 获取内存中好友user list
	 * 
	 * @return
	 */
	public Map<String, BmobChatUser> getContactList() {
		return contactList;
	}

	/**
	 * 设置好友user list到内存中
	 * @param contactList
	 */
	public void setContactList(Map<String, BmobChatUser> contactList) {
		if (this.contactList != null) {
			this.contactList.clear();
		}
		this.contactList = contactList;
	}

	@SuppressWarnings("deprecation")
	public DisplayImageOptions getOptions(int drawableId) {
		return new DisplayImageOptions.Builder().showImageOnLoading(drawableId)
				.showImageForEmptyUri(drawableId).showImageOnFail(drawableId)
				.resetViewBeforeLoading(true).cacheInMemory(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}
	
	public synchronized SharePreferenceUtil getSpUtil() {
		if (mSpUtil == null) {
			String currentId = BmobUserManager.getInstance(
					getApplicationContext()).getCurrentUserObjectId();
			String sharedName = currentId + PREFERENCE_NAME;
			mSpUtil = new SharePreferenceUtil(this, sharedName);
		}
		return mSpUtil;
	}
	
	public final String PREF_LONGTITUDE = "longtitude";// 经度
	private String longtitude = "";

	/**
	 * 获取经度
	 * 
	 * @return
	 */
	public String getLongtitude() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		longtitude = preferences.getString(PREF_LONGTITUDE, "");
		return longtitude;
	}

	/**
	 * 设置经度
	 * 
	 * @param pwd
	 */
	public void setLongtitude(String lon) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_LONGTITUDE, lon).commit()) {
			longtitude = lon;
		}
	}

	public final String PREF_LATITUDE = "latitude";// 经度
	private String latitude = "";

	/**
	 * 获取纬度
	 * 
	 * @return
	 */
	public String getLatitude() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		latitude = preferences.getString(PREF_LATITUDE, "");
		return latitude;
	}

	/**
	 * 设置维度
	 * 
	 * @param pwd
	 */
	public void setLatitude(String lat) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_LATITUDE, lat).commit()) {
			latitude = lat;
		}
	}
	
	public final String PREF_DECLARATION = "is_declaration";// 经度
	private boolean isDeclaration = true;

	/**
	 * 
	 */
	public boolean getIsDeclaration() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		isDeclaration = preferences.getBoolean(PREF_DECLARATION, true);
		return isDeclaration;
	}

	/**
	 * 
	 */
	public void setIsDeclaration(boolean isDeclaration) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putBoolean(PREF_DECLARATION, isDeclaration).commit()) {
			this.isDeclaration = isDeclaration;
		}
	}
	
	public int getScreenHeight() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.widthPixels;
    }
    
    public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        this.displayMetrics = DisplayMetrics;
    }
	
	/**
	 * 退出登录,清空缓存数据
	 */
	public void logout() {
		BmobUserManager.getInstance(getApplicationContext()).logout();
		setContactList(null);
		setLatitude(null);
		setLongtitude(null);
	}
}
