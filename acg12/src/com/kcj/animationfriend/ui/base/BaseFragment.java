package com.kcj.animationfriend.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import butterknife.ButterKnife;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;

import com.kcj.animationfriend.config.UserProxy;
import com.liteutil.util.Toastor;

/**
 * @ClassName: BaseFragment
 * @Description: 
 * @author: kcj
 * @date:  
 */
public abstract class BaseFragment extends Fragment{
	
	protected Context mContext;
	protected String TAG; // 打印的名称
	public BmobUserManager userManager;
	public BmobChatManager manager;
	public LayoutInflater mInflater;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getSimpleName();
		mContext = getActivity();
		userManager = BmobUserManager.getInstance(getActivity());
		manager = BmobChatManager.getInstance(getActivity());
		mInflater = LayoutInflater.from(getActivity());
	}
	
    public void initViews(){}
	
	public void initEvent(){}
	
	public void initDatas(){}
	
	public void setContentView(View view) {
		ButterKnife.inject(this, view);
	}
	
	public View findViewById(int paramInt) {
		return getView().findViewById(paramInt);
	}
	
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}
	
	public void startAnimActivity(Class<?> cla) {
		getActivity().startActivity(new Intent(getActivity(), cla));
	}
	
	protected void startAnimActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}
	
	public void ShowToast(String text) {
		Toastor.ShowToast(text);
	}

	public void ShowToast(int text) {
		Toastor.ShowToast(text);
	}
	
	/**
	 * 只有title initTopBarLayoutByTitle
	 * @Title: initTopBarLayoutByTitle
	 * @throws
	 */
//	public void initTopBarForOnlyTitle(String titleName) {
//		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
//		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
//		mHeaderLayout.setDefaultTitle(titleName);
//	}
	
	/**
	 * 初始化标题栏-带左右按钮(左按钮默认)
	 */
//	public void initTopBarForBoth(String titleName, int rightDrawableId,
//			onRightImageButtonClickListener listener) {
//		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
//		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
//		mHeaderLayout.setTitleAndLeftImageButton(titleName,
//				R.drawable.base_action_bar_back_bg_selector,
//				new OnLeftButtonClickListener());
//		mHeaderLayout.setTitleAndRightImageButton(titleName, rightDrawableId,
//				listener);
//	}
	
	/**
	 * 初始化标题栏-带左右按钮(左按钮自定义)
	 */
//	public void initTopBarForBoth(String titleName, 
//			int leftDrawableId ,onLeftImageButtonClickListener leftlistener,
//			int rightDrawableId,onRightImageButtonClickListener listener) {
//		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
//		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
//		mHeaderLayout.setTitleAndLeftImageButton(titleName, leftDrawableId,leftlistener);
//		mHeaderLayout.setTitleAndRightImageButton(titleName, rightDrawableId,listener);
//	}
	
	/**
	 * 只有左边按钮和Title initTopBarLayout
	 */
//	public void initTopBarForLeft(String titleName) {
//		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
//		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
//		mHeaderLayout.setTitleAndLeftImageButton(titleName,
//				R.drawable.base_action_bar_back_bg_selector,
//				new OnLeftButtonClickListener());
//	}
	
//	public void initTopBarForRight(String titleName ,int rightDrawableId,onRightImageButtonClickListener listener) {
//		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
//		mHeaderLayout.init(HeaderStyle.TITLE_RIGHT_IMAGEBUTTON);
//		mHeaderLayout.setTitleAndRightImageButton(titleName, rightDrawableId,listener);
//	}
	
	/**
	 * 显示中间按钮  只有左边按钮和Title 
	 */
//	public void initTopBarForCenterNo() {
//		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
//		mHeaderLayout.init(HeaderStyle.TITLE_CENTER_NO);
//	}
	
	// 左边按钮的点击事件
//	public class OnLeftButtonClickListener implements 
//		onLeftImageButtonClickListener {
//			@Override
//		public void onClick(View v) {
//			getActivity().finish();	
//		}
//	}
	
	/** 隐藏软键盘
	  * hideSoftInputView
	  * @Title: hideSoftInputView
	  */
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getActivity().getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
}
