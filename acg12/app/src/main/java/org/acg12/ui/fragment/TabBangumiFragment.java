package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.acg12.bean.Video;
import org.acg12.config.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.listener.ItemClickSupport;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.activity.PreviewBangumiActivity;
import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.ui.views.TabBangumiView;
import org.acg12.widget.IRecycleView;

import java.util.List;

public class TabBangumiFragment extends PresenterFragmentImpl<TabBangumiView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener {

    boolean refresh = true;
    int page = 1;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        refresh(page);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Bundle bundle = new Bundle();
        bundle.putString("bangumiId" , mView.getBangumiId(position));
        startAnimActivity(PreviewBangumiActivity.class , bundle);
    }

    @Override
    public void onRefresh() {
        refresh = true;
        page = 1;
        refresh(page);
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        page ++;
        refresh(page);
    }

    public void refresh(int page){
        HttpRequestImpl.getInstance().bangumiList(page + "", new HttpRequestListener<List<Video>>() {
            @Override
            public void onSuccess(List<Video> result) {
                if (result.size() != 0 && result.get(result.size() - 1) != null) {
                    if (result.size() < Constant.LIMIT_PAGER) {
                        mView.stopLoading();
                    }
                    mView.bindData(result , refresh);
                }
                mView.stopRefreshLoadMore(refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag , msg);
                ShowToastView(msg);
                mView.stopRefreshLoadMore(refresh);
            }
        });
    }
}
