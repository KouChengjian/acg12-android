package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.CollectAlbumAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 15:00
 * Description:
 */
public class CollectAlbumView extends ViewImpl {

    @BindView(R.id.commonRecycleview)
    CommonRecycleview mCommonRecycleview;

    CollectAlbumAdapter mCollectAlbumAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect_album;
    }

    @Override
    public void created() {
        super.created();
        staggeredGridLayoutManager = mCommonRecycleview.setStaggeredGridLayoutManager();
        mCollectAlbumAdapter = new CollectAlbumAdapter(getContext());
        mCommonRecycleview.setAdapter(mCollectAlbumAdapter);
        mCommonRecycleview.startRefreshing();

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mCollectAlbumAdapter.setCollectAlbumListener((CollectAlbumAdapter.CollectAlbumListener) mPresenter);
        mCommonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        mCommonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mCommonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener) mPresenter);
    }

    public void bindData(List<Album> result, boolean refresh) {
        if (refresh) {
            mCollectAlbumAdapter.setList(result);
            mCommonRecycleview.notifyChanged();
        } else {
            mCollectAlbumAdapter.addAll(result);
            mCommonRecycleview.notifyChanged(getList().size() - result.size(), getList().size());
        }
    }

    public String getPicId() {
        return mCollectAlbumAdapter.getList().get(mCollectAlbumAdapter.getList().size() - 1).getPinId();
    }

    public List<Album> getList() {
        return mCollectAlbumAdapter.getList();
    }

    public Album getAlbum(int position) {
        return getList().get(position);
    }

    public void stopLoading() {
        mCommonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        mCommonRecycleview.stopRefreshLoadMore(refresh);
    }

    public void recycleException() {
        mCommonRecycleview.recycleException();
    }

    public void updataObject(int position, int isCollect) {
        Album album = getAlbum(position);
        album.setIsCollect(isCollect);
        mCommonRecycleview.notifyChanged(position);
    }

    public void moveToPosition(int n) {
        staggeredGridLayoutManager.scrollToPositionWithOffset(n, 0);
//        manager.setStackFromEnd(true);
    }
}
