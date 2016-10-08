package com.kcj.animationfriend.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.InjectView;
import cn.bmob.im.db.BmobDB;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.ContactActivity;
import com.kcj.animationfriend.ui.RecentActivity;
import com.kcj.animationfriend.ui.RstAndLoginActivity;
import com.kcj.animationfriend.ui.SettingActivity;
import com.kcj.animationfriend.ui.UserInfoActivity;
import com.kcj.animationfriend.ui.UserResActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @ClassName: SettingFragment
 * @Description: 设置
 * @author: KCJ
 * @date:
 */
public class SettingFragment extends BaseFragment implements OnClickListener{

	@InjectView(R.id.sv_setting_container)
	protected ScrollView sv_setting_container;
	@InjectView(R.id.re_myinfo)
	protected RelativeLayout re_myinfo;
	@InjectView(R.id.iv_avatar)
	protected ImageView iv_avatar;
	@InjectView(R.id.tv_name)
	protected TextView tv_name;
	@InjectView(R.id.iv_sex)
	protected ImageView iv_sex;
	@InjectView(R.id.tv_signature)
	protected TextView tv_signature;
	@InjectView(R.id.rl_res)
	protected RelativeLayout re_res;
	@InjectView(R.id.rl_friend)
	protected RelativeLayout rl_friend;
	@InjectView(R.id.rl_message)
	protected RelativeLayout rl_message;
	@InjectView(R.id.rl_setting)
	protected RelativeLayout rl_setting;
	
	@InjectView(R.id.iv_friend_tips)
	protected ImageView iv_friend_tips;
	@InjectView(R.id.iv_message_tips)
	protected ImageView iv_message_tips;
	
	@InjectView(R.id.rl_setting_empty)
	protected RelativeLayout rl_setting_empty;
	@InjectView(R.id.tv_setting_login)
	protected TextView tv_setting_login;
	
	protected User user;
	protected boolean hidden;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container,false);
		setContentView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initEvents();
		initDatas();
	}

	public void initViews() {
		user = HttpProxy.getCurrentUser(mContext);
		if(user == null){
			sv_setting_container.setVisibility(View.GONE);
			rl_setting_empty.setVisibility(View.VISIBLE);
		}else{
			sv_setting_container.setVisibility(View.VISIBLE);
			rl_setting_empty.setVisibility(View.GONE);
		}
	}

	public void initEvents() {
		re_myinfo.setOnClickListener(this);
		re_res.setOnClickListener(this); 
		rl_friend.setOnClickListener(this); 
		rl_message.setOnClickListener(this); 
		rl_setting.setOnClickListener(this);
		tv_setting_login.setOnClickListener(this);
	}

	public void initDatas() {
		if(user != null){
			String avatarFile = user.getAvatar();
			if(null != avatarFile){
				ImageLoader.getInstance().displayImage(avatarFile, iv_avatar, MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),new SimpleImageLoadingListener(){
					@Override
					public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
				});
			}
			if(TextUtils.isEmpty(user.getNick())){
				tv_name.setText(user.getUsername());
			}else{
				tv_name.setText(user.getNick());
			}
			if(user.getSex().equals(Constant.SEX_FEMALE)){
				iv_sex.setBackgroundResource(R.drawable.icon_pop_girl);
			}else{
				iv_sex.setBackgroundResource(R.drawable.icon_pop_boy);
			}
			tv_signature.setText(user.getSignature());
		}else{
			sv_setting_container.setVisibility(View.GONE);
			rl_setting_empty.setVisibility(View.VISIBLE);
		}
	}
	
	@SuppressWarnings("static-access")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == getActivity().RESULT_OK){
			switch (requestCode) {
			case Constant.REQUEST_CODE_LOGIN:
				initViews();
				initDatas();
				break;
			case Constant.REQUEST_CODE_EXIT:
				sv_setting_container.setVisibility(View.GONE);
				rl_setting_empty.setVisibility(View.VISIBLE);
				break;
			}
		}
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if(!hidden){
			User curuser = HttpProxy.getCurrentUser(mContext);
			if(user == null){
				if(curuser != null){
					user = curuser;
					initViews();
					initDatas();
				}
			}else{
				user = curuser;
				initDatas();
			}
		}
		if(BmobDB.create(mContext).hasNewInvite()){
			iv_friend_tips.setVisibility(View.VISIBLE);
		}else{
			iv_friend_tips.setVisibility(View.GONE);
		}
		if(BmobDB.create(mContext).hasUnReadMsg()){ // 未阅读的信息
			iv_message_tips.setVisibility(View.VISIBLE);
		}else{
			iv_message_tips.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(BmobDB.create(mContext).hasNewInvite()){
			iv_friend_tips.setVisibility(View.VISIBLE);
		}else{
			iv_friend_tips.setVisibility(View.GONE);
		}
		if(BmobDB.create(mContext).hasUnReadMsg()){ // 未阅读的信息
			iv_message_tips.setVisibility(View.VISIBLE);
		}else{
			iv_message_tips.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
        switch (v.getId()) {
        case R.id.re_myinfo:
			startAnimActivity(UserInfoActivity.class);
			break;
        case R.id.rl_res:
        	Intent intent = new Intent(mContext , UserResActivity.class);
			intent.putExtra("data", user);
			mContext.startActivity(intent);
			break;
        case R.id.rl_friend:
        	startAnimActivity(ContactActivity.class);
			break;
        case R.id.rl_message:
        	startAnimActivity(RecentActivity.class);
			break;
		case R.id.rl_setting:
			startActivityForResult(new Intent(getActivity(),SettingActivity.class), Constant.REQUEST_CODE_EXIT);
			break;
		case R.id.tv_setting_login:
			startActivityForResult(new Intent(getActivity(),RstAndLoginActivity.class), Constant.REQUEST_CODE_LOGIN);
			break;
		}		
	}
}
