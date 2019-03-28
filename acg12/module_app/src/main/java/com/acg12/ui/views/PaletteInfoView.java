package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.acg12.entity.Album;
import com.acg12.entity.Palette;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.PaletteInfoAdapter;
import com.acg12.ui.adapter.TabAlbumAdapter;

import com.acg12.R;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/24.
 */
public class PaletteInfoView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;

    PaletteInfoAdapter mPaletteInfoAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_palette_info;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("");

        staggeredGridLayoutManager = commonRecycleview.setStaggeredGridLayoutManager();
        mPaletteInfoAdapter = new PaletteInfoAdapter(getContext());
        commonRecycleview.setAdapter(mPaletteInfoAdapter);
        commonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , toolbar);
        mPaletteInfoAdapter.setPaletteInfoListener((PaletteInfoAdapter.PaletteInfoListener)mPresenter);
        commonRecycleview.setRecycleUpdataListener((CommonRecycleview.IRecycleUpdataListener)mPresenter);
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void setTitle(String title){
        toolbar.setTitle(title);
    }

    public void bindData(List<Album> result , boolean refresh){
        if (refresh) {
            mPaletteInfoAdapter.setList(result);
        } else {
            mPaletteInfoAdapter.addAll(result);
        }
        commonRecycleview.notifyChanged(getList().size() - result.size() , getList().size());
        commonRecycleview.stopRefreshLoadMore(refresh);
    }

    public String getPicId(){
        return getList().get(getList().size() - 1).getPinId();
    }

    public Album getObject(int position) {
        return mPaletteInfoAdapter.getList().get(position);
    }

    public List<Album> getList() {
        return mPaletteInfoAdapter.getList();
    }

    public void stopLoading(){
        commonRecycleview.stopLoading();
    }

    public void recycleException( ) {
        commonRecycleview.recycleException();
    }

    public void updataObject(int position, int isCollect) {
        Album album = getObject(position);
        album.setIsCollect(isCollect);
        commonRecycleview.notifyChanged(position);
    }

    /**
     * RecyclerView 移动到当前位置，
     * @param n  要跳转的位置
     */
    public void moveToPosition(int n) {
        staggeredGridLayoutManager.scrollToPositionWithOffset(n, 0);
    }
}
