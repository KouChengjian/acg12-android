package com.kcj.animationfriend.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.ui.SearchActivity;
import com.kcj.animationfriend.ui.UserResActivity;


/**
 * @ClassName: SearchPopWindow
 * @Description: 搜索
 * @author: KCJ
 * @date: 
 */
public class SearchPopWindow extends PopupWindow implements OnClickListener{

	private View view;
	Context mContext;
	TextView back;
	TextView search;
	DeletableEditText deletableEditText;
	
	public SearchPopWindow(final Activity context){
		mContext = context;
		LayoutInflater inflater = context. getLayoutInflater();
		view = inflater.inflate(R.layout.include_search_pop, null);
		back = (TextView)view.findViewById(R.id.tv_search_back);
		search = (TextView)view.findViewById(R.id.tv_search);
		deletableEditText = (DeletableEditText)view.findViewById(R.id.edt_search);
		// 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);     
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.SearchPopWindow);
        
        back.setOnClickListener(this);
        search.setOnClickListener(this);
	}
	
	public void showPopupWindow(View parent ) {
		int[] location = new int[2];  
		parent.getLocationOnScreen(location);  
        showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - this.getHeight());  
//		showAsDropDown(parent);
	} 
	
	public void dismissPopupWindow() {
		dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_search_back:
			dismissPopupWindow();
			break;
        case R.id.tv_search:
			String str = deletableEditText.getText().toString();
			if(str == null || str.isEmpty())
				return;
			Intent intent = new Intent(mContext , SearchActivity.class);
			intent.putExtra("title", str);
			mContext.startActivity(intent);
			deletableEditText.setText("");
			dismissPopupWindow();
			break;
		default:
			break;
		}
	} 
}
