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
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RefreshLayout;
import com.kcj.animationfriend.view.RefreshLayout.OnLoadListener;
import com.liteutil.async.AsyncTask;

/**
 * @ClassName: TabBankumFragment
 * @Description: 番剧
 * @author: KCJ
 * @date: 2015-10-14
 */
public class TabBankumFragment extends BaseFragment implements OnRefreshListener ,OnLoadListener{

	@InjectView(R.id.rl_tab_rank_refresh)
	protected RefreshLayout myRefreshListView;
	@InjectView(R.id.lv_tab_rank)
	protected ListView rankListView;
	protected VideoAdapter rankAdapter;
	protected List<Video> rankList = new ArrayList<Video>();
	protected int videoType = 1; // 视频类别
	protected Boolean refresh = true;
	protected int pageNum = 1;
	
	TabBankumAsyncTask tabBankumAsyncTask;
	
	public TabBankumFragment(int videoType) {
		this.videoType = videoType;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab_rank, container,false);
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
		rankAdapter = new VideoAdapter(getActivity(),rankList);
		rankListView.setAdapter(rankAdapter);
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
		if(tabBankumAsyncTask != null){
			tabBankumAsyncTask.cancel(true);
		}
		tabBankumAsyncTask = new TabBankumAsyncTask();
		tabBankumAsyncTask.execute(pageNum);// new Integer[]{0,0}
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(tabBankumAsyncTask != null){
			tabBankumAsyncTask.cancel(true);
		}
	}
	
	@Override
	public void onRefresh() {
		pageNum = 1;
		refresh = true;
		if(tabBankumAsyncTask != null){
			tabBankumAsyncTask.cancel(true);
		}
		tabBankumAsyncTask = new TabBankumAsyncTask();
		tabBankumAsyncTask.execute(pageNum);
	}
	
	@Override
	public void onLoad() {
		pageNum++;
		refresh = false;
		if(tabBankumAsyncTask != null){
			tabBankumAsyncTask.cancel(true);
		}
		tabBankumAsyncTask = new TabBankumAsyncTask();
		tabBankumAsyncTask.execute(pageNum);
	}
	
	private class TabBankumAsyncTask extends AsyncTask<Integer, Void, List<Video>> {

		@Override
		protected List<Video> doInBackground(Integer... arg0) {
			switch (videoType) {
			case 1:
				return HttpProxy.getHomeMoreVedio(Constant.URL_BANKUN_SERIALIZE,arg0[0],null);
            case 2:
            	return HttpProxy.getHomeMoreVedio(Constant.URL_BANKUN_END,arg0[0],null);
            case 3:
            	return HttpProxy.getHomeMoreVedio(Constant.URL_BANKUN_MESSAGE,arg0[0],null);
            case 4:
            	return HttpProxy.getHomeMoreVedio(Constant.URL_BANKUN_OFFICIAL,arg0[0],null);
            case 5:
            	return HttpProxy.getHomeMoreVedio(Constant.URL_BANKUN_DOMESTIC,arg0[0],null);
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
	
	public void onSuccess(List<Video> list){
		if (list.size() != 0 && list.get(list.size() - 1) != null) {
			if (list.size() < Constant.NUMBERS_PER_PAGE) {
				// ShowToast("已加载完所有数据~");
			}
			if (pageNum == 1) {
				rankList.clear();
			}
			rankList.addAll(list);
			rankAdapter.notifyDataSetChanged();
			stopRefreshLoadMore();
		}else{
			// ShowToast("已加载完所有数据~");
		}
		
	}
	
    public void onFailure(String msg){
		ShowToast(msg);
		stopRefreshLoadMore();
	}
    
    public void stopRefreshLoadMore(){
		if(refresh ){
			myRefreshListView.setRefreshing(false);
		}else {
			myRefreshListView.setLoading(false);
		}
	}

}
