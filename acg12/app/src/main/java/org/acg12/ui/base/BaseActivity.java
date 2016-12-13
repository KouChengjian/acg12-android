package org.acg12.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import org.acg12.config.Constant;
import org.acg12.db.DaoBaseImpl;
import org.acg12.utlis.ActivityTack;
import org.acg12.utlis.Toastor;
import org.acg12.utlis.ViewServer;
import org.acg12.utlis.ViewUtil;

import butterknife.ButterKnife;

/**
 * @ClassName: BaseActivity
 * @Description: 重构 
 * @author: KCJ
 * @date: 2016-07-26 10:30
 */
public class BaseActivity extends AppCompatActivity {

	protected Context mContext;
	protected String mTag;
	protected ActivityTack mActivityTack = ActivityTack.getInstanse();
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mContext = this;
		mTag = this.getClass().getSimpleName();
		mActivityTack.addActivity(this);
		if(Constant.debug){
			ViewServer.get(this).addWindow(this);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(Constant.debug){
			ViewServer.get(this).setFocusedWindow(this);
		}
	}
	
	@Override
    public void onPause() {
        super.onPause();
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mActivityTack.removeActivity(this);
		if(Constant.debug){
			ViewServer.get(this).removeWindow(this);
		}
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.bind(this);
	}
	
	@Override
	public void finish() {
		super.finish();
		mActivityTack.removeActivity(this);
		ViewUtil.exitAnimActivity(this);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mActivityTack.removeActivity(this);
		ViewUtil.exitAnimActivity(this);
	}
	
	/*-------内部调用类---------*/
	protected void startAnimActivity(Class<?> cla) {
		ViewUtil.startAnimActivity(this, cla);
	}

	protected void startAnimActivity(Intent intent) {
		ViewUtil.startAnimActivity(this, intent);
	}
	
	protected void startAnimActivity(Class<?> cls, Bundle bundle) {
		ViewUtil.startAnimActivity(this, cls , bundle);
	}

	protected boolean hasCurrentUser(){
		if(DaoBaseImpl.getInstance().getCurrentUser() != null)
			return true;
		else{
			//startAnimActivity(LoginRegisterActivity.class);
			return false;
		}
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

	public void ShowToastView(final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toastor.ShowToastView(text);
			}
		});
	}

	public void ShowToastView(final int resId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toastor.ShowToastView(resId);
			}
		});
	}
	
	public void showSoftInputView(){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
	}
	
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public void initViews(){}

	public void initEvent(){}

	public void initDatas(){}

}
