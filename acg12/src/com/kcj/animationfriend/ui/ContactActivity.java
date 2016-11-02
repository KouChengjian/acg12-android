package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import butterknife.InjectView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.inteface.EventListener;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.MyMessageReceiver;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;


/**
 * @ClassName: ContactActivity
 * @Description: 联系人
 * @author: KCJ
 * @date: 2015-8-23
 */
public class ContactActivity extends BaseSwipeBackActivity implements EventListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_contact);
		setTitle(R.string.contact);
		setSupportActionBar(toolbar);
		initViews();
	}
	
	@Override
	public void initViews() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container_friend, MainActivity.contactFragment)
				.commit();
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
	public void onAddUser(BmobInvitation arg0) {
		MainActivity.contactFragment.refresh();
	}

	@Override
	public void onMessage(BmobMsg message) {
		refreshNewMsg(message);
	}

	@Override
	public void onNetChange(boolean arg0) {}

	@Override
	public void onOffline() {}

	@Override
	public void onReaded(String arg0, String arg1) {}
	
	/** 
	 * @Title: refreshNewMsg
	 * @Description: 刷新界面
	 */
	private void refreshNewMsg(BmobMsg message){
		// 声音提示
		boolean isAllow = MyApplication.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			MyApplication.getInstance().getMediaPlayer().start();
		}
		//也要存储起来
		if(message != null){
			BmobChatManager.getInstance(mContext).saveReceiveMessage(true,message);
		}
//		if(currentTabIndex == 2){
//			//当前页面如果为会话页面，刷新此页面
//			if(recentFragment != null){
//				recentFragment.refresh();
//			}
//		}
	}
}
