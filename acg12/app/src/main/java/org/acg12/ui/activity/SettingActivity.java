package org.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import org.acg12.R;
import org.acg12.bean.Update;
import org.acg12.bean.User;
import org.acg12.conf.Config;
import org.acg12.conf.Constant;
import org.acg12.db.DaoBaseImpl;
import org.acg12.listener.HttpRequestListener;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.SettingView;
import org.acg12.utlis.AppUtil;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.LogUtil;
import org.acg12.utlis.Network;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.AlertDialogView;
import org.acg12.widget.UpdateDialog;

public class SettingActivity extends PresenterActivityImpl<SettingView> implements View.OnClickListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            aminFinish();
        } else if(id == R.id.settings_cache){
            ImageLoadUtils.clearUniversalLoading();
            ImageLoadUtils.clearImageAllCache(this);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mView.calculateCache();
                    ShowToast("清理完成");
                }
            } , 1000);
        } else if(id == R.id.settings_update) {
            updateApp();
        } else if(id == R.id.settings_amdpwd) {
            startAnimActivity(AlterPwdActivity.class);
        }  else if(id == R.id.settings_feedback) {
            startAnimActivity(FeedbackActivity.class);
        } else if(id == R.id.settings_about) {
            startAnimActivity(AboutActivity.class);
        } else if(id == R.id.user_logout) {
            logoutDialog();
        }
    }

    public void showUpdateApp(Update result){
        UpdateDialog updateDialog = new UpdateDialog(mContext ,result);
        updateDialog.setTitle("漫友更新啦");
        updateDialog.setTitleGravity();
        updateDialog.show();
    }

    public void updateApp(){
        boolean isNetConnected = Network.isConnected(mContext);
        if (!isNetConnected) {
            ShowToastView(R.string.network_tips);
            return;
        }

        final ProgressDialog progress = ViewUtil.startLoading(mContext , "获取版本...");

        User user = DaoBaseImpl.getInstance().getCurrentUser();
        HttpRequestImpl.getInstance().updateApp(user, AppUtil.getPackageInfo(mContext).versionCode, new HttpRequestListener<Update>() {
            @Override
            public void onSuccess(Update result) {
                progress.dismiss();
                if(result.getDialogStatus() == 3){
                    result.setDialogStatus(1);
                }
                showUpdateApp(result);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                progress.dismiss();
                LogUtil.e(msg);
                ShowToastView(msg);
            }
        });
    }

    public void logoutDialog(){
        final AlertDialogView alertDialog = new AlertDialogView(this,"");
        alertDialog.setContent1("退出", new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                logout();
                alertDialog.cancel();
            }
        });
        alertDialog.setContent2("返回", new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                alertDialog.cancel();
            }
        });
    }

    public void logout(){
        DaoBaseImpl.getInstance().delTabUser();
        Config.userEventBus().post(new User(mContext));
        finish();
     }

}
