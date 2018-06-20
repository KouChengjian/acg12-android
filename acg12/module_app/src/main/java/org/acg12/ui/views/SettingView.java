package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.acg12.dao.DaoBaseImpl;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import org.acg12.R;
import org.acg12.conf.Constant;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class SettingView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.setting));

        if(DaoBaseImpl.getInstance(getContext()).getCurrentUser() == null ){
            userLogout.setVisibility(View.GONE);
            settingsAmdpwd.setVisibility(View.GONE);
        }

        String online = "";
        if(Constant.debug){
            online = "内测：";
        }
        ViewUtil.setText(tv_setting_update, online + new AppUtil().getPackageInfo(getContext()).versionName);

        calculateCache();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar , settingsCache ,settingsUpdate ,
                settingsAmdpwd, settingsFeedback , settingsAbout ,userLogout );
    }

    public void calculateCache(){
        ViewUtil.setText(tv_setting_cache , ImageLoadUtils.getCacheSize(getContext()));
    }
}
