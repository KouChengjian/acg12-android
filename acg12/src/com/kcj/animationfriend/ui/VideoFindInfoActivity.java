package com.kcj.animationfriend.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
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

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.FindScrollTabPagerdAdapter;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.view.ScrollTabHolder;
import com.liteutil.util.Log;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/**
 * @ClassName: VideoFindInfoActivity
 * @Description: 番剧信息
 * @author: KCJ
 * @date: 2016-08-18 10:20
 */
public class VideoFindInfoActivity extends BaseActivity implements ScrollTabHolder , OnClickListener ,ParameCallBack {

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;

	@InjectView(R.id.top_view)
	protected LinearLayout top_view;
	@InjectView(R.id.ll_info_header)
	protected RelativeLayout ll_info_header;
	@InjectView(R.id.pager)
	protected ViewPager pager;
	@InjectView(R.id.indicator)
	protected TabLayout tabLayout;
	protected FindScrollTabPagerdAdapter adapter;
	
	protected View header;
	protected ImageView iv_video_icon;
	protected TextView tv_video_title;
	protected TextView tv_video_author;
	protected TextView tv_video_playnum;
	protected TextView tv_video_review;
	protected TextView tv_video_time;
	protected Button btn_video_play;
	protected Video video;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_video_findinfo);
		video = (Video) getIntent().getSerializableExtra("videoItemdata");
		initViews();
		initEvent();
		initDatas();
	}

	@SuppressLint("InflateParams")
	@Override
	public void initViews() {
		super.initViews();
		setTitle(R.string.bankun_info);
		setSupportActionBar(toolbar);
		
		header = mInflater.inflate(R.layout.include_video_info_2, null);
		iv_video_icon = (ImageView)header.findViewById(R.id.iv_video_icon);
		tv_video_title = (TextView)header.findViewById(R.id.tv_video_title);
		tv_video_playnum = (TextView)header.findViewById(R.id.tv_video_playnum);
		tv_video_review = (TextView)header.findViewById(R.id.tv_video_review);
		tv_video_time = (TextView)header.findViewById(R.id.tv_video_time); // 这里显示更新时间
		btn_video_play = (Button)header.findViewById(R.id.btn_video_play);
        ll_info_header.addView(header);
		
		adapter = new FindScrollTabPagerdAdapter(getSupportFragmentManager(),video , this);
		pager.setOffscreenPageLimit(1);
		pager.setAdapter(adapter);
		tabLayout.setupWithViewPager(pager);
		pager.addOnPageChangeListener(getViewPagerPageChangeListener());
	}
	
	@Override
	public void initEvent() {
		btn_video_play.setOnClickListener(this);
	}
	
	@Override
	public void initDatas() {
		if(video != null){
			ImageLoader.getInstance().displayImage(video.getPic(), iv_video_icon, MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener(){});
			tv_video_title.setText(video.getTitle());
			
			btn_video_play.setClickable(false);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public void adjustScroll(int scrollHeight, int headerHeight) {}

	@Override
	public void onListViewScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount, int pagePosition) {
		if (pager.getCurrentItem() == pagePosition) {
			scrollHeader(getScrollY(view));
		}
	}

	@Override
	public void onScrollViewScroll(ScrollView view, int x, int y, int oldX,
			int oldY, int pagePosition) {
		if (pager.getCurrentItem() == pagePosition) {
			scrollHeader(view.getScrollY());
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_video_play){
			Intent intent = new Intent(mContext, VideoPlayActivity1.class);
			intent.putExtra("displayName",video.getTitle());
			intent.putExtra("av",video.getAid());
			intent.putExtra("page","1");
			startActivity(intent);
		}
	}
	
	@Override
	public void onCall(Object object) {
		if(object instanceof Video){
			video = (Video)object;
			if(video.getAid() != null && !video.getAid().isEmpty()){
				btn_video_play.setText("播放01话");
				btn_video_play.setClickable(true);
				btn_video_play.setSelected(false);
			}else{
				btn_video_play.setText("加载失败");
				btn_video_play.setClickable(false);
				btn_video_play.setSelected(true);
			}
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
	
	private ViewPager.OnPageChangeListener getViewPagerPageChangeListener() {
		ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int state) {}

			@Override
			public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
				Log.i("onPageScrolled","onPageScrolled===");
				int currentItem = pager.getCurrentItem();
				if (positionOffsetPixels > 0) {
					SparseArrayCompat<ScrollTabHolder> scrollTabHolders = adapter.getScrollTabHolders();
					ScrollTabHolder fragmentContent;
					if (position < currentItem) {
						fragmentContent = scrollTabHolders.valueAt(position);
					} else {
						fragmentContent = scrollTabHolders.valueAt(position + 1);
					}
					Log.i(TAG, "header height " + top_view.getHeight() +
							 " translationY " + top_view.getTranslationY());
					fragmentContent.adjustScroll((int) (top_view.getHeight() + top_view.getTranslationY()), top_view.getHeight());
				}
			}

			@Override
			public void onPageSelected(int position) {
				Log.i("onPageSelected","onPageSelected===");
				SparseArrayCompat<ScrollTabHolder> scrollTabHolders = adapter.getScrollTabHolders();
				if (scrollTabHolders == null|| scrollTabHolders.size() != adapter.getCount()) {
					return;
				}
				ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
				currentHolder.adjustScroll((int) (top_view.getHeight() + top_view.getTranslationY()), top_view.getHeight());
			}
			
		};
		return listener;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

//	private class VideoInfoTask extends AsyncTask<String, Void, List<Video>> {
//
//		@Override
//		protected List<Video> doInBackground(String... arg0) {
//			List<Video> videoList = new ArrayList<Video>();
//			try {
//				Document document = Jsoup.connect(video.getUrlInfo())
//						.data("jquery", "java").userAgent("Mozilla")
//						.cookie("auth", "token").timeout(50000).get();
//				Log.e("document", document.toString() + "=====");
//				Elements tags = document.select("div.nfo-row,.info-style");
//				Elements taga = tags.select("a");
//				String label = "";
//				for (Element tag : taga) {
//					Log.e("tag", tag.text() + "=====");
//					label = label + "  " + tag.text();
//				}
//				video.setSbutitle(label);
//				Elements bangumi_info_r = document.select("div.bangumi-info-r");
//				Elements info_descs = bangumi_info_r
//						.select("div.info-row,.info-desc-wrp");
//				Elements info = info_descs.select("div.info-desc");
//				video.setDescription(info.text());
//				Log.e("info", info.text() + "====");
//				// 获取视频
//				Elements episode_list_wrp = document.select("div.episode-list-wrp");
//				Elements episode_list = episode_list_wrp
//						.select("div.episode-list,.initialized,.ep-mode-cover");
//				Elements links = episode_list.select("a[href]");
//				for (int i = 0; i < links.size(); i++) {
//					Video item = new Video();
//					Element link = links.get(i);
//					item.setAid(link.attr("href").split("/")[2].replace("av", ""));
//					item.setTitle(link.attr("title"));
//					videoList.add(item);
//					i++;
//					Log.e("vedioList",
//							link.attr("href").split("/")[2].replace("av", "")
//									+ "====");
//					Log.e("vedioList", link.attr("title") + "====");
//				}
//				// 相关推荐
//				Elements page_video_wrp = document.select("div.page-video-wrp");
//				Elements page_video_wrp_r = page_video_wrp
//						.select("div.page-video-wrp-r");
//				Elements r_item = page_video_wrp_r.select("div.page-video-wrp-r");
//				Elements a_items = r_item.select("a[href]");
//				List<Video> list = new ArrayList<Video>();
//				for (int i = 0; i < a_items.size(); i++) {
//					Video item = new Video();
//					Element a_item = a_items.get(i);
//					item.setAid(a_item.attr("href"));
//					item.setTitle(a_item.attr("title"));
//					Elements preview = page_video_wrp_r.select("div.preview");
//					Element media = preview.select("[src]").get(0);
//					Elements num = preview.get(0).getElementsByClass("num");
//					item.setPic(media.attr("abs:data-img"));
//					item.setTitle(a_item.attr("title"));
//					item.setUrlInfo(Constant.URL_NEW_BANKUN_INFO
//							+ a_item.attr("href").replaceAll(" ", "%20"));
//					item.setUpdateContent(num.text());
//					list.add(item);
//					Log.e("num", num.text() + "=====");
//					Log.e("pic", media.attr("abs:data-img") + "=====");
//					Log.e("url", a_item.attr("href") + "====");
//					Log.e("title", a_item.attr("title") + "====");
//				}
//				video.setVideoList(list);
//			} catch (IOException e) {
//				Log.e("IOException", e.toString());
//				e.printStackTrace();
//			}
//			return videoList;
//		}
//		
//		@Override
//		protected void onPostExecute(List<Video> result) {
//			super.onPostExecute(result);
//			Log.e("TAG", result.size());
//			if(result != null && result.size()!=0){
//				btn_video_play.setText("播放01话");
//			}else{
//				btn_video_play.setText("加载失败");
//			}
//			videoList.addAll(result);
//			bankunAdapter.notifyDataSetChanged();
//			
//		}
//
//	}
}
