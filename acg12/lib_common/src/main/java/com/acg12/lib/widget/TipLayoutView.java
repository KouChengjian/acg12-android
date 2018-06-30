package com.acg12.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.R;

import java.util.List;

public class TipLayoutView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private View mContainer;
    private ProgressBar mProgressLoading;
    private ViewStub mLayoutLoadNull;
    private ImageView mLoadNullImageview;
    private TextView mLoadNullTextview;
    private TextView mLoadNullUpdate;

    private int errPic = R.mipmap.bg_loading_null;
    private String errMsg = "啥也没有了，去其它地方看看吧";

    private OnReloadClick onReloadClick;

    public TipLayoutView(Context context) {
        this(context, null);
    }

    public TipLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.common_loading_schedule, null);
        mLayoutLoadNull = (ViewStub) view.findViewById(R.id.layout_load_null);
        mProgressLoading = (ProgressBar) view.findViewById(R.id.progress_loading);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
    }

    public void setContainer(View container) {
        mContainer = container;
    }

    public void setOnReloadClick(OnReloadClick onReloadClick) {
        this.onReloadClick = onReloadClick;
    }

    public void startProgress() {
        hideNullLayout();
        hideContainer();
        mProgressLoading.setVisibility(View.VISIBLE);
    }

    public void stopProgress() {
        hideNullLayout();
        showContainer();
        mProgressLoading.setVisibility(View.GONE);
    }

    public void stopProgressOrEmpty() {
        showNullLayout();
        hideContainer();
        mProgressLoading.setVisibility(View.GONE);
    }

    public void stopProgressOrNetError() {
        showNullLayout();
        hideContainer();
        mProgressLoading.setVisibility(View.GONE);
    }

    public void stopProgress(List list) {
        mProgressLoading.setVisibility(View.GONE);
        if (list != null && list.size() > 0) {
            hideNullLayout();
            showContainer();
        } else {
            showNullLayout();
            hideContainer();
        }
    }

    public void stopProgress(int list) {
        mProgressLoading.setVisibility(View.GONE);
        if (list > 0) {
            hideNullLayout();
            showContainer();
        } else {
            showNullLayout();
            hideContainer();
        }
    }

    public void stopProgress(Object object) {
        mProgressLoading.setVisibility(View.GONE);
        if (object != null) {
            hideNullLayout();
            showContainer();
        } else {
            showNullLayout();
            hideContainer();
        }
    }

    private void hideNullLayout() {
        if (mLoadNullImageview != null && mLoadNullTextview != null) {
            mLoadNullTextview.setText("");
            if (mLoadNullImageview.getVisibility() == View.VISIBLE) {
                mLoadNullImageview.setVisibility(View.GONE);
            }
            if (mLoadNullUpdate.getVisibility() == View.VISIBLE) {
                mLoadNullUpdate.setVisibility(View.GONE);
            }
        }
    }

    private void showNullLayout() {
        if (mLoadNullImageview == null && mLoadNullTextview == null) {
            View view = mLayoutLoadNull.inflate();
            mLoadNullImageview = (ImageView) view.findViewById(R.id.iv_load_null);
            mLoadNullTextview = (TextView) view.findViewById(R.id.tv_load_null);
            mLoadNullUpdate = (TextView) view.findViewById(R.id.tv_load_update);
            mLoadNullUpdate.setOnClickListener(this);

        }
        if (mLoadNullUpdate.getVisibility() == View.GONE) {
            mLoadNullUpdate.setVisibility(View.VISIBLE);
        }
        mLoadNullTextview.setText(errMsg);
        if (mLoadNullImageview.getVisibility() == View.GONE) {
            mLoadNullImageview.setVisibility(View.VISIBLE);
        }
        mLoadNullImageview.setImageResource(errPic);
    }

    private void hideContainer() {
        if (mContainer != null) {
            if (mContainer.getVisibility() == View.VISIBLE) {
                mContainer.setVisibility(View.GONE);
            }
        }
    }

    private void showContainer() {
        if (mContainer != null) {
            if (mContainer.getVisibility() == View.GONE) {
                mContainer.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_load_update) {
            if (onReloadClick != null) {
                onReloadClick.onReload();
            }
        }
    }

    public interface OnReloadClick {
        void onReload();
    }
}
