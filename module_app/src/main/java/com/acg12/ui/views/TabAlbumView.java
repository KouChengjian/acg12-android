package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.acg12.entity.Album;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.TabAlbumAdapter;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.ui.adapter.TabAlbumAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/23.
 */
public class TabAlbumView extends ViewImpl {

    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;
    TabAlbumAdapter tabAlbumAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_album;
    }

    @Override
    public void created() {
        super.created();
        staggeredGridLayoutManager = commonRecycleview.setStaggeredGridLayoutManager();
        tabAlbumAdapter = new TabAlbumAdapter(getContext());
        commonRecycleview.setAdapter(tabAlbumAdapter);
        commonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void bindData(List<Album> result , boolean refresh){
        if (refresh) {
            tabAlbumAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            tabAlbumAdapter.addAll(result);
            commonRecycleview.notifyChanged(tabAlbumAdapter.getList().size() - result.size() , tabAlbumAdapter.getList().size());
        }
    }

    public String getPicId(){
        return tabAlbumAdapter.getList().get(tabAlbumAdapter.getList().size() - 1).getPinId();
    }

    public List<Album> getAlbumList() {
        return tabAlbumAdapter.getList();
    }

    public Album getAlbum(int position) {
        return getAlbumList().get(position);
    }

    public void stopLoading(){
        commonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        commonRecycleview.stopRefreshLoadMore(refresh);
    }

    /**
     * RecyclerView 移动到当前位置，
     * @param n  要跳转的位置
     */
    public void MoveToPosition(int n) {
        staggeredGridLayoutManager.scrollToPositionWithOffset(n, 0);
//        manager.setStackFromEnd(true);
    }
}
