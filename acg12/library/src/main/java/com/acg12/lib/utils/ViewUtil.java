package com.acg12.lib.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.acg12.lib.R;
import com.acg12.lib.listener.ParameCallBack;


public class ViewUtil {
	
	public static void setText(TextView textview , int i ){
		setText(textview,i+"");
	}
	
	public static void setText(TextView textview , String str ){
		if(textview == null) return;
		textview.setCompoundDrawables(null, null, null, null);
		if(str != null && !str.isEmpty()){
			textview.setText(str);
		}else{
			textview.setText("");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setText(Context context , TextView textview  , String str , int id1, int id2){
		Drawable drawable1 = context.getResources().getDrawable(id1);
		Drawable drawable2 = context.getResources().getDrawable(id2);
		drawable1.setBounds(0,0 ,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		drawable2.setBounds(0,0 ,drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
		textview.setCompoundDrawables(drawable1, null, drawable2, null);
		textview.setText(str);
	}
	
	@SuppressWarnings("deprecation")
	public static void setText(Context context , TextView textview, String str  , int id1){
		Drawable drawable1 = context.getResources().getDrawable(id1);
		drawable1.setBounds(0,0 ,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		textview.setCompoundDrawables(drawable1, null, null, null);
		textview.setText(str);
	}
	
	public static void setText(TextView textview , String str , Drawable drawable1){
		drawable1.setBounds(0,0 ,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		textview.setCompoundDrawables(drawable1, null, null, null);
		textview.setText(str);
	}

	public static void setText(TextView textview , String str , Drawable drawable1, Drawable drawable2){
		drawable1.setBounds(0,0 ,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		drawable2.setBounds(0,0 ,drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
		textview.setCompoundDrawables(drawable1, null, drawable2, null);
		textview.setText(str);
	}

	/**
	 * 下划线
	 * @param textview
	 * @param s1
	 * @param s2
	 * @param color
	 * @param parameCallBack
     */
	public static void setText(TextView textview , String s1 , String s2 , final int color, final ParameCallBack parameCallBack){
		setText(textview , s1 , s2 ,color , true ,  parameCallBack);

	}

	public static void setText(TextView textview , String s1 , String s2 , final int color , final boolean underlineText , final ParameCallBack parameCallBack){
		textview.setText(s1);
		SpannableString spStr = new SpannableString(s2);
		spStr.setSpan(new ClickableSpan() {
			@Override
			public void updateDrawState(TextPaint ds) {
				super.updateDrawState(ds);
				ds.setColor(color);       //设置文件颜色
				ds.setUnderlineText(underlineText);      //设置下划线
			}

			@Override
			public void onClick(View widget) {
				if(parameCallBack != null){
					parameCallBack.onCall(widget);
				}
			}
		}, 0, s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
		textview.append(spStr);
		textview.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
	}

	public static void setText(TextView textview , String s , int start, int end , final int color , final boolean underlineText , final ParameCallBack parameCallBack) {
		SpannableString spStr = new SpannableString(s);
		spStr.setSpan(new ClickableSpan() {
			@Override
			public void updateDrawState(TextPaint ds) {
				super.updateDrawState(ds);
				ds.setColor(color);       //设置文件颜色
				ds.setUnderlineText(underlineText);      //设置下划线
			}

			@Override
			public void onClick(View widget) {
				if(parameCallBack != null){
					parameCallBack.onCall(widget);
				}
			}
		}, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
		textview.append(spStr);
		textview.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
	}
	
	/**
     * 动态设置listView的高度 
     *  item 总布局必须是linearLayout
     * @param listView 
     */  
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {  
            return;  
        }  
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }  
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight  
                + (listView.getDividerHeight() * (listAdapter.getCount()-1))  
                ;  //+15
        listView.setLayoutParams(params);  
    }

	public static void startAnimActivity(Activity activity , Class<?> cls, Bundle bundle , int code) {
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		activity.startActivityForResult(intent ,code);
	}

	public static void startAnimActivity(Fragment fragment , Class<?> cls, Bundle bundle , int code) {
		Intent intent = new Intent();
		intent.setClass(fragment.getActivity(), cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		fragment.startActivityForResult(intent ,code);
	}

	public static void startRouterActivity(String activityRouter) {
		startRouterActivity(activityRouter , null);
	}

	public static void startRouterActivity(String activityRouter , Bundle bundle) {
//		ARouter aRouter = ARouter.getInstance();
//		Postcard postcard = aRouter.build(activityRouter);
//		if(bundle != null){
//			postcard.with(bundle);
//		}
//		postcard.navigation();
	}

	@TargetApi(21)
	public static void startTransitionActivity(Context mContext , Class<?> cls, Bundle bundle , ImageView view) {
		Intent intent = new Intent();
		intent.setClass(mContext, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
				(Activity)mContext , view , view.getTransitionName()).toBundle());
	}

	public static ProgressDialog startLoading(Context context , String msg){
		ProgressDialog progress = new ProgressDialog(context);
		progress.setMessage(msg);
		progress.setCanceledOnTouchOutside(false);
		progress.setCancelable(false);
		progress.show();
		return progress;
	}

	public static boolean isNetConnected(Context mContext){
		boolean isNetConnected = Network.isConnected(mContext);
		if (!isNetConnected) {
			Toastor.ShowToastView(R.string.kk_network_tips);
			return false;
		}
		return true;
	}

}
