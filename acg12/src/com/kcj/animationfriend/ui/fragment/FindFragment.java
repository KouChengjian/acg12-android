package com.kcj.animationfriend.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.FindAdapter;
import com.kcj.animationfriend.adapter.FindAdapter.OnRecyclerViewItemClickListener;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.listener.HttpRequestListener;
import com.kcj.animationfriend.listener.LodeMoreCallBack;
import com.kcj.animationfriend.ui.VideoFindInfoActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.Network;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RecyclerViewOnScroll;

/**
 * @ClassName: FindFragment
 * @Description: 发现
 * @author: KCJ
 * @date: 2015-11-19 9:05
 */
public class FindFragment extends BaseFragment implements OnRecyclerViewItemClickListener, LodeMoreCallBack, OnRefreshListener {

	@InjectView(R.id.rl_refresh)
	protected SwipeRefreshLayout myRefreshListView;
	@InjectView(R.id.rl_content_null)
	protected View headerView;
	@InjectView(R.id.rl_footer)
	protected View footView;
	@InjectView(R.id.rv_find)
	protected RecyclerView recyclerView;
	protected FindAdapter findAdapter;
	protected List<Video> videoList = new ArrayList<Video>();
	protected int pageNum = 1;
	protected Boolean refresh = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_find, container, false);
		setContentView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initEvent();
		initDatas();
	}

	@Override
	public void initViews() {
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL));
		findAdapter = new FindAdapter(getActivity(), videoList);
		recyclerView.setAdapter(findAdapter);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
		myRefreshListView.setProgressViewOffset(false, -PixelUtil.dp2px(50),PixelUtil.dp2px(24));
		myRefreshListView.setRefreshing(true);
	}

	@Override
	public void initEvent() {
		findAdapter.setOnClickItem(this);
		recyclerView.addOnScrollListener(new RecyclerViewOnScroll(findAdapter,this));
		myRefreshListView.setOnRefreshListener(this);
	}

	@Override
	public void initDatas() {
		if (!Network.isConnected(mContext)) {
			myRefreshListView.setRefreshing(false);
			ShowToast("请链接网络~~~");
			headerView.setVisibility(View.VISIBLE);
			return;
		}
		refresh(pageNum);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onRefresh() {
		pageNum = 1;
		refresh = true;
		refresh(pageNum);
	}

	@Override
	public void LodeMore() {
		pageNum++;
		refresh = false;
		refresh(pageNum);
	}

	@Override
	public void onItemClick(View view, int position) {
		Intent i = new Intent();
		Bundle bundle = new Bundle();
		Video item = (Video) videoList.get(position);
		i.setClass(mContext, VideoFindInfoActivity.class);
		bundle.putSerializable("videoItemdata", item);
		i.putExtras(bundle);
		mContext.startActivity(i);
	}

	public void refresh(int page) {
		HttpProxy.getBankunList(page, new HttpRequestListener<Video>() {

			@Override
			public void onSuccess(List<Video> list) {
				if (list.size() != 0 && list.get(list.size() - 1) != null) {
					if (list.size() < Constant.NUMBERS_PER_PAGE) {
						// ShowToast("已加载完所有数据~");
					}
					if (pageNum == 1) {
						videoList.clear();
					}
					videoList.addAll(list);
					findAdapter.notifyDataSetChanged();
					headerView.setVisibility(View.GONE);
				} else {
					// ShowToast("已加载完所有数据~");
				}
				stopRefreshLoadMore();
			}

			@Override
			public void onFailure(String msg) {
				pageNum--;
				ShowToast(msg);
				Log.e("onFailure", msg);
				stopRefreshLoadMore();
			}
		});
	}

	public void stopRefreshLoadMore() {
		if (refresh) {
			myRefreshListView.setRefreshing(false);
		} else {
			footView.setVisibility(View.GONE);
		}
	}

}
