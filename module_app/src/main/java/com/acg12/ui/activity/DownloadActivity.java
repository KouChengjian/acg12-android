package com.acg12.ui.activity;


import android.os.Bundle;

import com.acg12.R;
import com.acg12.lib.utils.ClickUtil;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.ui.adapter.DownloadAdapter;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.DownloadContract;
import com.acg12.ui.presenter.DownloadPresenter;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/24
 * Description: 自动生成
 */
public class DownloadActivity extends BaseMvpActivity<DownloadPresenter> implements DownloadContract.View {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.commonRecycleview)
    CommonRecycleview commonRecycleview;

    DownloadAdapter downloadAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        toolBarView.setNavigationOrBreak("下载");

        downloadAdapter = new DownloadAdapter(context());
        commonRecycleview.setLinearLayoutManager();
        commonRecycleview.setLoadingEnabled(false);
        commonRecycleview.setRefreshEnabled(false);
        commonRecycleview.setAdapter(downloadAdapter);
        commonRecycleview.startRefreshing();

        commonRecycleview.notifyChanged();
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        ClickUtil.click(this, toolBarView.getToolbar());
        ClickUtil.recycleClick(this, commonRecycleview);
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