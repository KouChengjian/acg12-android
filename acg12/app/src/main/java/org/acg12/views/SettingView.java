package org.acg12.views;

import android.support.v7.app.AppCompatActivity;
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
        ((AppCompatActivity) getContext()).setTitle(getContext().getString(R.string.setting));
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getContext()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , settingsCache ,settingsUpdate , settingsFeedback , settingsAbout ,userLogout );
    }
}
