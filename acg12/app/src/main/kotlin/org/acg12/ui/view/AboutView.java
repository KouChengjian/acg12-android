package org.acg12.ui.view;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.AppUtil;
import org.acg12.utlis.ViewUtil;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class AboutView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.version_name)
    TextView version_name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.about));
        ViewUtil.setText(version_name, AppUtil.getPackageInfo(getContext()).versionName);

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar);
    }
}
