package com.acg12.ui.views;

import com.acg12.R;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.DeletableEditText;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/25.
 */
public class NickView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.edt_nick)
    DeletableEditText edt_nick;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nick;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("修改昵称");
        toolBarView.setRightText("保存");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolBarView.getToolbar(), toolBarView.getTitleRight());
    }

    public String getNick() {
        return edt_nick.getText().toString();
    }
}
