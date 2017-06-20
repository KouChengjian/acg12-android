package org.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import org.acg12.R;
import org.acg12.bean.Album;
import org.acg12.bean.Palette;
import org.acg12.listener.ItemClickSupport;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.TabAlbumAdapter;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.widget.CommonRecycleview;
import org.acg12.widget.IRecycleView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/24.
 */
public class PreviewPaletteView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;

    TabAlbumAdapter tabAlbumAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_palette;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("");

        staggeredGridLayoutManager = commonRecycleview.setStaggeredGridLayoutManager();
        tabAlbumAdapter = new TabAlbumAdapter(getContext());
        commonRecycleview.setAdapter(tabAlbumAdapter);
        commonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , toolbar);
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void bindData(Palette palette){
        toolbar.setTitle(palette.getName());
    }

    public void bindData(List<Album> result , boolean refresh){
        if (refresh) {
            tabAlbumAdapter.setList(result);
        } else {
            tabAlbumAdapter.addAll(result);
        }
        commonRecycleview.notifyChanged(tabAlbumAdapter.getList().size() - result.size() , tabAlbumAdapter.getList().size());
    }

    public String getPicId(){
        return tabAlbumAdapter.getList().get(tabAlbumAdapter.getList().size() - 1).getPinId();
    }

    public List<Album> getAlbumList() {
        return tabAlbumAdapter.getList();
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
    }
}
