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
 * @ClassName: TabDonghuaFragment
 * @Description: 动画
 * @author: KCJ
 * @date: 2015-10-14
 */
public class TabDonghuaFragment extends BaseFragment implements OnRefreshListener ,OnLoadListener{

	@InjectView(R.id.rl_tab_rank_refresh)
	protected RefreshLayout myRefreshListView;
	@InjectView(R.id.lv_tab_rank)
	protected ListView rankListView;
	protected VideoAdapter dongmanAdapter;
	protected List<Video> dongmanList = new ArrayList<Video>();
	protected int videoType = 0; // 视频类别
	protected boolean refresh = true;
	protected int pageNum = 1;
	DongManAsyncTask dongManAsyncTask;
	
	public TabDonghuaFragment(int videoType) {
		this.videoType = videoType;
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
		if(dongManAsyncTask != null){
			dongManAsyncTask.cancel(true);
		}
		dongManAsyncTask = new DongManAsyncTask();
		dongManAsyncTask.execute(pageNum);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(dongManAsyncTask != null){
			dongManAsyncTask.cancel(true);
		}
	}
	
	@Override
	public void onRefresh() {
		pageNum = 1;
		refresh = true;
		if(dongManAsyncTask != null){
			dongManAsyncTask.cancel(true);
		}
		dongManAsyncTask = new DongManAsyncTask();
		dongManAsyncTask.execute(pageNum);
	}
	
	@Override
	public void onLoad() {
		pageNum++;
		refresh = false;
		if(dongManAsyncTask != null){
			dongManAsyncTask.cancel(true);
		}
		dongManAsyncTask = new DongManAsyncTask();
		dongManAsyncTask.execute(pageNum);
	}

	public class DongManAsyncTask extends AsyncTask<Integer, String, List<Video>> {

		@Override
		protected void onCancelled() {}
		
		@Override
		protected List<Video> doInBackground(Integer... params) {
			switch (videoType) {
			case 1:
				return HttpProxy.getHomeMoreVedio(Constant.URL_DONGMAN_MAD_AMV,params[0],null);
            case 2:
            	return HttpProxy.getHomeMoreVedio(Constant.URL_DONGMAN_MMD_3D,params[0],null);
            case 3:
            	return HttpProxy.getHomeMoreVedio(Constant.URL_DONGMAN_SHORT_FILM,params[0],null);
            case 4:
            	return HttpProxy.getHomeMoreVedio(Constant.URL_DONGMAN_SYNTHESIZE,params[0],null);
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
		dongmanList.addAll(result);
		dongmanAdapter.notifyDataSetChanged();
		stopRefreshLoadMore();
	}
	
    public void onFailure(String msg){
		ShowToast(msg);
		stopRefreshLoadMore();
	}
	
	public void stopRefreshLoadMore(){
		if(refresh){
			myRefreshListView.setRefreshing(false);
		}else {
			myRefreshListView.setLoading(false);
		}
	}

	
}
