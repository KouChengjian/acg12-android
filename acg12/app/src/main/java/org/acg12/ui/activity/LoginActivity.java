package org.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import org.acg12.R;
import org.acg12.bean.User;
import org.acg12.config.Config;
import org.acg12.config.Constant;
import org.acg12.db.DaoBaseImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.LoginView;
import org.acg12.utlis.LogUtil;
import org.acg12.utlis.Network;
import org.acg12.utlis.TelNumMatch;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

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
            finish();
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

        final ProgressDialog progress = new ProgressDialog(mContext);
        progress.setMessage(getResources().getString(R.string.toast_login_loading));
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        final User user = new User(mContext);
        user.setUsername(name);
        user.setPassword(password);

        user.login( new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    progress.setMessage(getResources().getString(R.string.toast_login_loading_msg));
                    userInfo(progress , user);
                }else {
                    progress.dismiss();
                    LogUtil.e(mTag, e.toString());
                    ShowToastView(e.toString());
                }
            }
        });


    }

    public void userInfo(final ProgressDialog progress ,final User u){

        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject("a203eba875", new QueryListener<User>() {

            @Override
            public void done(User result, BmobException e) {
                if(e==null){
                    u.setAvatar(result.getAvatar());
                    u.setNick(result.getNick());
                    u.setSignature(result.getSignature());
                    u.setSex(result.getSex());
                    LogUtil.e(u.getObjectId()+"====");
                    LogUtil.e(u.getUsername()+"====");
                    LogUtil.e(u.getAvatar()+"====");
                    LogUtil.e(u.getSignature()+"====");
                    LogUtil.e(u.getSex()+"====");
                    long i = DaoBaseImpl.getInstance().saveUser(u);
                    LogUtil.e(i+"====");
                    Config.userEventBus().post(u);
                    progress.dismiss();
                    finish();
                }else{
                    progress.dismiss();
                    LogUtil.e(mTag, e.toString());
                    ShowToastView(e.toString());
                }
            }

        });
    }
}
