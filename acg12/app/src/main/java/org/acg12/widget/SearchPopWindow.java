package org.acg12.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import org.acg12.R;
import org.acg12.ui.activity.SearchActivity;
import org.acg12.utlis.ViewUtil;

/**
 * Created by DELL on 2017/1/6.
 */
public class SearchPopWindow extends PopupWindow implements View.OnClickListener {

    private View view;
    private Context mContext;
    private ImageView search;
    DeletableEditText deletableEditText;

    public SearchPopWindow(final Activity context){
        mContext = context;
        LayoutInflater inflater = context. getLayoutInflater();
        view = inflater.inflate(R.layout.common_pop_search, null);
        search = (ImageView)view.findViewById(R.id.tv_search);
        deletableEditText = (DeletableEditText)view.findViewById(R.id.edt_search);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
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
        this.setAnimationStyle(R.style.Animations_SearchPopWindow);

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
        if(v.getId() == R.id.tv_search){
            String str = deletableEditText.getText().toString();
            if(str == null || str.isEmpty())
                return;
            Bundle bundle = new Bundle();
            bundle.putString("title", str);
            ViewUtil.startAnimActivity(mContext , SearchActivity.class , bundle);
            dismissPopupWindow();
        }
    }
}
