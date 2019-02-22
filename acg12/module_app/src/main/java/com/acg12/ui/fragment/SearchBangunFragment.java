package com.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.widget.recycle.IRecycleView;

import com.acg12.lib.constant.Constant;
import com.acg12.entity.Video;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.activity.PreviewBangumiActivity;
import com.acg12.ui.base.SkinBaseFragment;
import com.acg12.ui.views.SearchBangunView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchBangunFragment extends SkinBaseFragment<SearchBangunView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener {

    String title = "";
    int page = 1;
    boolean refresh = true;

    public static SearchBangunFragment newInstance(String title) {
        SearchBangunFragment fragment = new SearchBangunFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        title = getArguments().getString("title");
        refresh(title , page);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Bundle bundle = new Bundle();
        bundle.putString("bangumiId" , mView.getBangumiId(position));
        startAnimActivity(PreviewBangumiActivity.class , bundle);
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        refresh(title , page++);
    }

    @Override
    public void onRefresh() {
        refresh = true;
        refresh(title , page);
    }

    public void refresh(String key , int page){
        HttpRequestImpl.getInstance().searchBangumi(currentUser(),key , page + "", new HttpRequestListener<List<Video>>() {
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
//                ShowToastView(msg);
                mView.stopRefreshLoadMore(refresh);
            }
        });
    }

}
