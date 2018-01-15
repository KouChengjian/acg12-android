package org.acg12.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.common.dao.DaoBaseImpl;
import com.acg12.common.entity.DownLoad;
import com.acg12.kk.listener.ItemClickSupport;
import com.acg12.kk.widget.IRecycleView;

import org.acg12.conf.Constant;
import com.acg12.common.ui.base.BaseActivity;
import org.acg12.ui.views.DownloadView;
import org.acg12.widget.AlertDialogView;

import java.util.List;

public class DownloadActivity extends BaseActivity<DownloadView> implements View.OnClickListener ,ItemClickSupport.OnItemClickListener ,
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
//        alertDialog.setContent2("返回", new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                alertDialog.cancel();
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
