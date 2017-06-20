package org.acg12.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
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

import org.acg12.R;
import org.acg12.listener.ItemClickSupport;
import org.acg12.utlis.LogUtil;
import org.acg12.utlis.PixelUtil;
import org.acg12.utlis.ViewUtil;


/**
 * Created by Administrator on 2017/5/20.
 */
public class CommonRecycleview extends FrameLayout{

    Context mContext;
    IRecycleView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView loadingProgress;
    ViewStub layoutLoadNull;
    ImageView loadNullImageview;
    TextView loadNullTextview;


    AnimationDrawable animationDrawable;
    RecyclerView.Adapter adapter;

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

    public void init(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_loading_recyclerview, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mSwipeRefreshLayout);
        mRecyclerView = (IRecycleView) view.findViewById(R.id.mRecyclerView);
        layoutLoadNull = (ViewStub) view.findViewById(R.id.layout_load_null);


        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
    }

    public IRecycleView getIRecycleView(){
        return mRecyclerView;
    }

    public LinearLayoutManager setLinearLayoutManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        return layoutManager;
    }

    public StaggeredGridLayoutManager setStaggeredGridLayoutManager(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        return staggeredGridLayoutManager;
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void startRefreshing(){
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void stopLoading(){
        mRecyclerView.noMoreLoading();
    }

    public void setLoadingListener(IRecycleView.LoadingListener mPresenter){
        mRecyclerView.setLoadingListener(mPresenter);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener mPresenter){
        mSwipeRefreshLayout.setOnRefreshListener(mPresenter);
    }

    public void setOnItemClickListener(ItemClickSupport.OnItemClickListener mPresenter){
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(mPresenter);
    }

    public void stopRefreshLoadMore(boolean refresh) {
        if (refresh)
            mSwipeRefreshLayout.setRefreshing(false);
        else
            mRecyclerView.loadMoreComplete();
        loadingNull();
    }

    public  void notifyChanged(){
        adapter.notifyDataSetChanged();
        loadingNull();
    }

    public  void notifyChanged(int positionStart, int itemCount){
        adapter.notifyItemChanged(positionStart , itemCount);
        loadingNull();
    }

    public void loadingNull(){
        if (adapter.getItemCount() != 0) {
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
            loadNullImageview.setImageResource(R.mipmap.bg_loading_null);
            ViewUtil.setText(loadNullTextview, "啥也没有了，去其它地方看看吧");
            if (loadNullImageview.getVisibility() == View.GONE) {
                loadNullImageview.setVisibility(View.VISIBLE);
            }
        }
    }
}
