package org.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import org.acg12.R;
import org.acg12.bean.User;
import org.acg12.conf.Config;
import org.acg12.conf.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.LoginView;
import org.acg12.utlis.LogUtil;
import org.acg12.utlis.Network;
import org.acg12.utlis.TelNumMatch;
import org.acg12.utlis.ViewUtil;

public class LoginActivity extends PresenterActivityImpl<LoginView> implements View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == Constant.TOOLBAR_ID){
            aminFinish();
        } else if(id == R.id.btn_login){
            login();
        }
    }

    public void login(){
        boolean isNetConnected = Network.isConnected(mContext);
        if (!isNetConnected) {
            ShowToastView(R.string.network_tips);
            return;
        }

        String name = mView.getUsername();
        String password = mView.getPassword();

        if (TextUtils.isEmpty(name)) {
            ShowToastView(R.string.toast_error_username_null);
            return;
        }

        int type = new TelNumMatch(name).matchNum();
        if (type == 4 || type == 5) {
            ShowToastView(R.string.toast_error_username);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ShowToastView(R.string.toast_error_password_null);
            return;
        }

        if (password.length() < 6 ) {
            ShowToastView(R.string.toast_error_password);
            return;
        }

        final ProgressDialog progress = ViewUtil.startLoading(mContext , getResources().getString(R.string.toast_login_loading));

        final User user = new User(mContext);
        user.setUsername(name);
        user.setPassword(password);

        HttpRequestImpl.getInstance().login(user, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                progress.dismiss();
                Config.userEventBus().post(result);
                finish();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                progress.dismiss();
                LogUtil.e(msg);
                ShowToastView(msg);
            }
        });
    }
}
