package com.acg12.kk.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.kk.R;
import com.acg12.kk.listener.IRecycleUpdataListener;
import com.acg12.kk.listener.ItemClickSupport;
import com.acg12.kk.utils.PixelUtil;
import com.acg12.kk.utils.ViewUtil;

/**
 * Created by Administrator on 2017/5/20.
 */
public class CommonRecycleview extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private IRecycleView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewStub layoutLoadNull;
    private ImageView loadNullImageview;
    private TextView loadNullTextview;
    private TextView loadNullUpdate;

    private RecyclerView.Adapter adapter;
    private IRecycleUpdataListener recycleUpdataListener;
    private boolean refreshHideHeader = true; // 刷新是否隐藏header
    private int errPic = R.mipmap.kk_bg_loading_null;
    private String errMsg = "啥也没有了，去其它地方看看吧";

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.kk_common_loading_recyclerview, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mSwipeRefreshLayout);
        mRecyclerView = (IRecycleView) view.findViewById(R.id.mRecyclerView);
        layoutLoadNull = (ViewStub) view.findViewById(R.id.layout_load_null);


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

    public void setErrMsg(String msg) {
        this.errMsg = msg;
    }

    public void setErrPic(int errpic) {
        this.errPic = errpic;
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
            if (loadNullImageview != null && loadNullTextview != null) {
                ViewUtil.setText(loadNullTextview, "");
                if (loadNullImageview.getVisibility() == View.VISIBLE) {
                    loadNullImageview.setVisibility(View.GONE);
                }
            }
            if (mRecyclerView.getHeaderView() != null) {
                mRecyclerView.getHeaderView().setVisibility(View.VISIBLE);
            }
        } else {
            if (loadNullImageview == null && loadNullTextview == null) {
                View view = layoutLoadNull.inflate();
                loadNullImageview = (ImageView) view.findViewById(R.id.iv_load_null);
                loadNullTextview = (TextView) view.findViewById(R.id.tv_load_null);
                loadNullUpdate = (TextView) view.findViewById(R.id.tv_load_update);
                loadNullUpdate.setOnClickListener(this);
            }
            if (mRecyclerView.getHeaderView() != null) {
                if (refreshHideHeader) {
                    mRecyclerView.getHeaderView().setVisibility(View.GONE);
                } else {
                    mRecyclerView.getHeaderView().setVisibility(View.VISIBLE);
                }
            }
            loadNullImageview.setImageResource(errPic);
            ViewUtil.setText(loadNullTextview, errMsg);
            if (loadNullImageview.getVisibility() == View.GONE) {
                loadNullImageview.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_load_update){
            if(recycleUpdataListener != null){
                recycleUpdataListener.onReload();
            }
        }
    }
}
