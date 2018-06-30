package com.acg12.lib.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.acg12.lib.R;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.utils.PixelUtil;

/**
 * Created by Administrator on 2017/5/20.
 */
public class CommonRecycleview extends FrameLayout implements TipLayoutView.OnReloadClick{

    private Context mContext;
    private IRecycleView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TipLayoutView mTipLayoutView;
    private RecyclerView.Adapter adapter;
    private IRecycleUpdataListener recycleUpdataListener;
    private boolean refreshHideHeader = true; // 刷新是否隐藏header

    public CommonRecycleview(Context context) {
        super(context);
        mContext = context;
    }

    public CommonRecycleview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CommonRecycleview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_loading_recyclerview, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mSwipeRefreshLayout);
        mRecyclerView = (IRecycleView) view.findViewById(R.id.mRecyclerView);
        mTipLayoutView = (TipLayoutView) view.findViewById(R.id.tipLayoutView);
        mTipLayoutView.setContainer(mSwipeRefreshLayout);
        mTipLayoutView.setOnReloadClick(this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_body);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(mContext, 50), PixelUtil.dp2px(mContext, 24));
    }

    public IRecycleView getIRecycleView() {
        return mRecyclerView;
    }

    public LinearLayoutManager setLinearLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        return layoutManager;
    }

    public StaggeredGridLayoutManager setStaggeredGridLayoutManager() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        return staggeredGridLayoutManager;
    }

    public void setPadding(int padding) {
        setPadding(padding, padding, padding, padding);
    }

    public void setPadding(int left, int top, int right, int button) {
        mRecyclerView.setPadding(left, top, right, button);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void addHeader(View view) {
        addHeader(view, true);
    }

    public void addHeader(View view, boolean isShow) {
        mRecyclerView.addHeaderView(view);
        if (!isShow) { // 初始化是否隐藏
            mRecyclerView.getHeaderView().setVisibility(View.GONE);
        }
    }

    public void addFootView(View view) {
        mRecyclerView.addFootView(view);
    }

    public void setRefrreshHideHeader(boolean refreshHideHeader) {
        this.refreshHideHeader = refreshHideHeader;
    }

    public void setLoadingEnabled(boolean enabled) {
        mRecyclerView.setLoadingMoreEnabled(enabled);
    }

    public void setRefreshEnabled(boolean enabled) {
        mSwipeRefreshLayout.setEnabled(enabled);
    }

    public void startRefreshing() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void stopLoading() {
        mRecyclerView.noMoreLoading();
    }

    public void setLoadingListener(IRecycleView.LoadingListener mPresenter) {
        mRecyclerView.setLoadingListener(mPresenter);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener mPresenter) {
        mSwipeRefreshLayout.setOnRefreshListener(mPresenter);
    }

    public void setRecycleUpdataListener(IRecycleUpdataListener recycleUpdataListener) {
        this.recycleUpdataListener = recycleUpdataListener;
    }

    public void setOnItemClickListener(ItemClickSupport.OnItemClickListener mPresenter) {
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(mPresenter);
    }

    public void setOnItemLongClickListener(ItemClickSupport.OnItemLongClickListener mPresenter) {
        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(mPresenter);
    }

    public void stopRefreshLoadMore(boolean refresh) {
        if (refresh)
            mSwipeRefreshLayout.setRefreshing(false);
        else
            mRecyclerView.loadMoreComplete();
        loadingNull();

    }

    public void notifyChanged() {
        adapter.notifyDataSetChanged();
        loadingNull();
    }

    public void notifyChanged(int positionStart, int itemCount) {
        adapter.notifyItemChanged(positionStart, itemCount);
        loadingNull();
    }

    public void loadingNull() {
        if (adapter.getItemCount() != 0) {
            if (mRecyclerView.getHeaderView() != null) {
                mRecyclerView.getHeaderView().setVisibility(View.VISIBLE);
            }
        } else {
            if (mRecyclerView.getHeaderView() != null) {
                if (refreshHideHeader) {
                    mRecyclerView.getHeaderView().setVisibility(View.GONE);
                } else {
                    mRecyclerView.getHeaderView().setVisibility(View.VISIBLE);
                }
            }
        }
        mTipLayoutView.stopProgress(adapter.getItemCount());
    }

    @Override
    public void onReload() {
        if(recycleUpdataListener != null){
            recycleUpdataListener.onReload();
        }
    }


    public interface IRecycleUpdataListener {
        void onReload();
    }
}
