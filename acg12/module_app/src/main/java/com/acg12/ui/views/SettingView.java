package com.acg12.ui.views;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.cache.DaoBaseImpl;
import com.acg12.lib.app.BaseApp;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class SettingView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.settings_cache)
    RelativeLayout settingsCache;
    @BindView(R.id.tv_setting_cache)
    TextView tv_setting_cache;
    @BindView(R.id.settings_update)
    RelativeLayout settingsUpdate;
    @BindView(R.id.tv_setting_update)
    TextView tv_setting_update;
    @BindView(R.id.settings_amdpwd)
    RelativeLayout settingsAmdpwd;
    @BindView(R.id.settings_feedback)
    RelativeLayout settingsFeedback;
    @BindView(R.id.settings_about)
    RelativeLayout settingsAbout;
    @BindView(R.id.user_logout)
    TextView userLogout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("设置");

        if (DaoBaseImpl.getInstance(getContext()).getCurrentUser() == null) {
            userLogout.setVisibility(View.GONE);
            settingsAmdpwd.setVisibility(View.GONE);
        }

        String online = "";
        if (BaseApp.isDebug()) {
            online = "内测：";
        }
        ViewUtil.setText(tv_setting_update, online + new AppUtil().getPackageInfo(getContext()).versionName);

        calculateCache();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolBarView.getToolbar(), settingsCache, settingsUpdate,
                settingsAmdpwd, settingsFeedback, settingsAbout, userLogout);
    }

    public ToolBarView getToolBarView() {
        return toolBarView;
    }

    public void calculateCache() {
        ViewUtil.setText(tv_setting_cache, ImageLoadUtils.getCacheSize(getContext()));
    }
}
