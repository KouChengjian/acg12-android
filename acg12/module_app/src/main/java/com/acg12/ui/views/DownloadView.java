package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.entity.DownLoad;

import com.acg12.entity.DownLoad;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.PixelUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.DownloadAdapter;

import com.acg12.R;
import com.acg12.ui.adapter.DownloadAdapter;
import com.acg12.ui.adapter.SkinLoaderAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class DownloadView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.commonRecycleview)
    CommonRecycleview commonRecycleview;

    DownloadAdapter downloadAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("下载");

        commonRecycleview.setLinearLayoutManager();
        commonRecycleview.setLoadingEnabled(false);
        commonRecycleview.setRefreshEnabled(false);
        downloadAdapter = new DownloadAdapter(getContext());
        commonRecycleview.setAdapter(downloadAdapter);
        commonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , toolBarView.getToolbar());
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener) mPresenter);
        commonRecycleview.setRecycleUpdataListener((CommonRecycleview.IRecycleUpdataListener)mPresenter);
    }

    public ToolBarView getToolBarView() {
        return toolBarView;
    }

    public void bindData(List<DownLoad> result , boolean refresh){
        if (refresh) {
            downloadAdapter.setList(result);
        } else {
            downloadAdapter.addAll(result);
        }
        downloadAdapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(){
        downloadAdapter.notifyDataSetChanged();
    }

    public DownLoad getDownLoad(int position){
        return downloadAdapter.getList().get(position);
    }

    public List<DownLoad> getList(){
        return downloadAdapter.getList();
    }

    public void stopLoading(){
        commonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        commonRecycleview.stopRefreshLoadMore(refresh);
    }
}
