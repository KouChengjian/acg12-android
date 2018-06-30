package org.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import org.acg12.entity.User;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.ui.activity.PresenterActivityImpl;

import org.acg12.R;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.views.ResetPwdView;
import com.acg12.lib.utils.CountDownTimerUtils;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.ViewUtil;


public class ResetPwdActivity extends PresenterActivityImpl<ResetPwdView> implements View.OnClickListener{

    User user;
    boolean flag = false;

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
        if(view.getId() == R.id.tv_verify){
            verify();
        } else if(view.getId() == R.id.iv_pwd_show){
            flag = !flag;
            mView.edtShowOrHide(flag);
        } else if(view.getId() == R.id.btn_resetpwd){
            resetpwd();
        }
    }

    private void verify(){
        if(!ViewUtil.isNetConnected(mContext))return;

        String name = mView.getUsername();
        if (TextUtils.isEmpty(name)) {
            ShowToast(R.string.toast_error_username_null);
            return;
        }

        final CountDownTimerUtils timerUtils = new CountDownTimerUtils(mView.getBtnVerify(), 60000, 1000);;
        final ProgressDialog progress = ViewUtil.startLoading(mContext , "正在获取验证码...");

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

    private void resetpwd(){
        if(!ViewUtil.isNetConnected(mContext))return;
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

        if (pas.length() < 6 ) {
            ShowToast(R.string.toast_error_password);
            return;
        }

        user.setUsername(name);
        user.setPassword(pas);
        user.setVerify(verify);

        final ProgressDialog progress = ViewUtil.startLoading(mContext , "正在重置密码中...");

        HttpRequestImpl.getInstance().register(user, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                progress.dismiss();
                ShowToast("重置密码成功，请登录");
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
