package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import butterknife.InjectView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.db.BmobDB;
import cn.bmob.v3.listener.UpdateListener;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.BlackListAdapter;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.util.CollectionUtils;
import com.kcj.animationfriend.view.AlertDialogView;


/**
 * @ClassName: BlackListActivity
 * @Description: 黑名单列表
 * @author: KCJ
 * @date: 2015-8-24
 */
public class BlackListActivity extends BaseActivity implements OnItemClickListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.list_blacklist)
	protected ListView listview;
	protected BlackListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_blacklist);
		setTitle(R.string.blacklist);
		setSupportActionBar(toolbar);
		initViews();
		initEvent();
	}
	
	@Override
	public void initViews() {
		adapter = new BlackListAdapter(this, BmobDB.create(this).getBlackList());
		listview.setAdapter(adapter);
	}
	
	@Override
	public void initEvent() {
		super.initEvent();
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		BmobChatUser invite = (BmobChatUser) adapter.getItem(arg2);
		showRemoveBlackDialog(arg2,invite);
	}
	
	/** 
	  * @Title: showRemoveBlackDialog
	  * @Description: 显示移除黑名单对话框
	  * @param @param position
	  * @param @param invite 
	  * @return void
	  * @throws
	  */
	public void showRemoveBlackDialog(final int position, final BmobChatUser user) {
		final AlertDialogView alertDialog = new AlertDialogView(this,"");
		alertDialog.setContent1("移出黑名单", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				moveUserBrackList(position,user);
				alertDialog.cancel();
			}
		});
	}
	
	public void moveUserBrackList(int position,BmobChatUser user){
		adapter.remove(position);
		userManager.removeBlack(user.getUsername(),new UpdateListener() {
			
			@Override
			public void onSuccess() {
				ShowToast("移出黑名单成功");
				//重新设置下内存中保存的好友列表
				MyApplication.getInstance().setContactList(CollectionUtils.list2map(BmobDB.create(getApplicationContext()).getContactList()));	
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				ShowToast("移出黑名单失败:"+arg1);
			}
		});
	}
	
}
