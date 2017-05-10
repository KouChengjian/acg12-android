package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class SettingView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.settings_cache)
    RelativeLayout settingsCache;
    @BindView(R.id.settings_update)
    RelativeLayout settingsUpdate;
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
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.setting));
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar , settingsCache ,settingsUpdate , settingsFeedback , settingsAbout ,userLogout );
    }
}
