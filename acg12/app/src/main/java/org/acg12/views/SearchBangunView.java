package org.acg12.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.bean.Video;
import org.acg12.listener.ItemClickSupport;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.TabBangumiAdapter;
import org.acg12.utlis.PixelUtil;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.IRecycleView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/6.
 */
public class SearchBangunView extends ViewImpl{

    @BindView(R.id.mRecyclerView)
    IRecycleView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_load_null)
    ViewStub layoutLoadNull;
    ImageView loadNullImageview;
    TextView loadNullTextview;

    TabBangumiAdapter tabBangumiAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void created() {
        super.created();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setLoadingMoreEnabled(true);
        tabBangumiAdapter = new TabBangumiAdapter(getContext());
        mRecyclerView.setAdapter(tabBangumiAdapter);

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

    public void bindData(List<Video> result , boolean refresh){
        if (refresh) {
            tabBangumiAdapter.setList(result);
        } else {
            tabBangumiAdapter.addAll(result);
        }
        tabBangumiAdapter.notifyDataSetChanged();
    }

    public String getBangumiId(int position){
        return tabBangumiAdapter.getList().get(position).getBmId();
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
        List<Video> mlist = tabBangumiAdapter.getList();
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
}
