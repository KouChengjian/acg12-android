package com.kcj.animationfriend.ui;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.PushListener;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.util.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * @ClassName: PersonInfoActivity
 * @Description: 个人信息(点击用户时)
 * @author: KCJ
 * @date: 
 */
public class PersonInfoActivity extends BaseSwipeBackActivity implements OnClickListener{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.personal_info) // user信息
	protected RelativeLayout ll_resource_info; 
	@InjectView(R.id.personal_icon)
	protected ImageView iv_resource_icon;
	@InjectView(R.id.personl_name)
	protected TextView tv_resource_name;
	@InjectView(R.id.personl_sex)
	protected ImageView personl_sex;
	@InjectView(R.id.personl_signature)
	protected TextView tv_resource_sign;
	@InjectView(R.id.rl_personl_res) // 打开资源
	protected RelativeLayout rl_personl_res;
	@InjectView(R.id.tv_personl_account) // 账号
	protected TextView tv_personl_account;
	@InjectView(R.id.rl_personl_send) // 发送消息
	protected TextView rl_personl_send;
	@InjectView(R.id.rl_personl_add) // 添加好友
	protected TextView rl_personl_add;
	 
	public User user ;
	public String type;
	public ShowType type1;
	
	public enum ShowType{
		contactType ,albumType
	}
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_person);
		setTitle(R.string.personinfo);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		user = (User)getIntent().getSerializableExtra("data");
		type = getIntent().getStringExtra("from");
		initViews();
		initEvent();
		initDatas();
	}
	
	@Override
	public void initViews() {
		super.initViews();
	}
	
	@Override
	public void initEvent() {
		super.initEvent();
		rl_personl_res.setOnClickListener(this);
		rl_personl_send.setOnClickListener(this);
		rl_personl_add.setOnClickListener(this);
	}
	
	@SuppressWarnings("deprecation")
	public void initDatas(){
		tv_personl_account.setText(user.getUsername());
		final String avatar = user.getAvatar();
		if (!TextUtils.isEmpty(avatar)) {
			ImageLoader.getInstance().displayImage(avatar, iv_resource_icon, ImageLoadOptions.getOptions());
		} else {
			iv_resource_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.user_icon_default));
		}
		final String nick = user.getNick();
		if (!TextUtils.isEmpty(nick)) {
			tv_resource_name.setText(nick);
		}else{
			tv_resource_name.setText(user.getUsername());
		}
		initOtherData(user.getUsername());
		if(!type.isEmpty()){
			if(type.equals("contactType")){
				rl_personl_add.setVisibility(View.GONE);
			}else if(type.equals("albumType")){
				if(HttpProxy.userIsFriend(mContext,user)){
					rl_personl_add.setVisibility(View.GONE);
				}else{
					rl_personl_add.setVisibility(View.VISIBLE);
				}
				if(HttpProxy.isCurUser(mContext,user)){
					rl_personl_send.setVisibility(View.GONE);
					rl_personl_add.setVisibility(View.GONE);
				}
			}
		}
	}
	
	private void initOtherData(String name) {
		userManager.queryUser(name, new FindListener<User>() {

			@Override
			public void onError(int arg0, String arg1) {}

			@Override
			public void onSuccess(List<User> arg0) {
				if (arg0 != null && arg0.size() > 0) {
					user = arg0.get(0);
					tv_resource_sign.setText(user.getSignature());
					if(user.getSex().equals(Constant.SEX_FEMALE)){
						personl_sex.setBackgroundResource(R.drawable.icon_pop_girl);
					}else{
						personl_sex.setBackgroundResource(R.drawable.icon_pop_boy);
					}
				} else {
//					ShowLog("onSuccess 查无此人");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_personl_res:
			Intent intent = new Intent(mContext , UserResActivity.class);
			intent.putExtra("data", user);
			mContext.startActivity(intent);
			break;
		case R.id.rl_personl_send:
			Intent chatIntent = new Intent(mContext , ChatActivity.class);
			chatIntent.putExtra("user", user);
			mContext.startActivity(chatIntent);
			break;
		case R.id.rl_personl_add:
			addFriend();
			break;
		default:
			break;
		}
	}
	
	public void addFriend(){
		final ProgressDialog progress = new ProgressDialog(mContext);
		progress.setMessage("正在添加...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		//发送tag请求
		BmobChatManager.getInstance(mContext).sendTagMessage(BmobConfig.TAG_ADD_CONTACT, user.getObjectId(),new PushListener() {
			
			@Override
			public void onSuccess() {
				progress.dismiss();
				ShowToast("发送请求成功，等待对方验证!");
			}
			
			@Override
			public void onFailure(int arg0, final String arg1) {
				progress.dismiss();
				ShowToast("发送请求失败，请重新添加!");
			}
		});
	}
}
