package org.acg12.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.skin.loader.entity.DynamicAttr;
import com.skin.loader.listener.IDynamicNewView;
import com.skin.loader.listener.ISkinUpdate;
import com.skin.loader.loader.SkinInflaterFactory;
import com.skin.loader.loader.SkinManager;

import org.acg12.config.Constant;
import org.acg12.db.DaoBaseImpl;
import org.acg12.utlis.ActivityTack;
import org.acg12.utlis.SystemBarUtlis;
import org.acg12.utlis.Toastor;
import org.acg12.utlis.ViewServer;
import org.acg12.utlis.ViewUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @ClassName: BaseActivity
 * @Description: 重构 
 * @author: KCJ
 * @date: 2016-07-26 10:30
 */
public class BaseActivity extends AppCompatActivity implements ISkinUpdate, IDynamicNewView {

	protected boolean replace = true;
	protected Context mContext;
	protected String mTag;
	protected ActivityTack mActivityTack = ActivityTack.getInstanse();
	
	@Override
	protected void onCreate(Bundle bundle) {
		initSkin();
		super.onCreate(bundle);
		mContext = this;
		mTag = this.getClass().getSimpleName();
		mActivityTack.addActivity(this);
		SystemBarUtlis.skinThemeUpdate(this ,replace);
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
		SkinManager.getInstance().attach(this);
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
		SkinManager.getInstance().detach(this);
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

	/** --------------------skin 切换-------------------------*/
	private boolean isResponseOnSkinChanging = true;

	private SkinInflaterFactory mSkinInflaterFactory;

	public void initSkin(){
		mSkinInflaterFactory = new SkinInflaterFactory();
		LayoutInflaterCompat.setFactory(LayoutInflater.from(this) , mSkinInflaterFactory);
//		try {
////			Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
////			field.setAccessible(true);
////			field.setBoolean(getLayoutInflater(), false);
//
//
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
	}

	protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId){
		mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
	}

	protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs){
		mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
	}

	final protected void enableResponseOnSkinChanging(boolean enable){
		isResponseOnSkinChanging = enable;
	}

	@Override
	public void onThemeUpdate() {
		if(!isResponseOnSkinChanging) return;
		mSkinInflaterFactory.applySkin();
		SystemBarUtlis.skinThemeUpdate(this ,replace);
	}

	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
		SkinManager.getInstance().load();
	}
}
