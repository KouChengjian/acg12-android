package org.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.widget.CommonRecycleview;
import com.acg12.lib.widget.IRecycleView;
import com.acg12.lib.widget.ToolBarView;

import org.acg12.R;
import org.acg12.entity.Album;
import org.acg12.ui.adapter.TabAlbumAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/26.
 */
public class NewestIllustrationView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView mToolBarView;
    @BindView(R.id.commonRecycleview)
    CommonRecycleview mCommonRecycleview;
    TabAlbumAdapter tabAlbumAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_newest_illustration;
    }

    @Override
    public void created() {
        super.created();
        mToolBarView.setNavigationOrBreak("每日精选");

        staggeredGridLayoutManager = mCommonRecycleview.setStaggeredGridLayoutManager();
        tabAlbumAdapter = new TabAlbumAdapter(getContext());
        mCommonRecycleview.setAdapter(tabAlbumAdapter);
        mCommonRecycleview.startRefreshing();

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , mToolBarView.getToolbar());
        mCommonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        mCommonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mCommonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void bindData(List<Album> result , boolean refresh){
        if (refresh) {
            tabAlbumAdapter.setList(result);
            mCommonRecycleview.notifyChanged();
        } else {
            tabAlbumAdapter.addAll(result);
            mCommonRecycleview.notifyChanged(tabAlbumAdapter.getList().size() - result.size() , tabAlbumAdapter.getList().size());
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
        mCommonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        mCommonRecycleview.stopRefreshLoadMore(refresh);
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
