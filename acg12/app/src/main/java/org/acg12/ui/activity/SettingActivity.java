package org.acg12.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.acg12.R;
import org.acg12.config.Constant;
import org.acg12.ui.base.BaseActivity;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.views.SettingView;

import butterknife.BindView;

public class SettingActivity extends PresenterActivityImpl<SettingView> implements View.OnClickListener {


    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            finish();
        } else if(id == R.id.settings_cache){

        } else if(id == R.id.settings_update) {

        } else if(id == R.id.settings_feedback) {

        } else if(id == R.id.settings_about) {
            startAnimActivity(AboutActivity.class);
        } else if(id == R.id.user_logout) {

        }
    }
}
