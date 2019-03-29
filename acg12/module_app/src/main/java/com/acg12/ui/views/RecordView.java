package com.acg12.ui.views;

import com.acg12.R;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class RecordView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ecord;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("历史记录");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolBarView.getToolbar());
    }

    public ToolBarView getToolBarView() {
        return toolBarView;
    }
}
