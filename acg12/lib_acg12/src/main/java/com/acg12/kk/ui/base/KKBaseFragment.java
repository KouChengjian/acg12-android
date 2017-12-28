package com.acg12.kk.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.acg12.kk.conf.BaseConstant;
import com.acg12.kk.utils.Toastor;
import com.acg12.kk.utils.ViewUtil;

import butterknife.ButterKnife;

public class KKBaseFragment extends Fragment {

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
        startAnimActivity(cla, null, BaseConstant.RESULT_ACTIVITY_REG_DEFAULT);
    }

    protected void startAnimActivity(Class<?> cla, int code) {
        startAnimActivity(cla, null, code);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle) {
        startAnimActivity(cls, bundle, BaseConstant.RESULT_ACTIVITY_REG_DEFAULT);
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

    public void showSoftInputView(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
