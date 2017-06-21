package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import org.acg12.R;
import org.acg12.bean.User;
import org.acg12.conf.Config;
import org.acg12.conf.Constant;
import org.acg12.db.DaoBaseImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.SettingView;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.widget.AlertDialogView;

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
        } else if(id == R.id.settings_update) {

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
