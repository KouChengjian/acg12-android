package com.kcj.animationfriend.view;

import com.kcj.animationfriend.listener.LodeMoreCallBack;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

public class RecyclerViewOnScroll extends RecyclerView.OnScrollListener {
	@SuppressWarnings("rawtypes")
	public RecyclerView.Adapter mMyadputer;
	public LodeMoreCallBack mLodeMoreCallBack;
	public int lastVisibleItem = 0;
	public int firstVisibleItem = 0;

	@SuppressWarnings("rawtypes")
	public RecyclerViewOnScroll(RecyclerView.Adapter mMyadputer,LodeMoreCallBack mLodeMoreCallBack) {
		this.mMyadputer = mMyadputer;
		this.mLodeMoreCallBack = mLodeMoreCallBack;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		RecyclerView.LayoutManager layoutManager = recyclerView
				.getLayoutManager();
		// int totalItemCount = layoutManager.getItemCount();
		if (layoutManager instanceof LinearLayoutManager) {
			LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
			lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
			firstVisibleItem = linearLayoutManager
					.findFirstCompletelyVisibleItemPosition();
		} else if (layoutManager instanceof GridLayoutManager) {
			GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
			// 通过LayoutManager找到当前显示的最后的item的position
			lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
			firstVisibleItem = gridLayoutManager
					.findFirstCompletelyVisibleItemPosition();
		} else if (layoutManager instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
			// 因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
			// 得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
			int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager)
					.getSpanCount()];
			staggeredGridLayoutManager
					.findLastVisibleItemPositions(lastPositions);
			lastVisibleItem = findMax(lastPositions);
			firstVisibleItem = staggeredGridLayoutManager
					.findFirstVisibleItemPositions(lastPositions)[0];
		}

	}

	@Override
	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
		super.onScrollStateChanged(recyclerView, newState);
		// 获取总的适配器的数量
		int totalCount = mMyadputer.getItemCount();
		Log.i("总的数目", totalCount + "");
		Log.i("滚动的状态", newState + "");
		// 这个就是判断当前滑动停止了，并且获取当前屏幕最后一个可见的条目是第几个，当前屏幕数据已经显示完毕的时候就去加载数据
		if (newState == RecyclerView.SCROLL_STATE_IDLE
				&& lastVisibleItem + 1 == mMyadputer.getItemCount()) {
			// 回调加载更多
			mLodeMoreCallBack.LodeMore();
		}
	}

	// 找到数组中的最大值

	private int findMax(int[] lastPositions) {

		int max = lastPositions[0];
		for (int value : lastPositions) {
			// int max = Math.max(lastPositions,value);
			if (value > max) {
				max = value;
			}
		}
		return max;
	}
}
