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
import com.acg12.lib.utils.PixelUtil;
import com.acg12.lib.widget.TipLayoutView;


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
    private View.OnLayoutChangeListener mLayoutChangeListener;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.include_loading_recyclerview, this);
        mSwipeRefreshLayout =  view.findViewById(R.id.mSwipeRefreshLayout);
        mRecyclerView =  view.findViewById(R.id.mRecyclerView);
        mTipLayoutView =  view.findViewById(R.id.tip_layoutView);
        mTipLayoutView.setOnReloadClick(this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50, mContext), PixelUtil.dp2px(24, mContext));
    }

    public IRecycleView getIRecycleView() {
        return mRecyclerView;
    }

    public void setDefaultLayoutStatus(LayoutStatus layoutStatus) {
        mLayoutStatus = layoutStatus;
    }

    public void setCustomLayoutStatus(int id, String customMessgae, String customBtn) {
        this.customImage = id;
        this.customMessage = customMessgae;
        this.customBtn = customBtn;
    }

    public LinearLayoutManager setLinearLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        return layoutManager;
    }

    public StaggeredGridLayoutManager setStaggeredGridLayoutManager(int sum) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(sum, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
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

    @Deprecated
    public void setRefrreshHideHeader(boolean refreshHideHeader) {
        if (mRecyclerView.getHeaderView() != null) {
            if (refreshHideHeader) {
                mLayoutStatus = LayoutStatus.LAYOUT_STATUS_CONTENT_HEADER_HINT;
            } else {
                mLayoutStatus = LayoutStatus.LAYOUT_STATUS_CONTENT_HEADER_SHOW;
            }
        } else {
            mLayoutStatus = LayoutStatus.LAYOUT_STATUS_EMPTY;
        }
    }

    @Deprecated
    public void setHasShowRefreshBtn(boolean hasShowRefreshBtn) {
        if (hasShowRefreshBtn) {
            mLayoutStatus = LayoutStatus.LAYOUT_STATUS_EMPTY_REFRESH;
        } else {
            mLayoutStatus = LayoutStatus.LAYOUT_STATUS_EMPTY;
        }
    }

    @Deprecated
    public void setHasShowContent(boolean hasShowContent) {
        if (hasShowContent) { // 是否显示内容
            mLayoutStatus = LayoutStatus.LAYOUT_STATUS_CONTENT;
        } else {
            mLayoutStatus = LayoutStatus.LAYOUT_STATUS_EMPTY;
        }
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
        if(mRecyclerView.getFooterView()!=null){
            mRecyclerView.getFooterView().setVisibility(View.INVISIBLE);
        }
    }

    public void startLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
        mTipLayoutView.showLoading();
    }

    public void stopLoading() {
        mRecyclerView.stopMoreLoading();
    }

    public void setLoadingListener(IRecycleView.LoadingListener mPresenter) {
        mRecyclerView.setLoadingListener(mPresenter);
    }

    public void setOnRefreshListener(final SwipeRefreshLayout.OnRefreshListener mPresenter) {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setisStopLoadingLayout(false);
                mPresenter.onRefresh();
            }
        });
    }

    public void setOnItemClickListener(final ItemClickSupport.OnItemClickListener mPresenter) {
//        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(mPresenter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (position < adapter.getItemCount() + (mRecyclerView.getHeaderView() != null ? 1 : 0)) {
                    mPresenter.onItemClicked(recyclerView, position, v);
                }
            }
        });
    }

    public void setOnItemLongClickListener(final ItemClickSupport.OnItemLongClickListener mPresenter) {
//        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(mPresenter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                if (position < adapter.getItemCount() + (mRecyclerView.getHeaderView() != null ? 1 : 0)) {
                    mPresenter.onItemLongClicked(recyclerView, position, v);
                }
                return true;
            }
        });
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mRecyclerView.addOnScrollListener(listener);
    }

    public boolean canScrollVerticallyTop() {
        return getIRecycleView().canScrollVertically(-1);
    }

    public void setRecycleUpdataListener(IRecycleUpdataListener recycleUpdataListener) {
        this.recycleUpdataListener = recycleUpdataListener;
    }

    public void setScrollToEndByLayoutChangeEnable(boolean enable) {
        if (enable) {
            mLayoutChangeListener = new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (bottom != oldBottom) {
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                int x = adapter.getItemCount() - 1 + (mRecyclerView.getHeaderView() == null ? 0 : 1);
                                mRecyclerView.scrollToPosition(x);
                            }
                        });
                    }
                }
            };
            mRecyclerView.addOnLayoutChangeListener(mLayoutChangeListener);
        } else {
            mRecyclerView.removeOnLayoutChangeListener(mLayoutChangeListener);
            mLayoutChangeListener = null;
        }
    }

    public void stopRefreshLoadMore(boolean refresh) {
        if (refresh) {
            mRecyclerView.resetLayoutFooter();
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
        if (itemCount == 0) {
            return;
        }
        if (positionStart == itemCount) {
            return;
        }
        adapter.notifyItemChanged(positionStart, itemCount);
        resetLoadingView(mLayoutStatus);
    }

    public void notifyDataOrViewLayout(boolean refresh) {
        if (refresh) {
            if (mRecyclerView.getChildCount() - ((mRecyclerView.getFooterView() != null ? 1 : 0) + (mRecyclerView.getHeaderView() != null ? 1 : 0)) > 0) {
                mRecyclerView.resetLayoutFooter();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mRecyclerView.loadMoreComplete();
        }
        resetLoadingView(mLayoutStatus);
        adapter.notifyDataSetChanged();
    }

    public void notifyDataOrViewLayout(boolean refresh, int positionStart, int itemCount) {
        if (itemCount == 0) {
            return;
        }
        if (positionStart == itemCount) {
            return;
        }
        if (refresh) {
            if (mRecyclerView.getChildCount() - ((mRecyclerView.getFooterView() != null ? 1 : 0) + (mRecyclerView.getHeaderView() != null ? 1 : 0)) > 0) {
                mRecyclerView.resetLayoutFooter();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mRecyclerView.loadMoreComplete();
        }
        resetLoadingView(mLayoutStatus);
        adapter.notifyItemChanged(positionStart + (mRecyclerView.getHeaderView() == null ? 0 : 1)
                , itemCount + (mRecyclerView.getHeaderView() == null ? 0 : 1) + (mRecyclerView.getFooterView() == null ? 0 : 1));
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
        } else if (LayoutStatus.LAYOUT_STATUS_CONTENT_HEADER_HINT == layoutStatus) {
            mTipLayoutView.showContent();
            mRecyclerView.getHeaderView().setVisibility(View.GONE);
        } else if (LayoutStatus.LAYOUT_STATUS_EMPTY == layoutStatus) {
            mTipLayoutView.showEmpty();
        } else if (LayoutStatus.LAYOUT_STATUS_EMPTY_REFRESH == layoutStatus) {
            mTipLayoutView.showEmptyOrRefresh();
        } else if (LayoutStatus.LAYOUT_STATUS_NRT == layoutStatus) {
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
