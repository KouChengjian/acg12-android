package com.acg12.ui.activity;


import android.os.Bundle;

import com.acg12.R;
import com.acg12.lib.utils.ClickUtil;
import com.acg12.lib.widget.TipLayoutView;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.RecordContract;
import com.acg12.ui.presenter.RecordPresenter;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/07/04
 * Description: 自动生成
 */
public class RecordActivity extends BaseMvpActivity<RecordPresenter> implements RecordContract.View {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.tipLayoutView)
    TipLayoutView tipLayoutView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        toolBarView.setNavigationOrBreak("历史记录");
        tipLayoutView.showCustomError(R.mipmap.bg_loading_null,"暂未开启","");
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        ClickUtil.click(this ,toolBarView.getToolbar());
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