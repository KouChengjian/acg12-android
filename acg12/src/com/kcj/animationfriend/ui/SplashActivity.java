package com.kcj.animationfriend.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.im.BmobChat;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.util.AppUtil;

/**
 * @ClassName: SplashActivity
 * @Description: guidance activity
 * @author: KCJ
 * @date: 2014-11-6
 */
public class SplashActivity extends BaseActivity {

	@InjectView(R.id.tv_login_version)
	protected TextView tv_login_version;
	private static final int GO_HOME = 100;
	//private static final int GO_LOGIN = 200;
	
	private BaiduReceiver mReceiver;// 注册广播接收器，用于监听网络以及验证key

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			setTranslucentStatus(true);
//		}
//		// 沉浸式
//		setSystemBarTintManager();
		mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		initViews();
	}
	
	@Override
	public void initViews() {
		super.initViews();
		mReceiver = new BaiduReceiver();
		initLocClient(MyApplication.getInstance().mLocationClient);
		initBroadcast(mReceiver);
	}
	
	public void onResume() {
		super.onResume();
		tv_login_version.setText("版本  "+ new AppUtil().getPackageInfo(mContext).versionName);
	}
	
	public void onPause() {
		super.onPause();
	}
		
	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				updateUserInfos();
				startAnimActivity(MainActivity.class);
				finish();
				break;
			}
		}
	};
}