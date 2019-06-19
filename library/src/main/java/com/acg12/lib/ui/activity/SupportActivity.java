package com.acg12.lib.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;


import com.acg12.lib.ui.SupportView;
import com.acg12.lib.utils.AppManager;
import com.acg12.lib.utils.AppStartUtil;
import com.acg12.lib.utils.DoubleClickUtil;
import com.acg12.lib.utils.ToastUtil;
import com.acg12.lib.widget.dialog.factory.DialogLoader;
import com.acg12.lib.widget.dialog.factory.LoadingDialog;

import butterknife.ButterKnife;


/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2019/1/7 13:47
 * Description:
 */
public abstract class SupportActivity extends AppCompatActivity implements SupportView, View.OnClickListener{

    protected Context mContext = null; //context
    private DialogLoader dialogLoader;
    private int activityCloseEnterAnimation;
    private int activityCloseExitAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        create(savedInstanceState); // dagger2注解
        super.onCreate(savedInstanceState);
        mContext = this;
        AppManager.get().addActivity(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);

        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId
                , new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();

        created(savedInstanceState);
        bindEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (DoubleClickUtil.isFastDoubleClick()) {
            return;
        }
        if (v.getId() == -1) {
            aminFinish();
        }
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void setLoadingDialog(DialogLoader dialog) {
        dialogLoader = dialog;
    }

    @Override
    public void showProgressDialog(String msg) {
        if (null == dialogLoader) dialogLoader = LoadingDialog.get();
        dialogLoader.showProgressDialog(mContext, msg);
    }

    @Override
    public void dismissProgressDialog() {
        if (null == dialogLoader) dialogLoader = LoadingDialog.get();
        dialogLoader.dismissProgressDialog(mContext);
    }

    @Override
    public void showMsg(String msg) {
        ToastUtil.showShort(msg);
    }

    /**
     * Dagger2 use in your application module(not used in 'base' module)
     */
    protected void create(Bundle savedInstanceState){}

    /**
     * bind layout resource file
     */
    protected abstract int getLayoutId();

    /**
     * init views and events here
     */
    protected abstract void created(Bundle savedInstanceState);

    /**
     * init views and events here
     */
    protected void bindEvent() {}

    protected void startAnimActivity(Class<?> cls) {
        startAnimActivity(cls, null, -1, 0);
    }

    protected void startAnimActivity(Class<?> cls, int code) {
        startAnimActivity(cls, null, -1, code);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle) {
        startAnimActivity(cls, bundle, 0);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle, int code) {
        startAnimActivity(cls, bundle, -1, code);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle, int flag, int code) {
        AppStartUtil.startAnimActivity(this, cls, bundle, flag, code);
    }

    public void finishResult(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        finishResult(intent);
    }

    public void finishResult(Intent intent) {
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void aminFinish() {
        super.finish();
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
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
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        AppManager.get().finishActivity(this);
        super.onDestroy();
    }
}
