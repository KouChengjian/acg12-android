package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.SearchAlbumAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/6.
 */
public class SearchAlbumView extends ViewImpl {

    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;
    SearchAlbumAdapter mSearchAlbumAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void created() {
        super.created();
        staggeredGridLayoutManager = commonRecycleview.setStaggeredGridLayoutManager();
        mSearchAlbumAdapter = new SearchAlbumAdapter(getContext());
        commonRecycleview.setAdapter(mSearchAlbumAdapter);
        commonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener) mPresenter);
        commonRecycleview.setRecycleUpdataListener((CommonRecycleview.IRecycleUpdataListener) mPresenter);
        mSearchAlbumAdapter.setSearchAlbumListener((SearchAlbumAdapter.SearchAlbumListener)mPresenter);
    }

    public void bindData(List<Album> result, boolean refresh) {
        if (refresh) {
            mSearchAlbumAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            mSearchAlbumAdapter.addAll(result);
            commonRecycleview.notifyChanged(getList().size() - result.size(), getList().size());
        }
        commonRecycleview.stopRefreshLoadMore(refresh);
    }

    public String getPicId() {
        return mSearchAlbumAdapter.getList().get(mSearchAlbumAdapter.getList().size() - 1).getPinId();
    }

    public List<Album> getList() {
        return mSearchAlbumAdapter.getList();
    }

    public Album getObject(int position) {
        return mSearchAlbumAdapter.getList().get(position);
    }

    public void stopLoading() {
        commonRecycleview.stopLoading();
    }

    public void recycleException() {
        commonRecycleview.recycleException();
    }

    public void updataObject(int position, int isCollect) {
        Album album = getObject(position);
        album.setIsCollect(isCollect);
        commonRecycleview.notifyChanged(position);
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param n 要跳转的位置
     */
    public void MoveToPosition(int n) {
        staggeredGridLayoutManager.scrollToPositionWithOffset(n, 0);
//        manager.setStackFromEnd(true);
    }
}
