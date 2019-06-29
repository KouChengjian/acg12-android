package com.acg12.lib.widget.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.R;


/**
 * Created by DELL on 2016/12/7.
 */
public class IRecycleView extends RecyclerView {

    private Context mContext;
    private View mHeaderView;
    private View mFooterView;
    private RelativeLayout mFooterLayout;
    private ProgressBar mFooterProgress;
    private TextView mFooterMsg;
    private Adapter mAdapter;
    private IWrapAdapter mWrapAdapter;
    private LoadingListener mLoadingListener;
    private boolean mLoadingMoreEnabled = false;
    private boolean isStopLoadingLayout = false;
    private int previousTotal = 0;

    public IRecycleView(Context context) {
        this(context, null);
    }

    public IRecycleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IRecycleView(Context mContext, AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        this.mContext = mContext;
    }

    public void addHeaderView(View view) {
        mHeaderView = view;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setisStopLoadingLayout(boolean isStopLoadingLayout) {
        this.isStopLoadingLayout = isStopLoadingLayout;
    }

    public void loadMoreComplete() {
        if (mFooterView == null) return;
        if (previousTotal < getLayoutManager().getItemCount()) {
//            mFooterLayout.setVisibility(View.INVISIBLE);
        } else {
//            mFooterLayout.setVisibility(View.INVISIBLE);
        }
        previousTotal = getLayoutManager().getItemCount();
    }

    public void resetLayoutFooter() {
        if (mFooterView == null) return;
        if (!isStopLoadingLayout) {
            mFooterView.setVisibility(View.VISIBLE);
            mFooterLayout.setVisibility(View.VISIBLE);
            mFooterProgress.setVisibility(View.VISIBLE);
            mFooterMsg.setText("加载中...");
        }
    }

    public void stopMoreLoading() {
        stopMoreLoading("");
    }

    public void stopMoreLoading(String msg) {
        if (mFooterView == null) return;
        isStopLoadingLayout = true;
        if (msg == null || msg.isEmpty()) {
            mFooterView.setVisibility(View.GONE);
            mFooterLayout.setVisibility(View.GONE);
        } else {
            mFooterView.setVisibility(View.VISIBLE);
            mFooterLayout.setVisibility(View.VISIBLE);
            mFooterProgress.setVisibility(View.GONE);
            mFooterMsg.setText(msg);
        }
    }

    public void setLoadingMoreEnabled(boolean enabled) {
        mLoadingMoreEnabled = enabled;
        if (mLoadingMoreEnabled) {
            mFooterView = LayoutInflater.from(mContext).inflate(R.layout.include_loading_footer, null);
            mFooterLayout = mFooterView.findViewById(R.id.layout_footer);
            mFooterProgress = mFooterView.findViewById(R.id.pull_to_refresh_load_progress);
            mFooterMsg = mFooterView.findViewById(R.id.pull_to_refresh_loadmore_text);
            mFooterLayout.setVisibility(View.VISIBLE);
            removeOnScrollListener(mOnLoadMoreScrollListener);
            addOnScrollListener(mOnLoadMoreScrollListener);
        } else {
            removeOnScrollListener(mOnLoadMoreScrollListener);
            if (mFooterView != null) {
                mFooterView.setVisibility(View.GONE);
                mFooterLayout.setVisibility(View.GONE);
            }
        }
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mWrapAdapter = new IWrapAdapter(mHeaderView, mFooterView, mAdapter);
//        mWrapAdapter.setHasStableIds(hasStableIds);
        super.setAdapter(mWrapAdapter);
    }

    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    private OnLoadMoreScrollListener mOnLoadMoreScrollListener = new OnLoadMoreScrollListener() {
        @Override
        public void onLoadMore(RecyclerView recyclerView) {
            if (mLoadingListener != null) {
                if (!isStopLoadingLayout) {
                    mLoadingListener.onLoadMore();
                }
            }
        }
    };

    // TODO 以后要移动到listener路径下面去
    public interface LoadingListener {
        void onLoadMore();
    }
}
