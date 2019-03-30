package com.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.acg12.conf.AccountManager;
import com.acg12.lib.conf.EventConfig;
import com.acg12.cache.DaoBaseImpl;
import com.acg12.entity.Update;
import com.acg12.entity.User;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.widget.dialog.UpdateDialog;

import com.acg12.R;
import com.acg12.lib.constant.Constant;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.SettingView;
import com.acg12.widget.dialog.AlertDialogView;

import java.util.ArrayList;
import java.util.List;


public class SettingActivity extends SkinBaseActivity<SettingView> implements View.OnClickListener {

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        List<DynamicAttr> mDynamicAttr = new ArrayList<>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TOOLBARVIEW, R.color.theme_primary));
        dynamicAddView(mView.getToolBarView(), mDynamicAttr);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            aminFinish();
        } else if(id == R.id.settings_cache){
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

        final ProgressDialog progress = ViewUtil.startLoading(mContext , "获取版本...");

        User user = DaoBaseImpl.getInstance(mContext).getCurrentUser();
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
                alertDialog.cancel();
                logout();
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
        AccountManager.getInstance().logout();
        finish();
     }

}
