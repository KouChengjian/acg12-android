package org.acg12.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import org.acg12.db.DaoBaseImpl;
import org.acg12.listener.IDynamicNewView;
import org.acg12.utlis.Toastor;
import org.acg12.utlis.ViewUtil;
import org.acg12.utlis.skin.entity.DynamicAttr;

import java.util.List;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment implements IDynamicNewView {
	
	protected Context mContext;
	protected String mTag;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		initSkin(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTag = this.getClass().getSimpleName();
		mContext = getActivity();
	}
	
	public void setContentView(View view) {
		ButterKnife.bind(this,view);
	}
	
	public View findViewById(int paramInt) {
		return getView().findViewById(paramInt);
	}

	protected void startAnimActivity(Class<?> cla) {
		ViewUtil.startAnimActivity(getActivity(), cla);
	}

	protected void startAnimActivity(Intent intent) {
		ViewUtil.startAnimActivity(getActivity(), intent);
	}
	
	protected void startAnimActivity(Class<?> cls, Bundle bundle) {
		ViewUtil.startAnimActivity(getActivity(), cls , bundle);
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
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toastor.ShowToast(text);
				}
			});
		}
	}

	public void ShowToast(final int resId) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toastor.ShowToast(resId);
			}
		});
	}

	public void ShowToastView(final String text) {
		if (!TextUtils.isEmpty(text)) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toastor.ShowToastView(text);
				}
			});
		}
	}

	public void ShowToastView(final int resId) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toastor.ShowToastView(resId);
			}
		});
	}

	public void showSoftInputView(){
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getActivity().getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public void initViews(){}

	public void initEvent(){}

	public void initDatas(){}

	/** --------------------skin 切换-------------------------*/
	private IDynamicNewView mIDynamicNewView;

	public void initSkin(Activity activity){
		try{
			mIDynamicNewView = (IDynamicNewView)activity;
		}catch(ClassCastException e){
			mIDynamicNewView = null;
		}
	}

	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		if(mIDynamicNewView == null){
			throw new RuntimeException("IDynamicNewView should be implements !");
		}else{
			mIDynamicNewView.dynamicAddView(view, pDAttrs);
		}
	}
}
