package com.kcj.animationfriend.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.view.RefreshLayout;
import com.kcj.animationfriend.view.RefreshLayout.OnLoadListener;


/**
 * @ClassName: TabRankFragment
 * @Description: 排行榜
 * @author: KCJ
 * @date: 
 */
@SuppressLint("InflateParams")
public class TabRankFragment extends BaseFragment {

	@InjectView(R.id.rl_tab_rank_refresh)
	protected RefreshLayout myRefreshListView;
	@InjectView(R.id.lv_tab_rank)
	protected ListView rankListView;
	protected VideoAdapter rankAdapter;
	protected List<Video> rankList = new ArrayList<Video>();
	protected int videoType = 1; // 视频类别
	protected int refresh = 0;
	
	public TabRankFragment(int videoType) {
		this.videoType = videoType;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab_rank, container , false);
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
	@SuppressWarnings("deprecation")
	public void initViews() {
		rankAdapter = new VideoAdapter(getActivity(),rankList);
		rankListView.setAdapter(rankAdapter);
		// 设置下拉刷新时的颜色值,颜色值需要定义在xml中
		myRefreshListView.setColorScheme(R.color.theme_color);
	}

	@Override
	public void initEvent() {
		myRefreshListView.isLoading(false);
		myRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				refresh = 1;
				new TabRankAsyncTask().execute(videoType);
			}
    	});
    	myRefreshListView.setOnLoadListener(new OnLoadListener() {
    		@Override
    		public void onLoad() {
    			refresh = 2;
    		}
    	});
	}
	
	@Override
	public void initDatas() {
		super.initDatas();
		new TabRankAsyncTask().execute(videoType);
	}
	
	
	private class TabRankAsyncTask extends AsyncTask<Integer, Void, List<Video>> {

		@Override
		protected List<Video> doInBackground(Integer... arg0) {
			switch (arg0[0]) {
			case 1:
//				return HttpProxy.getRankHtmlString(mContext,HttpUrl.URL_RANK_BANGUMI,0);
            case 2:
//            	return HttpProxy.getRankHtmlString(mContext,HttpUrl.URL_RANK_DOUGA,0);
            case 3:
//            	return HttpProxy.getRankHtmlString(mContext,HttpUrl.URL_RANK_MUSIC,0);
            case 4:
//            	return HttpProxy.getRankHtmlString(mContext,HttpUrl.URL_RANK_ENT,0);
            case 5:
//            	return HttpProxy.getRankHtmlString(mContext,HttpUrl.URL_RANK_KICHIKU,0);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(List<Video> result) {
			if(result == null){
				onFailure("数据接收失败~~~");
			}else{
				onSuccess(result);
			}
		}
	}
	
	public void onSuccess(List<Video> result){
		rankList.clear();
		rankList.addAll(result);
		rankAdapter.notifyDataSetChanged();
		stopRefreshLoadMore();
	}
	
    public void onFailure(String msg){
		ShowToast(msg);
		stopRefreshLoadMore();
	}
    
    public void stopRefreshLoadMore(){
		if(refresh == 1){
			myRefreshListView.setRefreshing(false);
		}else if(refresh == 2){
			myRefreshListView.setLoading(false);
		}
	}
}
