package com.kcj.animationfriend.ui;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.util.SharePreferenceUtil;
import com.kcj.animationfriend.view.AlertDialogView;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * @ClassName: SettingActivity
 * @Description: 设置
 * @author: KCJ
 * @date: 
 */
public class SettingActivity extends BaseActivity implements OnClickListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.settings_push_switch)
	protected Button pushSwitch;// 推送
	
	@InjectView(R.id.settings_push_voice)
	protected RelativeLayout settings_push_voice;
	@InjectView(R.id.settings_voice_push_switch)
	protected Button pushVoiceSwitch;// 推送_声音
	
	@InjectView(R.id.settings_push_shake)
	protected RelativeLayout settings_push_shake;
	@InjectView(R.id.settings_push_shake_switch)
	protected Button pushShakeSwitch;// 推送_震动
	
	@InjectView(R.id.settings_update)
	protected RelativeLayout update ;// 更新
	@InjectView(R.id.settings_cache)
	protected RelativeLayout cleanCache;// 清理缓存
	@InjectView(R.id.settings_feedback)
	protected RelativeLayout feedbackLayout;// 反馈
	@InjectView(R.id.settings_about)
	protected RelativeLayout settings_about;
	@InjectView(R.id.user_logout)
	protected TextView logout;// 退出
	
	private static final int GO_LOGIN = 13;
	SharePreferenceUtil mSharedUtil;
	
	boolean pushSwitchBool = false;
	boolean pushVoiceSwitchBool = false;
	boolean pushShakeSwitchBool = false;
	
	ProgressDialog progress;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setTitle(R.string.user_setting);
		setSupportActionBar(toolbar);
		initViews();
		initEvents();
		initDatas();
	}
	
	public void initViews(){
		progress = new ProgressDialog(this);
		mSharedUtil = mApplication.getSpUtil();
	}
	
	public void initEvents(){
		logout.setOnClickListener(this);
		update.setOnClickListener(this);
		cleanCache.setOnClickListener(this);
		feedbackLayout.setOnClickListener(this);
		pushSwitch.setOnClickListener(this);
		pushVoiceSwitch.setOnClickListener(this);
		pushShakeSwitch.setOnClickListener(this);
		settings_about.setOnClickListener(this);
	}
	
	public void initDatas(){
		User user = HttpProxy.getCurrentUser(mContext);
		if(user != null){
			logout.setText("退出登录");
		}else{
			logout.setText("登录");
		}
		// 初始化
		boolean isAllowNotify = mSharedUtil.isAllowPushNotify();
		if (isAllowNotify) {
			pushSwitchBool = true;
			pushSwitch.setSelected(true);
		} else {
			pushSwitchBool = false;
			pushSwitch.setSelected(false);
			settings_push_voice.setVisibility(View.GONE);
			settings_push_shake.setVisibility(View.GONE);
		}
		boolean isAllowVoice = mSharedUtil.isAllowVoice();
		if (isAllowVoice) {
			pushVoiceSwitchBool = true;
			pushVoiceSwitch.setSelected(true);
		} else {
			pushVoiceSwitchBool = false;
			pushVoiceSwitch.setSelected(false);
		}
		boolean isAllowVibrate = mSharedUtil.isAllowVibrate();
		if (isAllowVibrate) {
			pushShakeSwitchBool = true;
			pushShakeSwitch.setSelected(true);
		} else {
			pushShakeSwitchBool = false;
			pushShakeSwitch.setSelected(false);
		}
	}
	
	private void initOtherData(String name) {
		userManager.queryUser(name, new FindListener<User>() {

			@Override
			public void onError(int arg0, String arg1) {}

			@Override
			public void onSuccess(List<User> arg0) {
				if (arg0 != null && arg0.size() > 0) {
					User user = arg0.get(0);
					Intent chatIntent = new Intent(mContext , ChatActivity.class);
					chatIntent.putExtra("user", user);
					mContext.startActivity(chatIntent);
					progress.dismiss();
				} else {
//					ShowLog("onSuccess 查无此人");
				}
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settings_push_switch: 
			if(pushSwitchBool){
				pushSwitchBool = false;
				settings_push_voice.setVisibility(View.GONE);
				settings_push_shake.setVisibility(View.GONE);
			}else{
				pushSwitchBool = true;
				settings_push_voice.setVisibility(View.VISIBLE);
				settings_push_shake.setVisibility(View.VISIBLE);
			}
			mSharedUtil.setPushNotifyEnable(pushSwitchBool);
			pushSwitch.setSelected(pushSwitchBool);
			break;
		case R.id.settings_voice_push_switch: 
			if(pushVoiceSwitchBool){
				pushVoiceSwitchBool = false;
			}else{
				pushVoiceSwitchBool = true;
			}
			mSharedUtil.setAllowVoiceEnable(pushVoiceSwitchBool);
			pushVoiceSwitch.setSelected(pushVoiceSwitchBool);
			break;
		case R.id.settings_push_shake_switch: 
			if(pushShakeSwitchBool){
				pushShakeSwitchBool = false;
			}else{
				pushShakeSwitchBool = true;
			}
			mSharedUtil.setAllowVibrateEnable(pushShakeSwitchBool);
			pushShakeSwitch.setSelected(pushShakeSwitchBool);
			break;
		case R.id.settings_update: // 更新
			BmobUpdateAgent.forceUpdate(mContext);
			break;
		case R.id.settings_cache:
			ImageLoader.getInstance().clearDiscCache();
			ShowToast("清除缓存完毕");
			break;
		case R.id.settings_feedback:
			// 反馈
			// startAnimActivity(ConstructActivity.class);
			progress.setMessage("正在初始化...");
			progress.setCanceledOnTouchOutside(false);
			progress.show();
			initOtherData("13652390539");
			break;
		case R.id.user_logout:
			if(isLogined()){
				logoutDialog();
			}else{
				redictToLogin(GO_LOGIN);
			}
			break;
		case R.id.settings_about:
			startAnimActivity(AboutActivity.class);
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * 判断用户是否登录
	 * @return
	 */
	private boolean isLogined(){
		User user = HttpProxy.getCurrentUser(mContext);
		if(user != null){
			return true;
		}
		return false;
	}
	
	/**
	 *  跳转到登入
	 */
	private void redictToLogin(int requestCode){
		Intent intent = new Intent();
		intent.setClass(this, RstAndLoginActivity.class);
		startActivityForResult(intent, requestCode);
		ShowToast("请先登录。");
	}
	
	
	public void logoutDialog(){
		final AlertDialogView alertDialog = new AlertDialogView(this,"");
		alertDialog.setContent1("退出", new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				logout();
				alertDialog.cancel();
			}
		});
	}
	
	public void logout(){
		MyApplication.getInstance().logout();
		BmobUser.logOut(mContext);
		ShowToast("退出成功。");
		setResult(RESULT_OK);
		finish();
	}
}
