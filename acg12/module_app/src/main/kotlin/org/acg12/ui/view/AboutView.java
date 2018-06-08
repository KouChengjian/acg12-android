package org.acg12.ui.view;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.ViewUtil;

import org.acg12.R;

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
