package com.kcj.animationfriend.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.kcj.animationfriend.listener.HttpRequestListener1;
import com.kcj.animationfriend.listener.LodeMoreCallBack;
import com.kcj.animationfriend.ui.VideoFindInfoActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RecyclerViewOnScroll;


/**
 * @ClassName: SearchSeriesFragment
 * @Description: 搜索系列番剧
 * @author: KCJ
 * @date: 2015-11-26  15：04 
 */
public class SearchBangunFragment extends BaseFragment implements OnRecyclerViewItemClickListener ,LodeMoreCallBack ,OnRefreshListener{

	@InjectView(R.id.rl_refresh)
	protected SwipeRefreshLayout myRefreshListView;
	@InjectView(R.id.rl_footer)
	protected View footView;
	@InjectView(R.id.rv_find)
	protected RecyclerView recyclerView;
	protected FindAdapter findAdapter;
	protected List<Video> videoList = new ArrayList<Video>();
	protected int pageNum = 1;
	protected Boolean refresh = true;
	protected Boolean isLoad = true;
	protected String title;
	
	public static BaseFragment newInstance(String title ) {
    	BaseFragment fragment = new SearchBangunFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("title", title);
    	fragment.setArguments(args);
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_find, container, false);
		setContentView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		title = getArguments().getString("title");
		initViews();
		initEvent();
		initDatas();
	}
	
	@Override
	public void initViews() {
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
		findAdapter = new FindAdapter(getActivity(), videoList);
		findAdapter.setOnClickItem(this);
		recyclerView.setAdapter(findAdapter);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
		myRefreshListView.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
		myRefreshListView.setRefreshing(true);
	}
	
	@Override
	public void initEvent() {
		recyclerView.addOnScrollListener(new RecyclerViewOnScroll(findAdapter, this));
    	myRefreshListView.setOnRefreshListener(this);
	}
	
	@Override
	public void initDatas() {
		refresh(title,pageNum);
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
		isLoad = true;
		refresh(title,pageNum);
	}
	
	@Override
	public void LodeMore() {
		if(!isLoad)
			return;
		pageNum++;
		refresh= false;
		footView.setVisibility(View.VISIBLE);
		refresh(title,pageNum);
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
	
	public void refresh(String key,int page) {
		HttpProxy.getSearchBangunmi(key , page ,new HttpRequestListener1<Video>() {
			@Override
			public void onSuccess(List<Video> list) {
				if (list.size() != 0 && list.get(list.size() - 1) != null) {
					if(pageNum == 1){
			        	videoList.clear();
					}
					if (list.size() < Constant.NUMBERS_PER_PAGE) {
						isLoad = false;
					}
					videoList.addAll(list);
					findAdapter.notifyDataSetChanged();
				} else {
					isLoad = false;
				}
				stopRefreshLoadMore();
			}
			
			@Override
			public void onFailure(String msg) {
				pageNum--;
				isLoad = false;
				ShowToast(msg);
				stopRefreshLoadMore();
			}
		});
	}
	
	public void stopRefreshLoadMore(){
		if(refresh){
			myRefreshListView.setRefreshing(false);
		}else{
			footView.setVisibility(View.GONE);
		}
	}
}
