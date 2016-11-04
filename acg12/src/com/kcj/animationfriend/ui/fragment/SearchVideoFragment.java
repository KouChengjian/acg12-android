package com.kcj.animationfriend.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.VideoAdapter;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.listener.HttpRequestListener1;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RefreshLayout;
import com.kcj.animationfriend.view.RefreshLayout.OnLoadListener;


/**
 * @ClassName: SearchFragment
 * @Description: 搜索视频
 * @author: KCJ
 * @date: 2015-11-26 15：04 
 */
public class SearchVideoFragment extends BaseFragment implements OnRefreshListener ,OnLoadListener{

	@InjectView(R.id.rl_tab_rank_refresh)
	protected RefreshLayout myRefreshListView;
	@InjectView(R.id.lv_tab_rank)
	protected ListView rankListView;
	protected VideoAdapter dongmanAdapter;
	protected List<Video> dongmanList = new ArrayList<Video>();
	protected Boolean refresh = true;
	protected Boolean isLoad = true;
	protected int pageNum = 1;
	protected String title;
	
	public static BaseFragment newInstance(String title ) {
    	BaseFragment fragment = new SearchVideoFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("title", title);
    	fragment.setArguments(args);
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab_rank, container ,false);
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
		dongmanAdapter = new VideoAdapter(getActivity(),dongmanList);
		rankListView.setAdapter(dongmanAdapter);
		myRefreshListView.setColorSchemeResources(R.color.theme_primary);
		myRefreshListView.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
		myRefreshListView.setRefreshing(true);
	}
	
	@Override
	public void initEvent() {
		myRefreshListView.setOnRefreshListener(this);
    	myRefreshListView.setOnLoadListener(this);
	}
	
	@Override
	public void initDatas() {
		super.initDatas();
		refresh(title,pageNum);
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
	public void onLoad() {
		if(!isLoad)
			return;
		pageNum++;
		refresh = false;
		refresh(title,pageNum);
	}
	
	public void refresh(String key,int page) {
		HttpProxy.getSearchVideo(key , page ,new HttpRequestListener1<Video>() {
			@Override
			public void onSuccess(List<Video> list) {
				if (list.size() != 0 && list.get(list.size() - 1) != null) {
					if (list.size() < Constant.NUMBERS_PER_PAGE) {
						isLoad  = false;
					}
					if (pageNum == 1) {
						dongmanList.clear();
					}
					dongmanList.addAll(list);
					dongmanAdapter.notifyDataSetChanged();
				} else {
					isLoad  = false;
				}
				stopRefreshLoadMore();
			}
			
			@Override
			public void onFailure(String msg) {
				ShowToast(msg);
				stopRefreshLoadMore();
			}
		});
	}
	
	public void stopRefreshLoadMore(){
		if(refresh){
			myRefreshListView.setRefreshing(false);
		}else {
			myRefreshListView.setLoading(false);
		}
	}
	
}
