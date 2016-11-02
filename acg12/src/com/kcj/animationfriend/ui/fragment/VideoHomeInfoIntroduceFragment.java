package com.kcj.animationfriend.ui.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;

import com.android.material.widget.ProgressView;
import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.base.ViewHolderHomeHList;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.VideoInfoActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.view.ScrollTabHolderFragment;
import com.liteutil.async.AsyncTask;
import com.liteutil.util.Log;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @ClassName: VideoHomeInfoIntroduceFragment
 * @Description: 主页视频信息介绍
 * @author: KCJ
 * @date: 2015-10-17
 */
public class VideoHomeInfoIntroduceFragment extends ScrollTabHolderFragment implements OnClickListener{

	@InjectView(R.id.pv_circular_inout)
	protected ProgressView pv_circular_inout;
	@InjectView(R.id.lv_video_info)
	protected ListView lv_video_info;
	protected VideoInfoListAdapter videoInfoListAdapter;
	protected Video video;
	protected ArrayList<Video> videoInfoList = new ArrayList<Video>();
	// header
	protected View headerView;
	protected TextView tv_video_label;
	protected TextView tv_video_duration;
	protected ImageView iv_video_arrow;
	protected TextView tv_video_num;
	// foot
	protected View footView;
	protected RecyclerView footHListView;
	protected HomeHListAdapter areaHListAdapter = null;
	protected DetailsAsyncTask detailsAsyncTask;
	
	protected boolean isClickable = true;
	private static ParameCallBack parameCallBack;
	
	public static BaseFragment newInstance(ParameCallBack callBack) {
    	BaseFragment fragment = new VideoHomeInfoIntroduceFragment();
    	parameCallBack = callBack;
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_video_info_introduce, container,false);
		setContentView(view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		video = (Video)getArguments().getSerializable("video");
		initViews();
		initEvent();
		initDatas();
	}
	
	@SuppressLint("InflateParams")
	@Override
	public void initViews() {
		super.initViews();
		pv_circular_inout.start();
		lv_video_info.setVisibility(View.GONE);
		// headerview
		headerView = mInflater.inflate(R.layout.include_video_info_3, null);
		tv_video_label = (TextView) headerView.findViewById(R.id.tv_video_label);
		tv_video_duration = (TextView) headerView.findViewById(R.id.tv_video_duration);
		iv_video_arrow = (ImageView) headerView.findViewById(R.id.iv_video_arrow);
		tv_video_num = (TextView) headerView.findViewById(R.id.tv_video_num);
		lv_video_info.addHeaderView(headerView);
		// footview
		footView = mInflater.inflate(R.layout.include_video_info_4, null);
		footHListView = (RecyclerView) footView.findViewById(R.id.list_tools);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);  
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);  
        footHListView.setLayoutManager(linearLayoutManager); 
		videoInfoListAdapter = new VideoInfoListAdapter(mContext,videoInfoList, video.getAid());
		lv_video_info.setAdapter(videoInfoListAdapter);
	}
	
	@Override
	public void initEvent() {
		super.initEvent();
		iv_video_arrow.setOnClickListener(this);
	}
	
	@Override
	public void initDatas() {
		super.initDatas();
		refreshData(video);
		if(detailsAsyncTask != null){
			detailsAsyncTask.cancel(true);
		}
		detailsAsyncTask = new DetailsAsyncTask();
		detailsAsyncTask.execute("");
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.iv_video_arrow:
    	    if (isClickable) {
    		    tv_video_duration.setMaxLines(tv_video_duration.getLineCount());
			    isClickable = false;
			    iv_video_arrow.setImageResource(R.drawable.icon_show_video_content_close);
		    } else {
			    tv_video_duration.setMaxLines(2);
			    isClickable = true;
			    iv_video_arrow.setImageResource(R.drawable.icon_show_video_content);
		    }
		    break;
		}
	}
	
	public void refreshData(Video video){
		if (video != null) {
			tv_video_label.setText(video.getSbutitle());
			tv_video_duration.setText(video.getDescription());
			if (tv_video_duration.getLineCount() > 2) {
				iv_video_arrow.setVisibility(View.VISIBLE);
			}
			if (videoInfoList != null) {
				if (videoInfoList.size() == 0) {
					tv_video_num.setVisibility(View.GONE);
				}else{
					tv_video_num.setVisibility(View.VISIBLE);
				}
				tv_video_num.setText("共有" + videoInfoList.size() + "段视频");
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	private class DetailsAsyncTask extends AsyncTask<String, Void, List<Video>> {

		@Override
		protected List<Video> doInBackground(String... params) {
			try {
				videoInfoList.clear();
				Document document = Jsoup.connect(Constant.URL_GET_VIDEO_INFO+video.getAid()+".html").data("jquery", "java")
						.userAgent("Mozilla").cookie("auth", "token")
						.timeout(50000).get();
				Elements listElements = document.getElementsByClass("li-wrap-content");
				Log.i("TAG", Constant.URL_GET_VIDEO_INFO+video.getAid()+".html"+"======");
				Log.i("size", listElements.size()+"======");
				for (int i = 0; i < listElements.size(); i++) {
					Video video = new Video();
					video.setTitle(listElements.get(i).text());
					videoInfoList.add(video);
				}
				Elements labelElements = document.select("[name=keywords]");
			    String label = labelElements.attr("content");
				video.setSbutitle(label);	
			} catch (IOException e) {
				e.printStackTrace();
				Log.e("IOException", e.toString()+"====");
				return null;
			}
			return videoInfoList;
		}
		
		@Override
		protected void onPostExecute(List<Video> result) {
			if(video.getVideoList() != null && !video.getVideoList().isEmpty()){
				areaHListAdapter = new HomeHListAdapter(mContext ,video.getVideoList());
				footHListView.setAdapter(areaHListAdapter);
			}
			videoInfoList.addAll(result);
			lv_video_info.addFooterView(footView);
			videoInfoListAdapter.notifyDataSetChanged();
			refreshData(video);
			
			lv_video_info.setVisibility(View.VISIBLE);
			pv_circular_inout.stop();
			if(result == null || result.isEmpty()){
				parameCallBack.onCall(true);
			}else{
				parameCallBack.onCall(true);
			}
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
	
	/**
	 * @ClassName: VideoInfoListAdapter
	 * @Description: 
	 * @author: KCJ
	 * @date: 2015-9-20
	 */
	public class VideoInfoListAdapter extends BaseAdapter{
		private Context mContext;
		private List<Video> mList;
		Video videoItem;
		String av;
		String page;
		
		public VideoInfoListAdapter(Context mContext,List<Video> mList,String av){
			this.mContext = mContext;
			this.mList = mList;
			this.av = av;
		}
		
		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Video getItem(int position) {
			return mList == null ? null : mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			videoItem = getItem(position);
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_video_info_num, null);
				holder.titleTextView = (TextView) convertView.findViewById(R.id.title);
				holder.itemView = convertView.findViewById(R.id.linearlayout_row);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.titleTextView.setText(mList.get(position).getTitle());
			holder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mList.get(position).getAid() == null || mList.get(position).getAid().isEmpty()){
						// 处理跳转逻辑
						page = String.valueOf(position+1);
						Intent intent = new Intent(mContext, VideoInfoActivity.class);
						intent.putExtra("displayName",mList.get(position).getTitle());
						intent.putExtra("av",av);
						intent.putExtra("page",page);
						startActivity(intent);
					}else{
						Video video = mList.get(position);
						Intent intent = new Intent(mContext, VideoInfoActivity.class);
						intent.putExtra("displayName",video.getTitle());
						intent.putExtra("av",video.getAid());
						intent.putExtra("page",1+"");
						startActivity(intent);
					}
				}
			});
			return convertView;
		}

		class ViewHolder {
			View itemView;
			TextView titleTextView;
		}
	}
	
	/**
	 * @ClassName: HomeHListAdapter
	 * @Description: List
	 * @author: KCJ
	 * @date: 2015-9-20
	 */
	class HomeHListAdapter extends RecyclerView.Adapter<ViewHolderHomeHList>{

		private Context mContext;
		private List<Video> mList;

		public HomeHListAdapter(Context mContext,List<Video> mList){
			this.mContext = mContext;
			this.mList = mList;
		}
		
		@Override
		public int getItemCount() {
			return mList.size();
		}
		
		public void addAllList(List<Video> mLists){
			mList.addAll(mLists);
		}
		
		@Override
		public ViewHolderHomeHList onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(mContext).inflate(R.layout.item_area_hlist, parent, false);
			return new ViewHolderHomeHList(view);
		}

		@Override
		public void onBindViewHolder(final ViewHolderHomeHList mHolder, final int position) {
			Video object = mList.get(position);
			mHolder.ll_item_area_album.setVisibility(View.GONE);
			mHolder.ll_item_area_vedio.setVisibility(View.GONE);
			mHolder.ll_item_area_num.setVisibility(View.GONE);
			mHolder.ll_item_area_name.setVisibility(View.GONE);
			mHolder.ll_item_area_author.setVisibility(View.GONE);
			mHolder.ll_item_name.setVisibility(View.VISIBLE);
			String url = object.getPic();
			ImageLoader.getInstance().displayImage(url, mHolder.icon, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){
				@Override
				public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
				}
			});
			mHolder.tv_item_name.setText(object.getTitle());
			mHolder.icon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent();
					Bundle bundle = new Bundle();
					if(mList.get(position) instanceof Video){
						Video item = (Video) mList.get(position);
						i.setClass(mContext, VideoInfoActivity.class);
						bundle.putSerializable("videoItemdata", item);
					}
					i.putExtras(bundle);
					mContext.startActivity(i);
				}
			});
		}
	}
}
