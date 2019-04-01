package com.acg12.ui.views;

import android.widget.TextView;

import com.acg12.R;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class AboutView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.version_name)
    TextView version_name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("关于");
        ViewUtil.setText(version_name, AppUtil.getPackageInfo(getContext()).versionName);

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolBarView.getToolbar());
    }
}
