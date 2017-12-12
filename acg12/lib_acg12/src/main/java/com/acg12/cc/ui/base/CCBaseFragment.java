package com.acg12.cc.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.acg12.cc.conf.Constant;
import com.acg12.cc.utils.Toastor;
import com.acg12.cc.utils.ViewUtil;

import butterknife.ButterKnife;

public class CCBaseFragment extends Fragment {

    protected Context mContext;
    protected String mTag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = this.getClass().getSimpleName();
        mContext = getActivity();
    }

    public void setContentView(View view) {
        ButterKnife.bind(this, view);
    }

    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }

    /*-------内部调用类---------*/
    protected void startAnimActivity(Class<?> cla) {
        startAnimActivity(cla, null, Constant.RESULT_ACTIVITY_REG_DEFAULT);
    }

    protected void startAnimActivity(Class<?> cla, int code) {
        startAnimActivity(cla, null, code);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle) {
        startAnimActivity(cls, bundle, Constant.RESULT_ACTIVITY_REG_DEFAULT);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle, int code) {
        ViewUtil.startAnimActivity(this, cls, bundle, code);
    }

    protected void ShowToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toastor.ShowToast(text);
                }
            });
        }
    }

    protected void ShowToast(final int resId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toastor.ShowToast(resId);
            }
        });
    }

    protected void ShowToastView(final String text) {
        if (!TextUtils.isEmpty(text)) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toastor.ShowToastView(text);
                }
            });
        }
    }

    protected void ShowToastView(final int resId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toastor.ShowToastView(resId);
            }
        });
    }

}
