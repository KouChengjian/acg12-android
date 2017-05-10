package org.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.bean.Album;
import org.acg12.listener.ItemClickSupport;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.TabAlbumAdapter;
import org.acg12.utlis.PixelUtil;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.IRecycleView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/23.
 */
public class TabAlbumView extends ViewImpl {

    @BindView(R.id.mRecyclerView)
    IRecycleView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_load_null)
    ViewStub layoutLoadNull;
    ImageView loadNullImageview;
    TextView loadNullTextview;

    TabAlbumAdapter tabAlbumAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_album;
    }

    @Override
    public void created() {
        super.created();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        tabAlbumAdapter = new TabAlbumAdapter(getContext());
        mRecyclerView.setAdapter(tabAlbumAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mRecyclerView.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void bindData(List<Album> result ,boolean refresh){
        if (refresh) {
            tabAlbumAdapter.setList(result);
        } else {
            tabAlbumAdapter.addAll(result);
        }
        tabAlbumAdapter.notifyDataSetChanged();
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
        mRecyclerView.noMoreLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        if (refresh)
            mSwipeRefreshLayout.setRefreshing(false);
        else
            mRecyclerView.loadMoreComplete();
        loadNull();
    }

    private void loadNull() {
        List<Album> mlist = tabAlbumAdapter.getList();
        if (mlist != null && !mlist.isEmpty()) {
            if (loadNullImageview != null && loadNullTextview != null) {
                ViewUtil.setText(loadNullTextview, "");
                if (loadNullImageview.getVisibility() == View.VISIBLE) {
                    loadNullImageview.setVisibility(View.GONE);
                }
            }
        } else {
            if (loadNullImageview == null && loadNullTextview == null) {
                View view = layoutLoadNull.inflate();
                loadNullImageview = (ImageView) view.findViewById(R.id.iv_load_null);
                loadNullTextview = (TextView) view.findViewById(R.id.tv_load_null);
            }
            //loadNullImageview.setImageResource(R.mipmap.ic_error);
            ViewUtil.setText(loadNullTextview, "暂时没有消息");
            if (loadNullImageview.getVisibility() == View.GONE) {
                loadNullImageview.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager   设置RecyclerView对应的manager
     * @param mRecyclerView  当前的RecyclerView
     * @param n  要跳转的位置
     */
    public static void MoveToPosition(StaggeredGridLayoutManager manager, RecyclerView mRecyclerView, int n) {

//        firstVisibleItem   = manager.findFirstVisibleItemPosition();
//        lastVisibleItem = manager.findLastVisibleItemPosition();
//        if (n <= firstItem) {
//            mRecyclerView.scrollToPosition(n);
//        } else if (n <= lastItem) {
//            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
//            mRecyclerView.scrollBy(0, top);
//        } else {
//            mRecyclerView.scrollToPosition(n);
//        }

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
