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
			Intent intent = new Intent(mContext, VideoPlayActivity.class);
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

}
