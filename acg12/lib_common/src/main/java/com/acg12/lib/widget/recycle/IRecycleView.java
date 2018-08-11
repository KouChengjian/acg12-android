package com.acg12.lib.widget.recycle;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.acg12.lib.R;

import java.util.ArrayList;

/**
 * Created by DELL on 2016/12/7.
 */
public class IRecycleView extends RecyclerView {

    private Context mContext;

    private View mHeaderViews;
    private View mFootViews ;
    private RelativeLayout layout_footer;

    private boolean isLoadingData = false;
    private boolean isnomore = false;
    private boolean loadingMoreEnabled = false;
    private int previousTotal = 0;
    private Adapter mAdapter;
    private Adapter mWrapAdapter;
    private LoadingListener mLoadingListener;

    //private static final int TYPE_REFRESH_HEADER =  -5;
    private static final int TYPE_HEADER =  -4;
    private static final int TYPE_NORMAL =  0;
    private static final int TYPE_FOOTER =  -3;

    public IRecycleView(Context context) {
        this(context,null);
    }

    public IRecycleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IRecycleView(Context mContext, AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        this.mContext = mContext;
        initView();
    }

    public void initView(){

    }

    public void addHeaderView(View view) {
        mHeaderViews = view ;
    }

    public void addFootView(View view) {
        mFootViews = view ;
    }

    public View getHeaderView(){
        return mHeaderViews;
    }

    public View getFootView(){
        return mFootViews;
    }

    public void loadMoreComplete() {
        isLoadingData = false;
        View footView = mFootViews;
        if(previousTotal <  getLayoutManager().getItemCount()) {
            footView.setVisibility(View.GONE);
        } else {
            footView.setVisibility(View.GONE);
            //isnomore = true;
        }
        previousTotal = getLayoutManager().getItemCount();
    }

    public void noMoreLoading() {
        isLoadingData = false;
        View footView = mFootViews;
        isnomore = true;
        layout_footer.setVisibility(View.GONE);
    }

    public void setLoadingMoreEnabled(boolean enabled){
        loadingMoreEnabled = enabled;
        if(loadingMoreEnabled){
            View footView = LayoutInflater.from(mContext).inflate(R.layout.common_loading_footer,null);
            layout_footer = (RelativeLayout)footView.findViewById(R.id.layout_footer);
            footView.setVisibility(View.GONE);
            addFootView(footView);
        } else {
            if (mFootViews != null) {
                mFootViews.setVisibility(View.GONE);
            }
        }
    }


    public void setAdapter(Adapter adapter) {
        mAdapter  = adapter;
        mWrapAdapter = new WrapAdapter(mHeaderViews, mFootViews, adapter);
        super.setAdapter(mWrapAdapter);
        mAdapter.registerAdapterDataObserver(mDataObserver);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

//            Log.e("onLoadMore","layoutManager.getChildCount()="+layoutManager.getChildCount()+"    "+
//                               "lastVisibleItemPosition="+lastVisibleItemPosition+"    "+
//                    "layoutManager.getItemCount() - 1="+layoutManager.getItemCount()+"    "+
//                    "layoutManager.getItemCount()="+layoutManager.getItemCount()+"    "+
//                    "layoutManager.getChildCount()="+layoutManager.getChildCount());
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
                    && layoutManager.getItemCount() > layoutManager.getChildCount()
                    && !isnomore ) {

                //View footView = mFootViews;
                isLoadingData = true;
                mFootViews.setVisibility(View.VISIBLE);
                Log.e("onLoadMore","onLoadMore");
                mLoadingListener.onLoadMore();
            }else {
                Log.e("onLoadMore","false");
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private final RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    /**
     * WrapAdapter
     * @author DELL
     */
    private class WrapAdapter extends RecyclerView.Adapter<ViewHolder> {

        private RecyclerView.Adapter adapter;
        private ArrayList<View> mHeaderViews = new ArrayList<View>();
        private ArrayList<View> mFootViews = new ArrayList<View>();
        private int headerPosition = 0;

        public WrapAdapter(View headerViews, View footViews, RecyclerView.Adapter adapter) {
            this.adapter = adapter;
            if(headerViews != null){
                this.mHeaderViews.add(headerViews);
            }
            if(footViews != null){
                this.mFootViews.add(footViews);
            }
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                return getHeadersCount() + getFootersCount() + adapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeadersCount()) {
                int adjPosition = position - getHeadersCount();
                int adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public int getItemViewType(int position) {
            if (isHeader(position)) {
                return TYPE_HEADER;
            }
            if(isFooter(position)){
                return TYPE_FOOTER;
            }
            int adjPosition = position - getHeadersCount();;
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemViewType(adjPosition);
                }
            }
            return TYPE_NORMAL;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if(manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeader(position)||  isFooter(position)) ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if(lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams &&
                    (isHeader( holder.getLayoutPosition()) || isFooter( holder.getLayoutPosition())) ) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                return new SimpleViewHolder(mHeaderViews.get(headerPosition++ ));
            } else if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(mFootViews.get(0));
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isHeader(position)) {
                return;
            }
            int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                    return;
                }
            }
        }

        public boolean isHeader(int position) {
            return position >= 0 && position < mHeaderViews.size();
        }

        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - mFootViews.size();
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFootViews.size();
        }

        private class SimpleViewHolder extends RecyclerView.ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    public interface LoadingListener {

        //void onRefresh();

        void onLoadMore();
    }
}
