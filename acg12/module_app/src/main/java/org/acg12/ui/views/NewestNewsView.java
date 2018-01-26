package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;

import com.acg12.lib.ui.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.widget.CommonRecycleview;

import org.acg12.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/26.
 */

public class NewestNewsView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.commonRecycleview)
    CommonRecycleview mCommonRecycleview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_newest_news;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("每日快报");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , toolbar);
    }
}
