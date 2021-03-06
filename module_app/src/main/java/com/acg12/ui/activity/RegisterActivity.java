package com.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.acg12.R;
import com.acg12.entity.User;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.CountDownTimerUtils;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.RegisterView;


public class RegisterActivity extends SkinBaseActivity<RegisterView> implements View.OnClickListener {

    private User user;
    private boolean flag = false;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        user = new User(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_verify) {
            verify();
        } else if (view.getId() == R.id.iv_pwd_show) {
            flag = !flag;
            mView.edtShowOrHide(flag);
        } else if (view.getId() == R.id.btn_register) {
            register();
        }
    }

    private void verify() {
        if (!ViewUtil.isNetConnected(mContext)) return;

        String name = mView.getUsername();
        if (TextUtils.isEmpty(name)) {
            ShowToast(R.string.toast_error_username_null);
            return;
        }

        final CountDownTimerUtils timerUtils = new CountDownTimerUtils(mView.getBtnVerify(), 60000, 1000);
        ;
        final ProgressDialog progress = ViewUtil.startLoading(mContext, "正在获取验证码...");

        user.setUsername(name);
        HttpRequestImpl.getInstance().verify(user, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                progress.dismiss();
                timerUtils.start();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                progress.dismiss();
                LogUtil.e(msg);
                ShowToast(msg);
            }
        });

    }

    private void register() {
        if (!ViewUtil.isNetConnected(mContext)) return;
        String name = mView.getUsername();
        String verify = mView.getVerify();
        String pas = mView.getPassword();
        if (TextUtils.isEmpty(name)) {
            ShowToast(R.string.toast_error_username_null);
            return;
        }

        if (TextUtils.isEmpty(verify)) {
            ShowToast(R.string.toast_error_verify_null);
            return;
        }

        if (TextUtils.isEmpty(pas)) {
            ShowToast(R.string.toast_error_password_null);
            return;
        }

        if (pas.length() < 6) {
            ShowToast(R.string.toast_error_password);
            return;
        }

        user.setUsername(name);
        user.setPassword(pas);
        user.setVerify(verify);

        final ProgressDialog progress = ViewUtil.startLoading(mContext, "正在注册中...");

        HttpRequestImpl.getInstance().register(user, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                progress.dismiss();
                ShowToast("注册成功，请登录");
                aminFinish();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                progress.dismiss();
                LogUtil.e(msg);
                ShowToast(msg);
            }
        });
    }
}
