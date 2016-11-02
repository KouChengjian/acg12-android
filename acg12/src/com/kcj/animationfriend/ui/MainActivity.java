package com.kcj.animationfriend.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.InjectView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.inteface.EventListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.android.material.ui.fragment.DialogFragment;
import com.android.material.view.Dialog;
import com.android.material.view.SimpleDialog;
import com.android.material.view.ThemeManager;
import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.MyMessageReceiver;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.ui.fragment.ContactFragment;
import com.kcj.animationfriend.ui.fragment.FindFragment;
import com.kcj.animationfriend.ui.fragment.HomeFragment;
import com.kcj.animationfriend.ui.fragment.RecentFragment;
import com.kcj.animationfriend.ui.fragment.SettingFragment;
import com.kcj.animationfriend.view.SearchPopWindow;
import com.liteutil.util.Log;

/**
 * @ClassName: MainActivity
 * @Description: 主界面
 * @author: KCJ
 * @date: 2014-11
 */
public class MainActivity extends BaseActivity implements EventListener ,
    OnClickListener,Toolbar.OnMenuItemClickListener{

	protected Button[] mTabs;
	protected HomeFragment homeFragment;
	protected FindFragment findFragment;
	protected SettingFragment settingFragment;
	public static RecentFragment recentFragments;
	public static ContactFragment contactFragment;
	protected Fragment[] fragments;
	protected int index;
	protected int currentTabIndex;
	@InjectView(R.id.iv_message_tips)
	protected ImageView iv_message_tips;
	@InjectView(R.id.iv_setting_tips)
	protected ImageView iv_setting_tips;
	public static long firstTime;
	protected NewBroadcastReceiver newReceiver;
	protected TagBroadcastReceiver userReceiver;
	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	protected ActionBarDrawerToggle drawerToggle;
	protected DrawerLayout drawerLayout;
	protected User user;
	protected SearchPopWindow searchPopWindow;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			setTranslucentStatus(true);
//		}
//		// 沉浸式
		setSystemBarTintManager();
		// 更新APP
		BmobUpdateAgent.update(this);
		initViews();
		initDatas();
		initEvent();
		
	}

	public void initViews() {
		searchPopWindow = new SearchPopWindow(this);
		if (MyApplication.getInstance().getIsDeclaration()) {
			 initWarmPrompt();
		}
		// init actionbar
		setTitle(R.string.app_name);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		// tabs
		mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.nav_home);
        mTabs[1] = (Button) findViewById(R.id.nav_find);
        mTabs[2] = (Button) findViewById(R.id.nav_set);
        mTabs[0].setSelected(true);
		// init DrawerLayout
//		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.opendrawer, R.string.closedrawer);
//		drawerLayout.setDrawerListener(drawerToggle);
        // fragment
		contactFragment = new ContactFragment();
		recentFragments = new RecentFragment();
		homeFragment    = new HomeFragment();
		findFragment    = new FindFragment();
		settingFragment = new SettingFragment();
		fragments = new Fragment[] { homeFragment, findFragment,settingFragment};
		// 开启广播接收器
		initNewMessageBroadCast();
		initTagMessageBroadCast();
	}

	public void initEvent() {
//		setupDrawerContent(navigationView);
//		roundImageView.setOnClickListener(this);
		toolbar.setOnMenuItemClickListener(this);
	}

	public void initDatas() {
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragments[0])
				.add(R.id.fragment_container, fragments[1]).hide(fragments[1]).show(fragments[0]).commit();
		// 初始用户信息
		user = HttpProxy.getCurrentUser(this);
//		if(user != null){
//			String avatarFile = user.getAvatar();
//			if(null != avatarFile){
//				ImageLoader.getInstance().displayImage(avatarFile, roundImageView, MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),new SimpleImageLoadingListener(){
//					@Override
//					public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
//						super.onLoadingComplete(imageUri, view, loadedImage);
//					}
//				});
//			}
//			if(TextUtils.isEmpty(user.getNick())){
//				tv_nav_nick.setText(user.getUsername());
//			}else{
//				tv_nav_nick.setText(user.getNick());
//			}
//			if(user.getSex().equals(Constant.SEX_FEMALE)){
//				iv_nav_sex.setBackgroundResource(R.drawable.icon_pop_girl);
//			}else{
//				iv_nav_sex.setBackgroundResource(R.drawable.icon_pop_boy);
//			}
//			tv_nav_signature.setText(user.getSignature());
//		}
	}
	
	private void initNewMessageBroadCast() {
		// 注册接收消息广播
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(
				BmobConfig.BROADCAST_NEW_MESSAGE);
		// 优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(newReceiver, intentFilter);
	}

	private void initTagMessageBroadCast() {
		// 注册接收消息广播
		userReceiver = new TagBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(
				BmobConfig.BROADCAST_ADD_USER_MESSAGE);
		// 优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(userReceiver, intentFilter);
	}

	public void initWarmPrompt() {
		Dialog.Builder builder = null;
		boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
		builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
            	 MyApplication.getInstance().setIsDeclaration(false);
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        ((SimpleDialog.Builder)builder).message(getResources().getString(R.string.app_declaration))
        .title(getResources().getString(R.string.app_declaration_title))
        .positiveAction(getResources().getString(R.string.app_declaration_know))
        .negativeAction(getResources().getString(R.string.app_declaration_cancel));
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// 小圆点提示
		if (BmobDB.create(this).hasUnReadMsg()) { // 未阅读的信息
			iv_setting_tips.setVisibility(View.VISIBLE);
		} else {
			iv_setting_tips.setVisibility(View.GONE);
		}
		if (BmobDB.create(this).hasNewInvite()) { // 新的tag消息
			iv_setting_tips.setVisibility(View.VISIBLE);
		} else {
			iv_setting_tips.setVisibility(View.GONE);
		}
		// 新消息到达，重新刷新界面
		MyMessageReceiver.ehList.add(this);// 监听推送的消息
		// 清空消息未读数-这个要在刷新之后
		MyMessageReceiver.mNewNum = 0;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// drawerToggle.syncState();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MyMessageReceiver.ehList.remove(this);// 监听推送的消息
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			unregisterReceiver(newReceiver);
			unregisterReceiver(userReceiver);
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		MenuItem item = menu.findItem(R.id.menu_main_download);
		item.setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.menu_main_search:
			searchPopWindow.showPopupWindow(toolbar);;
	        break;
		case R.id.menu_main_download:
			if(HttpProxy.isUserEnter(mContext,new ParameCallBack() {
				@Override
				public void onCall(Object object) {
					
				}})){
				Intent intent = new Intent(mContext , UserResActivity.class);
				intent.putExtra("data", user);
				mContext.startActivity(intent);
			}
	        break;
	    case R.id.menu_main_about:
	    	  startAnimActivity(AboutActivity.class);
	        break;
	    }
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggleDrawer();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_nav_avatar:
			break;
		default:
			break;
		}
	}
	
	/**
     * button点击事件
     */
    public void onTabSelect(View view) {
    	onTabSelect(view.getId());
    }
    
    public void onTabSelect(int id) {
    	switch (id) {
    	case R.id.nav_home:
			index = 0;
			break;
		case R.id.nav_find:
			index = 1;
			break;
		case R.id.nav_set:
			index = 2;
			break;
        }
    	if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        //把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
	
	public void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem menuItem) {
				switch (menuItem.getItemId()) {
			    case R.id.nav_res:
//			    	if(isUserEnter()){
//			    		Intent intent = new Intent(mContext , UserResActivity.class);
//						intent.putExtra("data", user);
//						mContext.startActivity(intent);
//			    	    drawerLayout.closeDrawers();
//			    	}
		    	    break;
		        case R.id.nav_friend:
		        	if(HttpProxy.isUserEnter(mContext,new ParameCallBack() {
						@Override
						public void onCall(Object object) {
							
						}})){
		        		startAnimActivity(ContactActivity.class);
		        	}
		        	drawerLayout.closeDrawers();
		    	    break;
				case R.id.nav_home:
					index = 0;
					break;
				case R.id.nav_find:
					index = 1;
					break;
				}
				if(menuItem.getItemId() == R.id.nav_home || menuItem.getItemId() == R.id.nav_find){
					setTitle(menuItem.getTitle());
					if (currentTabIndex != index) {
						FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
						trx.hide(fragments[currentTabIndex]);
						if (!fragments[index].isAdded()) {
							trx.add(R.id.fragment_container,fragments[index]);
						}
						trx.show(fragments[index]).commit();
					}
					currentTabIndex = index;
					menuItem.setChecked(true);
					drawerLayout.closeDrawers();
				}
				return true;
			}
		});
	}

	private void toggleDrawer() {
		if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
		} else {
			drawerLayout.openDrawer(GravityCompat.START);
		}
	}

	@Override
	public void onAddUser(BmobInvitation message) {
		refreshInvite(message);
	}

	@Override
	public void onMessage(BmobMsg message) {
		refreshNewMsg(message);
	}

	@Override
	public void onNetChange(boolean isNetConnected) {
		if (isNetConnected) {
			ShowToast(R.string.network_tips);
		}
	}

	@Override
	public void onOffline() {}

	@Override
	public void onReaded(String arg0, String arg1) {}

	/**
	 * @Title: notifyAddUser
	 * @Description: 刷新好友请求
	 */
	private void refreshInvite(BmobInvitation message) {
		boolean isAllow = MyApplication.getInstance().getSpUtil()
				.isAllowVoice();
		if (isAllow) {
			MyApplication.getInstance().getMediaPlayer().start();
		}
		iv_setting_tips.setVisibility(View.VISIBLE);
		String tickerText = message.getFromname() + "请求添加好友";
		boolean isAllowVibrate = MyApplication.getInstance().getSpUtil()
				.isAllowVibrate();
		BmobNotifyManager.getInstance(this).showNotify(isAllow, isAllowVibrate,
				R.drawable.logo, tickerText, message.getFromname(),
				tickerText.toString(), NewFriendActivity.class);
	}

	/**
	 * @Title: refreshNewMsg
	 * @Description: 刷新界面
	 */
	private void refreshNewMsg(BmobMsg message) {
		// 声音提示
		boolean isAllow = MyApplication.getInstance().getSpUtil()
				.isAllowVoice();
		if (isAllow) {
			MyApplication.getInstance().getMediaPlayer().start();
		}
		iv_message_tips.setVisibility(View.VISIBLE);
		iv_setting_tips.setVisibility(View.VISIBLE);
		// 也要存储起来
		if (message != null) {
			BmobChatManager.getInstance(MainActivity.this).saveReceiveMessage(
					true, message);
		}
	}

	/**
	 * 新消息广播接收者
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 刷新界面
			refreshNewMsg(null);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}

	/**
	 * 标签消息广播接收者
	 */
	private class TagBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			BmobInvitation message = (BmobInvitation) intent
					.getSerializableExtra("invite");
			refreshInvite(message);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			ShowToast(R.string.double_click_logout);
		}
		firstTime = System.currentTimeMillis();
	}

}
