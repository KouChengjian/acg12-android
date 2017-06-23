package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.acg12.bean.Video;
import org.acg12.conf.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.listener.ItemClickSupport;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.activity.PreviewBangumiActivity;
import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.ui.views.SearchBangunView;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.IRecycleView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchBangunFragment extends PresenterFragmentImpl<SearchBangunView> implements IRecycleView.LoadingListener ,
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
        if(!ViewUtil.isNetConnected(mContext)) return;
        HttpRequestImpl.getInstance().searchBangumi(key , page + "", new HttpRequestListener<List<Video>>() {
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
