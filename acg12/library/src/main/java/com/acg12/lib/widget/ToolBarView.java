package com.acg12.lib.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.R;

public class ToolBarView extends FrameLayout {

    private Context mContext;
    private Toolbar mToolbar;
    private RelativeLayout mTitleContainer;
    private TextView mTitleRight;
    private View mTitleStatus;

    public ToolBarView(@NonNull Context context) {
        this(context, null);
    }

    public ToolBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
        initEvent();
        initDatas();
    }

    private void initViews() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_action_bar, this);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTitleContainer = (RelativeLayout) view.findViewById(R.id.title_container);
        mTitleRight = (TextView) view.findViewById(R.id.title_right);
        mTitleStatus = view.findViewById(R.id.title_status);
    }

    private void initEvent() {

    }

    private void initDatas() {
        resetStatusHeight();
    }

    public void resetStatusHeight(int height){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        mTitleStatus.setLayoutParams(params);
    }

    public int resetStatusHeight() {
        int height = getStatusBarHeight();
        if (height > 0) {
            resetStatusHeight(height);
        }
        return height;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setBackground(int color){
        mToolbar.setBackgroundColor(color);
        mTitleStatus.setBackgroundColor(color);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setNavigationIcon(){
        setNavigationIcon(R.mipmap.ic_action_back);
    }

    public void setNavigationIcon(int rId){
        mToolbar.setNavigationIcon(rId);
    }

    public void setNavigationTitle(String title){
        mToolbar.setTitle(title);
    }

    public void setNavigationOrBreak(String title){
        setNavigationTitle(title);
        setNavigationIcon();
    }

    public void setNavigationOrBreak(int rId ,String title){
        setNavigationTitle(title);
        setNavigationIcon(rId);
    }

    public void addTitleView(View view){
        mTitleContainer.removeAllViews();
        mTitleContainer.addView(view);
    }
}
