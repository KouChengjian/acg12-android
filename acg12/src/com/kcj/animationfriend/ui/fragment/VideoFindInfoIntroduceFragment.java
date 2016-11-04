package com.kcj.animationfriend.ui.fragment;


import java.util.ArrayList;
import java.util.List;

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
import android.widget.AbsListView;
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
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.HttpRequestListener;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.VideoInfoActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.view.ScrollTabHolderFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/**
 * @ClassName: VideoInfoTabDetailsFragment
 * @Description: 视频信息 - 详情
 * @author: KCJ
 * @date: 2015-10-17
 */
public class VideoFindInfoIntroduceFragment extends ScrollTabHolderFragment implements OnClickListener {

	protected TextView tv_video_label;
	protected TextView tv_video_duration;
	protected ImageView iv_video_arrow;
	protected TextView tv_video_num;
	protected ProgressView pv_circular_inout;
	@InjectView(R.id.lv_video_info)
	protected ListView lv_video_info;
	protected VideoInfoListAdapter videoInfoListAdapter;
	protected Video video;
	protected ArrayList<Video> videoInfoList = new ArrayList<Video>();
	
	protected static ParameCallBack parameCallBack;
	protected boolean isClickable = true;
	// foot
	protected View footView;
	protected RecyclerView footHListView;
	protected HomeHListAdapter areaHListAdapter = null;
	//protected DetailsAsyncTask detailsAsyncTask;
	
	public static BaseFragment newInstance(ParameCallBack callBack) {
    	BaseFragment fragment = new VideoFindInfoIntroduceFragment();
    	parameCallBack = callBack;
    	return fragment;
    }
	
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
		initViews();
		initEvent();
		initDatas();
	}
	
	@SuppressLint("InflateParams")
	@Override
	public void initViews() {
		super.initViews();
		pv_circular_inout = (ProgressView)findViewById(R.id.pv_circular_inout);
		pv_circular_inout.start();
		lv_video_info.setVisibility(View.GONE);
		// headerview
		View llInfo = mInflater.inflate(R.layout.include_home_video_info_introduction_header, null);
		tv_video_label = (TextView) llInfo.findViewById(R.id.tv_video_label);
		tv_video_duration = (TextView) llInfo.findViewById(R.id.tv_video_duration);
		iv_video_arrow = (ImageView) llInfo.findViewById(R.id.iv_video_arrow);
		tv_video_num = (TextView) llInfo.findViewById(R.id.tv_video_num);
		lv_video_info.addHeaderView(llInfo);
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
		lv_video_info.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onListViewScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, 0);
                }
            }
        });
	}

	@Override
	public void initDatas() {
		super.initDatas();
		refreshData(video);
//		if(detailsAsyncTask != null){
//			detailsAsyncTask.cancel(true);
//		}
//		detailsAsyncTask = new DetailsAsyncTask();
//		detailsAsyncTask.execute("");
		refresh();
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
	
	@Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (lv_video_info == null) return;
        if (scrollHeight == 0 && lv_video_info.getFirstVisiblePosition() >= 1) {
            return;
        }
        lv_video_info.setSelectionFromTop(1, scrollHeight);
    }
	
	public void refresh(){
		String av = video.getUrlInfo().split("/anime/")[1];
		HttpProxy.getBankunInfo(av, new HttpRequestListener<Video>() {
			
			@Override
			public void onSuccess(Video result) {
				if(result.getQuarterVideoList() != null && !result.getQuarterVideoList().isEmpty()){
					areaHListAdapter = new HomeHListAdapter(mContext ,result.getQuarterVideoList());
					footHListView.setAdapter(areaHListAdapter);
				}
				videoInfoList.addAll(result.getBangumiVideoList());
				lv_video_info.addFooterView(footView);
				videoInfoListAdapter.notifyDataSetChanged();
				refreshData(result);
				lv_video_info.setVisibility(View.VISIBLE);
				pv_circular_inout.stop();
				if(result == null && !result.getBangumiVideoList().isEmpty()){
					parameCallBack.onCall(video);
				}else{
					parameCallBack.onCall(videoInfoList.get(0));
				}
			}
			
			@Override
			public void onFailure(String msg) {
				ShowToast(msg);
				lv_video_info.setVisibility(View.VISIBLE);
				pv_circular_inout.stop();
			}
		});
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
//		if(detailsAsyncTask != null){
//			detailsAsyncTask.cancel(true);
//		}
	}
	
//	/**
//	 * @ClassName: DetailsAsyncTask
//	 * @Description: 
//	 * @author: KCJ
//	 * @date: 2015-12-16
//	 */
//	private class DetailsAsyncTask extends AsyncTask<String, Void, List<Video>> {
//
//		@Override
//		protected List<Video> doInBackground(String... arg0) {
//			List<Video> videoList = new ArrayList<Video>();
//			try {
//				Log.e("video.getUrlInfo()", video.getUrlInfo());
//				Document document = Jsoup.connect(video.getUrlInfo()).data("jquery", "java")
//						.userAgent("Mozilla").cookie("auth", "token")
//						.timeout(50000).get();
//				Log.e("url", video.getUrlInfo()+"=====");
//				Log.i("document", document.toString()+"=====");                                             
//				Elements tags = document.select("div.nfo-row,.info-style");
//				Elements taga = tags.select("a");
//				String label = "";
//				for(Element tag:taga){
//					Log.e("tag", tag.text()+"=====");
//					label = label+"  "+tag.text();
//				}
//				video.setSbutitle(label);
//				Elements bangumi_info_r = document.select("div.bangumi-info-r");
//				Elements info_descs = bangumi_info_r.select("div.info-row,.info-desc-wrp");
//				Elements info = info_descs.select("div.info-desc");
//				video.setDescription(info.text());
//				Log.i("info", info.text()+"====");
//				// 获取视频
//				Elements episode_list_wrp = document.select("div.episode-list-wrp");
//				Elements episode_list = episode_list_wrp.select("div.episode-list,.initialized,.ep-mode-cover");
//				Elements links = episode_list.select("a[href]");
//				for(int i = 0;i< links.size();i++){
//					Video item = new Video();
//					Element link = links.get(i);
//					item.setAid(link.attr("href").split("/")[2].replace("av",""));
//					item.setTitle(link.attr("title"));
//					videoList.add(item);
//					i++;
//					Log.i("vedioList", link.attr("href").split("/")[2].replace("av","")+"====");
//					Log.i("vedioList", link.attr("title")+"====");
//				}
//				// 相关推荐
//				Elements page_video_wrp = document.select("div.page-video-wrp");
//				Elements page_video_wrp_r = page_video_wrp.select("div.page-video-wrp-r");
//				Elements r_item = page_video_wrp_r.select("div.page-video-wrp-r");
//				Elements a_items = r_item.select("a[href]");
//				List<Video> list = new ArrayList<Video>();
//				for(int i = 0;i< a_items.size();i++){
//					Video item = new Video();
//					Element a_item = a_items.get(i);
//					item.setAid(a_item.attr("href"));
//					item.setTitle(a_item.attr("title"));
//					Elements preview = page_video_wrp_r.select("div.preview");
//					Element media = preview.select("[src]").get(0);
//					Elements num = preview.get(0).getElementsByClass("num");
//					item.setPic(media.attr("abs:data-img"));
//					item.setTitle(a_item.attr("title"));
//					item.setUrlInfo(Constant.URL_NEW_BANKUN_INFO+a_item.attr("href").replaceAll(" ", "%20"));
//					item.setUpdateContent(num.text());
//					list.add(item);
//					Log.i("num", num.text()+"=====");
//					Log.i("pic", media.attr("abs:data-img")+"=====");
//					Log.i("url", a_item.attr("href")+"====");
//					Log.i("title", a_item.attr("title")+"====");
//				}
////				video.setVideoList(list);
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//			return videoList;
//		}
//		
//		@Override
//		protected void onPostExecute(List<Video> result) {
//			super.onPostExecute(result);
////			if(video.getVideoList() != null && !video.getVideoList().isEmpty()){
////				areaHListAdapter = new HomeHListAdapter(mContext ,video.getVideoList());
////				footHListView.setAdapter(areaHListAdapter);
////			}
//			videoInfoList.addAll(result);
//			lv_video_info.addFooterView(footView);
//			videoInfoListAdapter.notifyDataSetChanged();
//			refreshData(video);
//			lv_video_info.setVisibility(View.VISIBLE);
//			pv_circular_inout.stop();
//			if(result == null || result.isEmpty()){
//				parameCallBack.onCall(video);
//			}else{
//				parameCallBack.onCall(videoInfoList.get(0));
//			}
//		}
//	}
	
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
