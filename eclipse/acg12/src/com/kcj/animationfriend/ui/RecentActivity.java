package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import butterknife.InjectView;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.inteface.EventListener;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.MyMessageReceiver;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;

/**
 * @ClassName: RecentActivity
 * @Description: 会话
 * @author: KCJ
 * @date: 
 */
public class RecentActivity extends BaseSwipeBackActivity implements EventListener{
	
	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_recent);
		setTitle(R.string.message);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		initViews();
	}
	
	@Override
	public void initViews() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container_recent, MainActivity.recentFragments).commit();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// 新消息到达，重新刷新界面
		MyMessageReceiver.ehList.add(this);// 监听推送的消息
		//清空消息未读数-这个要在刷新之后
		MyMessageReceiver.mNewNum=0;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MyMessageReceiver.ehList.remove(this);// 监听推送的消息
	}
	
	@Override
	public void onAddUser(BmobInvitation message) {
		refreshInvite(message);
	}

	@Override
	public void onMessage(BmobMsg arg0) {
		MainActivity.recentFragments.refresh();
	}

	@Override
	public void onNetChange(boolean arg0) {}

	@Override
	public void onOffline() {}

	@Override
	public void onReaded(String arg0, String arg1) {}

	
	/** 
	 * @Title: notifyAddUser
	 * @Description: 刷新好友请求
	 * @param @param message 
	 * @return void
	 * @throws
	 */
	private void refreshInvite(BmobInvitation message){
		boolean isAllow = MyApplication.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			MyApplication.getInstance().getMediaPlayer().start();
		}
		String tickerText = message.getFromname()+"请求添加好友";
		boolean isAllowVibrate = MyApplication.getInstance().getSpUtil().isAllowVibrate();
		BmobNotifyManager.getInstance(this).showNotify(isAllow,isAllowVibrate,R.drawable.logo, tickerText, message.getFromname(), tickerText.toString(),NewFriendActivity.class);
	}
}
