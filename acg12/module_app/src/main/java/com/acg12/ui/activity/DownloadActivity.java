package com.acg12.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.R;
import com.acg12.cache.DaoBaseImpl;
import com.acg12.entity.DownLoad;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.DownloadView;
import com.acg12.widget.dialog.AlertDialogView;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends SkinBaseActivity<DownloadView> implements ItemClickSupport.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener
        , IRecycleView.LoadingListener, ItemClickSupport.OnItemLongClickListener, CommonRecycleview.IRecycleUpdataListener {

    boolean refresh = true;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        List<DynamicAttr> mDynamicAttr = new ArrayList<>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TOOLBARVIEW, R.color.theme_primary));
        dynamicAddView(mView.getToolBarView(), mDynamicAttr);

        refresh();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    }

    @Override
    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
        delDialog(position);
        return true;
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRecycleReload() {
        onRefresh();
    }

    private void refresh() {
        List<DownLoad> downloadList = DaoBaseImpl.getInstance(mContext).queryDownloadList();
        mView.bindData(downloadList, refresh);
        mView.stopRefreshLoadMore(refresh);
    }

    public void delDialog(final int position) {
        final AlertDialogView alertDialog = new AlertDialogView(this, "");
        alertDialog.setContent1("删除", new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DownLoad download = mView.getDownLoad(position);
                mView.getList().remove(download);
                DaoBaseImpl.getInstance(mContext).delDownLoad(download);
                mView.notifyDataSetChanged();
                alertDialog.cancel();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
