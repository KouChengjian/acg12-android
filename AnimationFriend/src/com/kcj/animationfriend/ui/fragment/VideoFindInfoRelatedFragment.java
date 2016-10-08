package com.kcj.animationfriend.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
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
import com.kcj.animationfriend.listener.HttpRequestListener;
import com.kcj.animationfriend.view.ScrollTabHolderFragment;
import com.liteutil.async.AsyncTask;


/**
 * @ClassName: VideoInfoTabRelatedFragment
 * @Description: 视频信息 - 相关
 * @author: KCJ
 * @date: 2015-10-17
 */
public class VideoFindInfoRelatedFragment extends ScrollTabHolderFragment{

	@InjectView(R.id.lv_video_info)
	protected ListView pinnePagerdListView;
	protected VideoAdapter pinnePagerdAdapter;
	protected List<Video> pinnePagerdList = new ArrayList<Video>();
	
	protected Video video;
	PinnePagerdAsyncTask pinnePagerdAsyncTask;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_find_video_info_introduce, container,false);
		setContentView(view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		video = (Video)getArguments().getSerializable("video");
//		initViews();
//		initEvent();
//		initDatas();
	}
	
	@Override
	public void initViews() {
		super.initViews();
		pinnePagerdAdapter = new VideoAdapter(getActivity(),pinnePagerdList);
		pinnePagerdListView.setAdapter(pinnePagerdAdapter);
	}
	
	@Override
	public void initEvent() {
		super.initEvent();
	}
	
	@Override
	public void initDatas() {
		super.initDatas();
		if(video.getUrlInfo() == null){
			
		}
        if(pinnePagerdAsyncTask != null){
        	pinnePagerdAsyncTask.cancel(true);
        }
        pinnePagerdAsyncTask = new PinnePagerdAsyncTask();
        pinnePagerdAsyncTask.execute(0);
	}
	
	public class PinnePagerdAsyncTask extends AsyncTask<Integer, String, List<Video>> {

		@Override
		protected void onCancelled() {}
		
		@Override
		protected List<Video> doInBackground(Integer... params) {
			if(video.getUrlInfo() == null){
				final List<Video> lists = new ArrayList<Video>();
				HttpProxy.getVideoRecommendString(mContext,Constant.URL_NEW_BANKUN_RE+video.getAid(),new HttpRequestListener<Video>() {
					
					@Override
					public void onSuccess(List<Video> list) {
						lists.addAll(list);
					}
					
					@Override
					public void onFailure(String msg) {
						
					}
				});
				return lists;
			}else{
//				return HttpProxy.getVideoHotString(mContext,video.getVideoAid(),0);
				return null;
			}
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
		if (result.size() != 0 && result.get(result.size() - 1) != null) {
			pinnePagerdList.addAll(result);
			pinnePagerdAdapter.notifyDataSetChanged();
		}
	}
	
    public void onFailure(String msg){
//		ShowToast(msg);
	}
}
