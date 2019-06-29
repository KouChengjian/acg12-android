package com.acg12.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.R;


/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2018/7/7 14:15
 * Description:
 */
public class TipLayoutView extends RelativeLayout implements View.OnClickListener{

    private ViewStub mLayoutNull, mLayoutError, mLayoutLoading;
    private LinearLayout mLLTipviewNull, mLLTipviewError, mLLTipviewLoading;
    private ImageView mTvTiplayoutPic;
    private TextView mTvTiplayoutMsg;
    private BGButton mReloadButton;
    private OnReloadClick onReloadClick;

    public TipLayoutView(Context context) {
        super(context);
        initView();
    }

    public TipLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TipLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.include_tiplayout_view, this);
        view.setOnClickListener(null);
        mLayoutNull = findViewById(R.id.layout_null);
        mLayoutError = findViewById(R.id.layout_error);
        mLayoutLoading = findViewById(R.id.layout_loading);

        showLoading();
    }

    /**
     * 现实文本内容
     */
    public void showContent() {
        if (this.getVisibility() == View.VISIBLE) {
            this.setVisibility(GONE);
        }
    }

    /**
     * 显示加载中布局
     */
    public void showLoading() {
        resetStatus();
        if (mLLTipviewLoading == null) {
            View view = mLayoutLoading.inflate();
            mLLTipviewLoading =  view.findViewById(R.id.ll_tipview_loading);
            mLLTipviewLoading.setBackgroundResource(R.color.white);
        }
        if (mLLTipviewLoading.getVisibility() == View.GONE) {
            mLLTipviewLoading.setVisibility(View.VISIBLE);
        }
    }

    public void showRestLoading() {
        resetStatus();
        if (mLLTipviewLoading == null) {
            View view = mLayoutLoading.inflate();
            mLLTipviewLoading =  view.findViewById(R.id.ll_tipview_loading);
        }
        mLLTipviewLoading.setBackgroundResource(R.color.transparent);
        if (mLLTipviewLoading.getVisibility() == View.GONE) {
            mLLTipviewLoading.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示没有网络
     */
    public void showNetError() {
        resetStatus();
        if (mLLTipviewError == null) {
            View view = mLayoutError.inflate();
            mLLTipviewError =  view.findViewById(R.id.ll_tipview_error);
            mTvTiplayoutPic =  view.findViewById(R.id.tv_tiplayout_pic);
            mTvTiplayoutMsg =  view.findViewById(R.id.tv_tiplayout_msg);
            mReloadButton =  view.findViewById(R.id.bg_refush);
            mReloadButton.setOnClickListener(this);
        }
        if (mLLTipviewError.getVisibility() == View.GONE) {
            mLLTipviewError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示加载失败
     */
    public void showLoginError() {
        resetStatus();
        if (mLLTipviewError == null) {
            View view = mLayoutError.inflate();
            mLLTipviewError =  view.findViewById(R.id.ll_tipview_error);
            mTvTiplayoutPic =  view.findViewById(R.id.tv_tiplayout_pic);
            mTvTiplayoutMsg =  view.findViewById(R.id.tv_tiplayout_msg);
            mReloadButton =  view.findViewById(R.id.bg_refush);
            mReloadButton.setOnClickListener(this);
        }
        mTvTiplayoutMsg.setText("获取消息失败，点击重新获取");
        mReloadButton.setText("重新获取");
        if (mLLTipviewError.getVisibility() == View.GONE) {
            mLLTipviewError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示空数据且存在刷新按钮
     */
    public void showEmptyOrRefresh() {
        resetStatus();
        if (mLLTipviewError == null) {
            View view = mLayoutError.inflate();
            mLLTipviewError =  view.findViewById(R.id.ll_tipview_error);
            mTvTiplayoutPic =  view.findViewById(R.id.tv_tiplayout_pic);
            mTvTiplayoutMsg =  view.findViewById(R.id.tv_tiplayout_msg);
            mReloadButton =  view.findViewById(R.id.bg_refush);
            mReloadButton.setOnClickListener(this);
        }
        mTvTiplayoutPic.setImageResource(R.mipmap.bg_loading_null);
        mTvTiplayoutMsg.setText("");
        mTvTiplayoutMsg.setVisibility(View.GONE);
        mReloadButton.setText("再次获取");
        if (mLLTipviewError.getVisibility() == View.GONE) {
            mLLTipviewError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示加载失败
     */
    public void showCustomError(int id, String msg, String btn) {
        resetStatus();
        if (mLLTipviewError == null) {
            View view = mLayoutError.inflate();
            mLLTipviewError = view.findViewById(R.id.ll_tipview_error);
            mTvTiplayoutPic = view.findViewById(R.id.tv_tiplayout_pic);
            mTvTiplayoutMsg = view.findViewById(R.id.tv_tiplayout_msg);
            mReloadButton = view.findViewById(R.id.bg_refush);
            mReloadButton.setOnClickListener(this);
        }
        mTvTiplayoutPic.setImageResource(id);
        mTvTiplayoutMsg.setText(msg);

        if (btn != null && !btn.isEmpty()) {
            mReloadButton.setText(btn);
            mReloadButton.setVisibility(View.VISIBLE);
        } else {
            mReloadButton.setVisibility(View.GONE);
        }
        if (mLLTipviewError.getVisibility() == View.GONE) {
            mLLTipviewError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示空数据
     */
    public void showEmpty() {
        resetStatus();
        if (mLLTipviewNull == null) {
            View view = mLayoutNull.inflate();
            mLLTipviewNull = (LinearLayout) view.findViewById(R.id.ll_tipview_null);
        }
        if (mLLTipviewNull.getVisibility() == View.GONE) {
            mLLTipviewNull.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏所有布局
     */
    public void resetStatus() {
        this.setVisibility(VISIBLE);
        if (mLLTipviewNull != null) {
            mLLTipviewNull.setVisibility(View.GONE);
        }
        if (mLLTipviewError != null) {
            mLLTipviewError.setVisibility(View.GONE);
        }
        if (mLLTipviewLoading != null) {
            mLLTipviewLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bg_refush){
            if(onReloadClick != null){
                resetStatus();
                showLoading();
                onReloadClick.onReload();
            }
        }
    }

    /**
     * 重新加载点击事件
     */
    public void setOnReloadClick(OnReloadClick onReloadClick) {
        this.onReloadClick = onReloadClick;
    }

    public interface OnReloadClick {
        void onReload();
    }
}
