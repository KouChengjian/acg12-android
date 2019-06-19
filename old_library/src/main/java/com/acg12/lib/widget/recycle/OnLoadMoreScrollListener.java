package com.acg12.lib.widget.recycle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by aspsine on 16/3/13.
 */
public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        boolean triggerCondition = visibleItemCount > 0
            && newState == RecyclerView.SCROLL_STATE_IDLE
            && canTriggerLoadMore(recyclerView)
            && effectiveHeight(recyclerView);

        if (triggerCondition) {
            onLoadMore(recyclerView);
        }
    }

    public boolean canTriggerLoadMore(RecyclerView recyclerView) {
        View lastChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        int position = recyclerView.getChildLayoutPosition(lastChild);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        return totalItemCount - 1 == position;
    }

    public boolean effectiveHeight(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = recyclerView.getChildCount();
        int screenHeight = recyclerView.getHeight();
        int visibleHeight = 0;
        if(layoutManager instanceof LinearLayoutManager){

        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            int spanCount = ((StaggeredGridLayoutManager)layoutManager).getSpanCount();
            visibleItemCount =  visibleItemCount / spanCount+ visibleItemCount % spanCount ;
        }

        for (int i = 0; i < visibleItemCount; i++) {
            View lastChild = recyclerView.getChildAt(i);
            visibleHeight += lastChild.getHeight();
            if (visibleHeight > screenHeight) {
                return true;
            }
        }
        return false;
    }

    public abstract void onLoadMore(RecyclerView recyclerView);
}
