package com.acg12.ui.activity.setting;


import android.os.Bundle;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.ClickUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.AboutContract;
import com.acg12.ui.presenter.AboutPresenter;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/07/04
 * Description: 自动生成
 */
public class AboutActivity extends BaseMvpActivity<AboutPresenter> implements AboutContract.View {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.version_name)
    TextView version_name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        toolBarView.setNavigationOrBreak("关于");
        ViewUtil.setText(version_name, AppUtil.getPackageInfo(context()).versionName);
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        ClickUtil.click(this, toolBarView.getToolbar());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}