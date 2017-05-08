package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import butterknife.InjectView;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.db.BmobDB;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.NewFriendAdapter;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.view.AlertDialogView;


/** 
 * @ClassName: NewFriendActivity
 * @Description: 新朋友
 * @author: KCJ
 * @date: 2015-8-24
 */
public class NewFriendActivity extends BaseSwipeBackActivity implements OnItemLongClickListener,OnItemClickListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.list_newfriend)
	protected ListView listview;
	protected NewFriendAdapter adapter;
	protected String from="";
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_newfriend);
		setTitle(R.string.newfriend);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		initViews();
		initEvent();
		
	}
	
	public void initViews(){
		adapter = new NewFriendAdapter(this,BmobDB.create(this).queryBmobInviteList());
		listview.setAdapter(adapter);
		if(from==null){//若来自通知栏的点击，则定位到最后一条
			listview.setSelection(adapter.getCount());
		}
	}
	
	public void initEvent(){
		listview.setOnItemLongClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		Intent intent = new Intent(mContext , PersonInfoActivity.class);
//		intent.putExtra("data", user);
//		intent.putExtra("from", "albumType");
//		mContext.startActivity(intent);
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
		BmobInvitation invite = (BmobInvitation) adapter.getItem(position);
		showDeleteDialog(position,invite);
		return false;
	}
	
	public void showDeleteDialog(final int position,final BmobInvitation invite) {
		final AlertDialogView alertDialog = new AlertDialogView(this,"");
		alertDialog.setContent1("删除好友请求", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				deleteInvite(position,invite);
				alertDialog.cancel();
			}
		});
	}
	
	/** 
	 * 删除请求
	  * deleteRecent
	  * @param @param recent 
	  * @return void
	  * @throws
	  */
	private void deleteInvite(int position, BmobInvitation invite){
		adapter.remove(position);
		BmobDB.create(this).deleteInviteMsg(invite.getFromid(), Long.toString(invite.getTime()));
	}

	

}
