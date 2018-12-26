package com.acg12.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.entity.DownLoad;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.widget.recycle.IRecycleView;

import com.acg12.constant.Constant;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.DownloadView;
import com.acg12.widget.dialog.AlertDialogView;

import java.util.List;

public class DownloadActivity extends SkinBaseActivity<DownloadView> implements View.OnClickListener ,ItemClickSupport.OnItemClickListener ,
        SwipeRefreshLayout.OnRefreshListener ,IRecycleView.LoadingListener ,ItemClickSupport.OnItemLongClickListener{

    boolean refresh = true;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        refresh();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            aminFinish();
        }
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

    private void refresh(){
        List<DownLoad> downloadList = DaoBaseImpl.getInstance(mContext).queryDownloadList();
        mView.bindData(downloadList , refresh);
        mView.stopRefreshLoadMore(refresh);
    }

    public void delDialog(final int position){
        final AlertDialogView alertDialog = new AlertDialogView(this,"");
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
