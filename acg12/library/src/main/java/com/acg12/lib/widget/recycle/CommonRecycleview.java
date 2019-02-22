package com.acg12.lib.widget.recycle;

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
import com.acg12.lib.widget.TipLayoutView;
import com.acg12.lib.widget.recycle.IRecycleView;

/**
 * Created by Administrator on 2017/5/20.
 */
public class CommonRecycleview extends FrameLayout implements TipLayoutView.OnReloadClick {

    private Context mContext;
    private IRecycleView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TipLayoutView mTipLayoutView;
    private IRecycleUpdataListener recycleUpdataListener;
    private RecyclerView.Adapter adapter;

    private LayoutStatus mLayoutStatus = LayoutStatus.LAYOUT_STATUS_EMPTY;          // 默认的布局状态
    private int customImage = R.mipmap.bg_loading_null;
    private String customMessage = "亲，出现异常咯";
    private String customBtn = "刷新看看";

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
        mSwipeRefreshLayout =  view.findViewById(R.id.mSwipeRefreshLayout);
        mRecyclerView =  view.findViewById(R.id.mRecyclerView);
        mTipLayoutView =  view.findViewById(R.id.tip_layoutView);
        mTipLayoutView.setOnReloadClick(this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_body);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(mContext, 50), PixelUtil.dp2px(mContext, 24));
    }

    public IRecycleView getIRecycleView() {
        return mRecyclerView;
    }

    public TipLayoutView getTipLayoutView() {
        return mTipLayoutView;
    }

    public LinearLayoutManager setLinearLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        return layoutManager;
    }

    public StaggeredGridLayoutManager setStaggeredGridLayoutManager(int sum) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(sum, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        return staggeredGridLayoutManager;
    }

    public StaggeredGridLayoutManager setStaggeredGridLayoutManager() {
        return setStaggeredGridLayoutManager(2);
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

    public void setDefaultLayoutStatus(LayoutStatus layoutStatus) {
        mLayoutStatus = layoutStatus;
    }

    public void setLoadingEnabled(boolean enabled) {
        mRecyclerView.setLoadingMoreEnabled(enabled);
    }

    public void setRefreshEnabled(boolean enabled) {
        mSwipeRefreshLayout.setEnabled(enabled);
    }

    public void startRefreshing() {
        mSwipeRefreshLayout.setRefreshing(true);
        mTipLayoutView.resetStatus();
    }

    public void startLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
        mTipLayoutView.showLoading();
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

    public void setOnItemClickListener(ItemClickSupport.OnItemClickListener mPresenter) {
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(mPresenter);
    }

    public void setOnItemLongClickListener(ItemClickSupport.OnItemLongClickListener mPresenter) {
        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(mPresenter);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener){
        mRecyclerView.addOnScrollListener(listener);
    }

    public void setRecycleUpdataListener(IRecycleUpdataListener recycleUpdataListener) {
        this.recycleUpdataListener = recycleUpdataListener;
    }

    public void stopRefreshLoadMore(boolean refresh) {
        if (refresh) {
//            mRecyclerView.resetLayoutFooter();
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mRecyclerView.loadMoreComplete();
        }
        resetLoadingView(mLayoutStatus);
    }

    public void recycleException() {
        if (adapter.getItemCount() != 0) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.loadMoreComplete();
        mTipLayoutView.showNetError();
    }

    public void notifyChanged() {
        adapter.notifyDataSetChanged();
        resetLoadingView(mLayoutStatus);
    }

    public void notifyChanged(int position) {
        adapter.notifyItemChanged(position);
        resetLoadingView(mLayoutStatus);
    }

    public void notifyChanged(int positionStart, int itemCount) {
        adapter.notifyItemChanged(positionStart, itemCount);
        resetLoadingView(mLayoutStatus);
    }

    public void resetLoadingView(LayoutStatus layoutStatus) {
        if (adapter.getItemCount() != 0) {
            mTipLayoutView.showContent();
            if (mRecyclerView.getHeaderView() != null) {
                mRecyclerView.getHeaderView().setVisibility(View.VISIBLE);
            }
            return;
        }
        if (LayoutStatus.LAYOUT_STATUS_CONTENT == layoutStatus) {
            mTipLayoutView.showContent();
        } else if (LayoutStatus.LAYOUT_STATUS_CONTENT_HEADER_SHOW == layoutStatus) {
            mTipLayoutView.showContent();
            mRecyclerView.getHeaderView().setVisibility(View.VISIBLE);
        } else if (LayoutStatus.LAYOUT_STATUS_CONTENT_HEADER_HIDE == layoutStatus) {
            mTipLayoutView.showContent();
            mRecyclerView.getHeaderView().setVisibility(View.GONE);
        } else if (LayoutStatus.LAYOUT_STATUS_EMPTY == layoutStatus) {
            mTipLayoutView.showEmpty();
        } else if (LayoutStatus.LAYOUT_STATUS_EMPTY_REFRESH == layoutStatus) {
            mTipLayoutView.showEmptyOrRefresh();
        } else if (LayoutStatus.LAYOUT_STATUS_NET_ERROR == layoutStatus) {
            mTipLayoutView.showNetError();
        } else if (LayoutStatus.LAYOUT_STATUS_CUSTOM == layoutStatus) {
            mTipLayoutView.showCustomError(customImage, customMessage, customBtn);
        }
    }

    @Override
    public void onReload() {
        if (recycleUpdataListener != null) {
            mTipLayoutView.showLoading();
            recycleUpdataListener.onRecycleReload();
        }
    }

    public interface IRecycleUpdataListener {
        void onRecycleReload();
    }
}
