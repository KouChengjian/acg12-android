package com.kcj.animationfriend.ui.base;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import butterknife.ButterKnife;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.util.ActivityTack;
import com.kcj.animationfriend.util.CollectionUtils;
import com.kcj.animationfriend.util.SystemBarTintManager;
import com.liteutil.util.Toastor;

/**
 * @ClassName: BaseActivity
 * @Description: 重构 
 * @author: KCJ
 * @date: 2014-11-6
 */
public class BaseActivity extends AppCompatActivity {
	
	protected Context mContext;
	protected String TAG; // 打印的名称
	protected ActivityTack tack = ActivityTack.getInstanse();
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected BmobUserManager userManager;
	protected BmobChatManager manager;
	protected MyApplication mApplication;
	public LayoutInflater mInflater;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mContext = this;
		TAG = this.getClass().getSimpleName();
		tack.addActivity(this);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		userManager = BmobUserManager.getInstance(this);
		manager = BmobChatManager.getInstance(this);
		mApplication = MyApplication.getInstance();
		mInflater = LayoutInflater.from(mContext);
	}
	
	public void initViews(){}
	
	public void initEvent(){}
	
	public void initDatas(){}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onDestroy() {
		tack.removeActivity(this);
		super.onDestroy();
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.inject(this);
	}
	
	@Override
	public void finish() {
		super.finish();
		tack.removeActivity(this);
	}
	
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}

	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}
	
	protected void startAnimActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}
	
	public void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toastor.ShowToast(text);
				}
			});
		}
	}

	public void ShowToast(final int resId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toastor.ShowToast(resId);
			}
		});
	}
	
	@TargetApi(19) 
	protected void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	protected void setSystemBarTintManager(int color){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(color);
	}
	
	protected void setSystemBarTintManager(){
		setSystemBarTintManager(R.color.theme_primary);
	}
	
	/** 隐藏软键盘
	  * hideSoftInputView
	  * @Title: hideSoftInputView
	  * @Description: 
	  * @param  
	  * @return void
	  * @throws
	  */
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	/**
	 * @Description: 开启定位，更新当前用户的经纬度坐标
	 */
	public void initLocClient(LocationClient mLocationClient) {
		mLocationClient = MyApplication.getInstance().mLocationClient;
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式:高精度模式
		option.setCoorType("bd09ll"); // 设置坐标类型:百度经纬度
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms:低于1000为手动定位一次，大于或等于1000则为定时定位
		option.setIsNeedAddress(false);// 不需要包含地址信息
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}
	
	public void initBroadcast(BaiduReceiver mReceiver){
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		registerReceiver(mReceiver, iFilter);
	}
	
	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class BaiduReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				ShowToast("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				ShowToast("当前网络连接不稳定，请检查您的网络设置!");
			}
		}
	}
	
	/** 
	 * @Title: updateUserInfos
	 * @Description: 用于登陆或者自动登陆情况下的用户资料及好友资料的检测更新
	 * @param  
	 * @return void
	 * @throws
	 */
	public void updateUserInfos(){
		updateUserLocation();
		//查询该用户的好友列表(这个好友列表是去除黑名单用户的哦),目前支持的查询好友个数为100，如需修改请在调用这个方法前设置BmobConfig.LIMIT_CONTACTS即可。
		//这里默认采取的是登陆成功之后即将好于列表存储到数据库中，并更新到当前内存中,
		userManager.queryCurrentContactList(new FindListener<BmobChatUser>() {

			@Override
			public void onError(int arg0, String arg1) {
				if(arg0==BmobConfig.CODE_COMMON_NONE){
//					ShowLog(arg1);
				}else{
//					ShowLog("查询好友列表失败："+arg1);
				}
			}

			@Override
			public void onSuccess(List<BmobChatUser> arg0) {
				MyApplication.getInstance().setContactList(CollectionUtils.list2map(arg0));
			}
			
		});
	}

	/** 
	 * @Title: uploadLocation
	 * @Description: 更新用户的经纬度信息
	 * @param  
	 * @return void
	 * @throws
	 */
	public void updateUserLocation(){
		if(MyApplication.lastPoint!=null){
			String saveLatitude  = mApplication.getLatitude();
			String saveLongtitude = mApplication.getLongtitude();
			String newLat = String.valueOf(MyApplication.lastPoint.getLatitude());
			String newLong = String.valueOf(MyApplication.lastPoint.getLongitude());
			if(!saveLatitude.equals(newLat)|| !saveLongtitude.equals(newLong)){//只有位置有变化就更新当前位置，达到实时更新的目的
				User u = (User) userManager.getCurrentUser(User.class);
				if(u != null){
					final User user = new User();
					user.setLocation(MyApplication.lastPoint);
					user.setObjectId(u.getObjectId());
					user.update(this,new UpdateListener() {
						@Override
						public void onSuccess() {
							MyApplication.getInstance().setLatitude(String.valueOf(user.getLocation().getLatitude()));
							MyApplication.getInstance().setLongtitude(String.valueOf(user.getLocation().getLongitude()));
//							ShowLog("经纬度更新成功");
						}
						@Override
						public void onFailure(int code, String msg) {
//							ShowLog("经纬度更新 失败:"+msg);
						}
					});
				}
			}else{
//				ShowLog("用户位置未发生过变化");
			}
		}
	}
}
