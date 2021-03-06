package com.acg12.lib.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.acg12.lib.utils.ActivityTack;
import com.acg12.lib.utils.AppStartUtil;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.ViewUtil;

import butterknife.ButterKnife;

/**
 * @ClassName: BsaeActivity
 * @Description: 重构
 */
public class BsaeActivity extends AppCompatActivity implements View.OnClickListener {

    protected Context mContext;
    protected String mTag;
    protected ProgressDialog mProgressDialog;
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

        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityTack.removeActivity(this);
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

    public void finishResult() {
        finishResult(new Intent());
    }

    public void finishResult(Intent intent) {
        setResult(RESULT_OK, intent);
        finish();
    }

    /*-------内部调用类---------*/

    protected void startAnimActivity(Class<?> cls) {
        startAnimActivity(cls, null, 0);
    }

    protected void startAnimActivity(Class<?> cls, int code) {
        startAnimActivity(cls, null, code);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle) {
        startAnimActivity(cls, bundle, 0);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle, int code) {
        AppStartUtil.startAnimActivity(this, cls, bundle, -1, code);
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

    public void startLoading(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ViewUtil.startLoading(mContext, msg);
        } else {
            mProgressDialog.setMessage(msg);
            mProgressDialog.show();
        }
    }

    public void stopLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
