package com.acg12.ui.activity.setting;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.cache.AccountManager;
import com.acg12.lib.app.BaseApp;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.GlideUtil;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.SettingContract;
import com.acg12.ui.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * UserEntity kcj
 * Date 2019/06/24
 * Description: 自动生成
 */
public class SettingActivity extends BaseMvpActivity<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.tv_setting_cache)
    TextView tv_setting_cache;
    @BindView(R.id.tv_setting_update)
    TextView tv_setting_update;
    @BindView(R.id.settings_amdpwd)
    RelativeLayout settingsAmdpwd;
    @BindView(R.id.user_logout)
    TextView userLogout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        toolBarView.setNavigationOrBreak("设置");

        if (AccountManager.getInstance().isLogin()) {
            userLogout.setVisibility(View.GONE);
            settingsAmdpwd.setVisibility(View.GONE);
        }
        String online = "";
        if (BaseApp.isDebug()) {
            online = "debug：";
        }
        ViewUtil.setText(tv_setting_update, online + new AppUtil().getPackageInfo(context()).versionName);

        calculateCache();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.settings_cache, R.id.settings_update, R.id.settings_amdpwd, R.id.settings_feedback, R.id.settings_about, R.id.user_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.settings_cache:
                clearCache();
                break;
            case R.id.settings_update:
                updateApp();
                break;
            case R.id.settings_amdpwd:
//                startAnimActivity(AlterPwdActivity.class);
                break;
            case R.id.settings_feedback:
//                startAnimActivity(FeedbackActivity.class);
                break;
            case R.id.settings_about:
//                startAnimActivity(AboutActivity.class);
                break;
            case R.id.user_logout:
                logoutDialog();
                break;
        }
    }

    private void updateApp() {

    }

    private void logoutDialog() {

    }

    private void clearCache() {
        GlideUtil.clearImageAllCache(this);
        new Handler().postDelayed(() -> {
            calculateCache();
            showMsg("清理完成");
        }, 1000);
    }

    private void calculateCache() {
        ViewUtil.setText(tv_setting_cache, GlideUtil.getCacheSize(context()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}