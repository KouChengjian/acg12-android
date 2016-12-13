package org.acg12.utlis;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
	public static void setText(Context context ,TextView textview  , String str , int id1, int id2){
		Drawable drawable1 = context.getResources().getDrawable(id1);
		Drawable drawable2 = context.getResources().getDrawable(id2);
		drawable1.setBounds(0,0 ,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		drawable2.setBounds(0,0 ,drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
		textview.setCompoundDrawables(drawable1, null, drawable2, null);
		textview.setText(str);
	}
	
	@SuppressWarnings("deprecation")
	public static void setText(Context context ,TextView textview, String str  , int id1){
		Drawable drawable1 = context.getResources().getDrawable(id1);
		drawable1.setBounds(0,0 ,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		textview.setCompoundDrawables(drawable1, null, null, null);
		textview.setText(str);
	}
	
	public static void setText(TextView textview , String str ,Drawable drawable1){
		drawable1.setBounds(0,0 ,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		textview.setCompoundDrawables(drawable1, null, null, null);
		textview.setText(str);
	}

	public static void setText(TextView textview , String str ,Drawable drawable1,Drawable drawable2){
		drawable1.setBounds(0,0 ,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		drawable2.setBounds(0,0 ,drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
		textview.setCompoundDrawables(drawable1, null, drawable2, null);
		textview.setText(str);
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
    
    public static void startAnimActivity(Context mContext ,Class<?> cla) {
    	mContext.startActivity(new Intent(mContext, cla));
		//((Activity)mContext).overridePendingTransition(R.anim.slide_in_from_right , R.anim.slide_out_to_left);
	}

    public static void startAnimActivity(Context mContext ,Intent intent) {
		mContext.startActivity(intent);
	}
    
    public static void startAnimActivity(Context mContext ,Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(mContext, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		mContext.startActivity(intent);
	}

	public static void exitAnimActivity(Context mContext) {
		//((Activity)mContext).overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
}
