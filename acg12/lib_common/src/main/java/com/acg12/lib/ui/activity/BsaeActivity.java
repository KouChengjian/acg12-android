package com.acg12.lib.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.acg12.lib.constant.BaseConstant;
import com.acg12.lib.utils.ActivityTack;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.ViewServer;
import com.acg12.lib.utils.ViewUtil;

import butterknife.ButterKnife;

/**
 * @ClassName: BsaeActivity
 * @Description: 重构
 */
public class BsaeActivity extends AppCompatActivity implements View.OnClickListener {

    protected Context mContext;
    protected String mTag;
    protected ActivityTack mActivityTack = ActivityTack.getInstanse();
    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;

    @Override
    @SuppressWarnings("ResourceType")
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mContext = this;
        mTag = this.getClass().getSimpleName();
        mActivityTack.addActivity(this);
        if (BaseConstant.debug) {
            ViewServer.get(this).addWindow(this);
        }

        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseConstant.debug) {
            ViewServer.get(this).setFocusedWindow(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityTack.removeActivity(this);
        if (BaseConstant.debug) {
            ViewServer.get(this).removeWindow(this);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }


    protected void aminFinish() {
        super.finish();
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            aminFinish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == -1) {
            aminFinish();
        }
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toastor.ShowToast(text);
                }
            });
        }
    }

    protected void ShowToast(final int resId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toastor.ShowToast(resId);
            }
        });
    }

    public void ShowToastView(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toastor.ShowToastView(text);
            }
        });
    }

    public void ShowToastView(final int resId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toastor.ShowToastView(resId);
            }
        });
    }

    protected void popActivity(Class<?>... clas) {
        for (Class activity : clas) {
            mActivityTack.popActivity(mActivityTack.getActivityByClass(activity));
        }
    }

    protected void exitActivity() {
        for (Activity activity : mActivityTack.activityList) {
            mActivityTack.popActivity(activity);
        }
    }

    protected void showSoftInputView() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
