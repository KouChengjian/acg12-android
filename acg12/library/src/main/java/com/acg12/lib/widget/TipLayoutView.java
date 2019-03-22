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

public class TipLayoutView extends RelativeLayout implements View.OnClickListener {

    private ViewStub mLayoutNull, mLayoutError, mLayoutLoading;
    private LinearLayout mLLTipviewNull, mLLTipviewError, mLLTipviewLoading;
    private ImageView mIvNullPic, mIvErrorPic;
    private TextView mTvNullMsg, mTvErrorMsg;
    private BGButton mBtnNull, mBtnError;
    private OnReloadClick onReloadClick;

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
        View view = inflater.inflate(R.layout.common_tiplayout_view, this);
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
            mLLTipviewLoading = (LinearLayout) view.findViewById(R.id.ll_tipview_loading);
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
            mLLTipviewLoading = (LinearLayout) view.findViewById(R.id.ll_tipview_loading);
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
            mLLTipviewError = (LinearLayout) view.findViewById(R.id.ll_tipview_error);
            mIvErrorPic = (ImageView) view.findViewById(R.id.tv_tiplayout_pic);
            mTvErrorMsg = (TextView) view.findViewById(R.id.tv_tiplayout_msg);
            mBtnError = (BGButton) view.findViewById(R.id.bg_refush);
            mBtnError.setOnClickListener(this);
        }
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
            mIvErrorPic = view.findViewById(R.id.tv_tiplayout_pic);
            mTvErrorMsg = view.findViewById(R.id.tv_tiplayout_msg);
            mBtnError = view.findViewById(R.id.bg_refush);
            mBtnError.setOnClickListener(this);
        }
        mIvErrorPic.setImageResource(id);
        mTvErrorMsg.setText(msg);

        if (btn != null && !btn.isEmpty()) {
            mBtnError.setText(btn);
            mBtnError.setVisibility(View.VISIBLE);
        } else {
            mBtnError.setVisibility(View.GONE);
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
            mLLTipviewNull = view.findViewById(R.id.ll_tipview_null);
        }
        if (mLLTipviewNull.getVisibility() == View.GONE) {
            mLLTipviewNull.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示空数据
     */
    public void showEmptyOrRefresh() {
        resetStatus();
        if (mLLTipviewNull == null) {
            View view = mLayoutNull.inflate();
            mLLTipviewNull = view.findViewById(R.id.ll_tipview_null);
            mIvNullPic = view.findViewById(R.id.tv_tiplayout_pic);
            mTvNullMsg = view.findViewById(R.id.tv_tiplayout_msg);
            mBtnNull = view.findViewById(R.id.bg_null_refush);
            mBtnNull.setOnClickListener(this);
        }
        mIvErrorPic.setImageResource(R.mipmap.bg_loading_null);
        mBtnError.setText("再次获取");
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
        if (v.getId() == R.id.bg_refush || v.getId() == R.id.bg_null_refush) {
            if (onReloadClick != null) {
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
