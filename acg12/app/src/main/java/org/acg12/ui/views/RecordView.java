package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class RecordView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ecord;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.record));
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar);
    }
}
