package com.kcj.animationfriend.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.InjectView;

import com.android.material.ui.fragment.DialogFragment;
import com.android.material.view.Dialog;
import com.android.material.view.SimpleDialog;
import com.android.material.view.ThemeManager;
import com.kcj.animationfriend.DownloadService;
import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.FindScrollTabPagerdAdapter;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.util.StringUtils;
import com.kcj.animationfriend.view.ScrollTabHolder;
import com.liteutil.async.AsyncTask;
import com.liteutil.exception.DbException;
import com.liteutil.util.Log;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @ClassName: VideoInfoActivity
 * @Description: 视频信息
 * @author: KCJ
 * @date: 2015-9-25
 */
@SuppressLint("InflateParams")
public class VideoInfoActivity1 extends BaseSwipeBackActivity implements OnClickListener ,ScrollTabHolder ,Toolbar.OnMenuItemClickListener{
	
	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	protected View llInfo;
	protected ImageView iv_video_icon;
	protected TextView tv_video_title;
	protected TextView tv_video_author;
	protected TextView tv_video_playnum;
	protected TextView tv_video_review;
	protected TextView tv_video_time;
	protected Button btn_video_play;
	@InjectView(R.id.top_view)
	protected LinearLayout top_view;
	@InjectView(R.id.ll_info_header)
	protected RelativeLayout ll_info_header;
	@InjectView(R.id.pager)
	protected ViewPager pager;
	@InjectView(R.id.indicator)
	protected TabLayout tabLayout;
	protected FindScrollTabPagerdAdapter adapter;
	protected Video video;
	protected VideoInfoTask videoInfoTask;
	protected List<Video> videoInfoList = new ArrayList<Video>();
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_videoinfo);
		video = (Video) getIntent().getSerializableExtra("videoItemdata");
		setTitle(R.string.video_info);
		setSupportActionBar(toolbar);
		initViews();
		initEvent();
		initDatas();
	}
	
	@Override
	public void initViews() {
		if(video.getUrlInfo() == null){
			llInfo = mInflater.inflate(R.layout.include_video_info_1, null);
			iv_video_icon = (ImageView)llInfo.findViewById(R.id.iv_video_icon);
			tv_video_title = (TextView)llInfo.findViewById(R.id.tv_video_title);
			tv_video_author = (TextView)llInfo.findViewById(R.id.tv_video_author);
			tv_video_playnum = (TextView)llInfo.findViewById(R.id.tv_video_playnum);
			tv_video_review = (TextView)llInfo.findViewById(R.id.tv_video_review);
			tv_video_time = (TextView)llInfo.findViewById(R.id.tv_video_time);
			btn_video_play = (Button)llInfo.findViewById(R.id.btn_video_play);
		}else{
			llInfo = mInflater.inflate(R.layout.include_video_info_2, null);
			iv_video_icon = (ImageView)llInfo.findViewById(R.id.iv_video_icon);
			tv_video_title = (TextView)llInfo.findViewById(R.id.tv_video_title);
			tv_video_playnum = (TextView)llInfo.findViewById(R.id.tv_video_playnum);
			tv_video_review = (TextView)llInfo.findViewById(R.id.tv_video_review);
			tv_video_time = (TextView)llInfo.findViewById(R.id.tv_video_time); // 这里显示更新时间
			btn_video_play = (Button)llInfo.findViewById(R.id.btn_video_play);
		}
		ll_info_header.addView(llInfo);
	}
	
	@Override
	public void initEvent() {
		btn_video_play.setOnClickListener(this);
		toolbar.setOnMenuItemClickListener(this);
	}
	
	@Override
	public void initDatas() {
		if(video != null){
			if(video.getUrlInfo() == null){
				ImageLoader.getInstance().displayImage(video.getPic(), iv_video_icon, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){});
				tv_video_title.setText(video.getTitle());
				tv_video_author.setText("Up主："+video.getAuthor());
				tv_video_playnum.setText("播放："+StringUtils.numswitch(video.getPlay()));
				tv_video_review.setText("弹幕："+StringUtils.numswitch(video.getVideoReview()));
				tv_video_time.setText("发布于："+video.getCreate());
			}else{
				ImageLoader.getInstance().displayImage(video.getPic(), iv_video_icon, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){});
				tv_video_title.setText(video.getTitle());
			}
			if(videoInfoTask != null){
				videoInfoTask.cancel(true);
			}
			videoInfoTask = new VideoInfoTask();
			videoInfoTask.execute("");
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(videoInfoTask != null){
		    videoInfoTask.cancel(true);
	    }
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		video = (Video) intent.getSerializableExtra("videoItemdata");
		initDatas();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_download, menu);
//		MenuItem item = menu.findItem(R.id.menu_video_download);
//		item.setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.menu_video_download:
			downloadVoid();
	        break;
	    }
		return true;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_video_play:
			if(btn_video_play.getText().equals("点击播放")){
				Intent intent = new Intent(mContext, VideoPlayActivity1.class);
				intent.putExtra("displayName",video.getTitle());
				intent.putExtra("av",video.getAid());
				intent.putExtra("page","1");
				startActivity(intent);
			}else if(btn_video_play.getText().equals("播放01话")){
				Video video = videoInfoList.get(videoInfoList.size()-1);
				Intent intent = new Intent(mContext, VideoPlayActivity1.class);
				intent.putExtra("displayName",video.getTitle());
				intent.putExtra("av",video.getAid());
				intent.putExtra("page","1");
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}
	
	public void downloadVoid(){
		Dialog.Builder builder = null;
		boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
		builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                CharSequence[] values =  getSelectedValues();
                if(values == null){
                	ShowToast("请选择视频~~~");
                }else{
                    for(int i = 0; i < values.length; i++){
                    	if(videoInfoList == null || videoInfoList.isEmpty()){
//                    		getDownloadList(values[i].toString(),video.getAid(),1);
//                    		download = new Download();
//                    		download.setFileName(values[i].toString());
//                    		download.setDownLoadAv(video.getAid());
                    		//UserProxy.getLiteOrmInstance().save(download);
                    		try {
								DownloadService.getDownloadManager().
								startDownload("http://v.iask.com/v_play_ipad.php?vid=95239034", "测试","/sdcard/xUtils/" + "测试" + ".mp4", true, false, null);
							} catch (DbException e) {
								e.printStackTrace();
							}
                        }else{
                        	for(int j = 0 ; j < videoInfoList.size() ;j++){
                        		Video video = videoInfoList.get(j);
                        		if(video.getTitle().equals(values[i].toString())){
//                        			getDownloadList(values[i].toString(),video.getAid(),1);
//                        			download = new Download();
//                            		download.setFileName(values[i].toString());
//                            		download.setDownLoadAv(video.getAid());
//                            		//UserProxy.getLiteOrmInstance().save(download);
//                            		try {
//        								DownloadService.getDownloadManager().
//        								startDownload("http://www.bilibili.com/m/html5?aid="+video.getAid()+"&page="+1, values[i].toString(),"/sdcard/xUtils/" + values[i].toString() + ".MP4", true, false, null);
//        							} catch (DbException e) {
//        								e.printStackTrace();
//        							}
                        		}
                        	}
                        }
                    }
//                    startAnimActivity(UserResActivity.class);
                }
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        String[] str ;
        if(videoInfoList == null || videoInfoList.isEmpty()){
        	str = new String[]{video.getTitle()};
        }else{
        	str = new String[videoInfoList.size()];
        	for(int i = 0 ; i < videoInfoList.size() ;i++){
        		str[i] = videoInfoList.get(i).getTitle();
        	}
        }
        ((SimpleDialog.Builder)builder).multiChoiceItems(str).title("选择视频").positiveAction("确定").negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
	}
	
	public void getDownloadList(final String name,final String aid,final int page){
		Runnable run = new Runnable() {
			@Override
			public void run() {
				try {
					JSONObject videopathjson = new JSONObject(HttpProxy.getHtmlString("http://www.bilibili.com/m/html5?aid="+aid+"&page="+page));
					String mUri = videopathjson.getString("src").toString();
					try {
						DownloadService.getDownloadManager().
						startDownload(mUri, name,"/sdcard/xUtils/" +
								name + ".MP4", true, false, null);
					} catch (DbException e) {
						e.printStackTrace();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		};
		new Thread(run).start();
	}
	
	private class VideoInfoTask extends AsyncTask<String, Void, List<Video>> {

		@Override
		protected List<Video> doInBackground(String... params) {
			try {
				videoInfoList.clear();
				if(video.getUrlInfo() == null){
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
				}else{
					Document document = Jsoup.connect(video.getUrlInfo()).data("jquery", "java")
							.userAgent("Mozilla").cookie("auth", "token")
							.timeout(50000).get();
					Elements tags = document.select("div.nfo-row,.info-style");
					Elements taga = tags.select("a");
					String label = "";
					for(Element tag:taga){
						Log.i("tag", tag.text()+"=====");
						label = label+"  "+tag.text();
					}
					video.setSbutitle(label);
					Elements bangumi_info_r = document.select("div.bangumi-info-r");
					Elements info_descs = bangumi_info_r.select("div.info-row,.info-desc-wrp");
					Elements info = info_descs.select("div.info-desc");
					video.setDescription(info.text());
					Log.i("info", info.text()+"====");
					// 获取视频
					Elements episode_list_wrp = document.select("div.episode-list-wrp");
					Elements episode_list = episode_list_wrp.select("div.episode-list,.initialized,.ep-mode-cover");
//					Elements e_item_l = episode_list.select("div.e-item-l");
					Elements links = episode_list.select("a[href]");
					for(int i = 0;i< links.size();i++){
						Video item = new Video();
						Element link = links.get(i);
						item.setAid(link.attr("href").split("/")[2].replace("av",""));
						item.setTitle(link.attr("title"));
						videoInfoList.add(item);
						i++;
						Log.i("vedioList", link.attr("href").split("/")[2].replace("av","")+"====");
						Log.i("vedioList", link.attr("title")+"====");
					}
					// 相关推荐
					Elements page_video_wrp = document.select("div.page-video-wrp");
					Elements page_video_wrp_r = page_video_wrp.select("div.page-video-wrp-r");
					Elements r_item = page_video_wrp_r.select("div.page-video-wrp-r");
					Elements a_items = r_item.select("a[href]");
					List<Video> list = new ArrayList<Video>();
					for(int i = 0;i< a_items.size();i++){
						Video item = new Video();
						Element a_item = a_items.get(i);
						item.setAid(a_item.attr("href"));
						item.setTitle(a_item.attr("title"));
						Elements preview = page_video_wrp_r.select("div.preview");
						Element media = preview.select("[src]").get(0);
						Elements num = preview.get(0).getElementsByClass("num");
						item.setPic(media.attr("abs:data-img"));
						item.setTitle(a_item.attr("title"));
						item.setUrlInfo(Constant.URL_NEW_BANKUN_INFO+a_item.attr("href").replaceAll(" ", "%20"));
						item.setUpdateContent(num.text());
						list.add(item);
						Log.i("num", num.text()+"=====");
						Log.i("pic", media.attr("abs:data-img")+"=====");
						Log.i("url", a_item.attr("href")+"====");
						Log.i("title", a_item.attr("title")+"====");
					}
					video.setVideoList(list);
					// 相关视频
				}	
			} catch (IOException e) {
				e.printStackTrace();
				Log.e("IOException", e.toString()+"====");
				return null;
			}
			return videoInfoList;
		}
		
		@Override
		protected void onPostExecute(List<Video> result) {
			if(video.getUrlInfo() == null){
				if(result != null){
					btn_video_play.setText("点击播放");
				}else{
					btn_video_play.setText("加载失败");
				}
			}else{
				if(result != null && videoInfoList.size()!=0){
					btn_video_play.setText("播放01话");
				}else{
					btn_video_play.setText("加载失败");
				}
			}
			if(result != null){
				UserProxy.videoInfoList.clear();
				UserProxy.videoInfoList.addAll(videoInfoList);
//				adapter = new ScrollTabPagerdAdapter(getSupportFragmentManager(),video.null);
				pager.setOffscreenPageLimit(1);
				pager.setAdapter(adapter);
				tabLayout.setupWithViewPager(pager);
				pager.addOnPageChangeListener(getViewPagerPageChangeListener());
			}
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
	
	private ViewPager.OnPageChangeListener getViewPagerPageChangeListener() {
		ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int state) {}

			@Override
			public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
//				Log.i("onPageScrolled","onPageScrolled===");
//				int currentItem = pager.getCurrentItem();
//				if (positionOffsetPixels > 0) {
//					SparseArrayCompat<ScrollTabHolder> scrollTabHolders = adapter.getScrollTabHolders();
//					ScrollTabHolder fragmentContent;
//					if (position < currentItem) {
//						fragmentContent = scrollTabHolders.valueAt(position);
//					} else {
//						fragmentContent = scrollTabHolders.valueAt(position + 1);
//					}
//					Log.i(TAG, "header height " + top_view.getHeight() +
//							 " translationY " + top_view.getTranslationY());
//					fragmentContent.adjustScroll((int) (top_view.getHeight() + top_view.getTranslationY()), top_view.getHeight());
//				}
			}

			@Override
			public void onPageSelected(int position) {
//				Log.i("onPageSelected","onPageSelected===");
//				SparseArrayCompat<ScrollTabHolder> scrollTabHolders = adapter.getScrollTabHolders();
//				if (scrollTabHolders == null|| scrollTabHolders.size() != adapter.getCount()) {
//					return;
//				}
//				ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
//				currentHolder.adjustScroll((int) (top_view.getHeight() + top_view.getTranslationY()), top_view.getHeight());
			}
			
		};
		return listener;
	}

	@Override
	public void adjustScroll(int scrollHeight, int headerHeight) {}

	@Override
	public void onListViewScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount, int pagePosition) {
		if (pager.getCurrentItem() == pagePosition) {
			scrollHeader(getScrollY(view));
		}
	}

	@Override
	public void onScrollViewScroll(ScrollView view, int x, int y, int oldX,int oldY, int pagePosition) {
		if (pager.getCurrentItem() == pagePosition) {
			scrollHeader(view.getScrollY());
		}
	}
	
	private int getScrollY(AbsListView view) {
		View child = view.getChildAt(0);
		if (child == null) {
			return 0;
		}

		int firstVisiblePosition = view.getFirstVisiblePosition();
		int top = child.getTop();
		int headerHeight = 0; // 50
		if (firstVisiblePosition >= 1) {
			headerHeight = top_view.getHeight();
		}
		
		return -top + firstVisiblePosition * child.getHeight() + headerHeight;
	}
	
	private void scrollHeader(int scrollY) {
		float translationY = Math.max(-scrollY, -top_view.getHeight()+tabLayout.getHeight());
		Log.i("scrollHeader", scrollY+"=========="+translationY);
		top_view.setTranslationY(translationY);
		// mTopImage.setTranslationY(-translationY / 3);
	}

}
