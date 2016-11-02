package com.kcj.animationfriend.ui.fragment;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.InjectView;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.HomeAdapter;
import com.kcj.animationfriend.bean.Area;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.listener.HttpRequestListener;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.util.Network;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.view.RefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @ClassName: HomeFragment
 * @Description: 主界面
 * @author: KCJ
 * @date: 2015-9-20
 */
public class HomeFragment extends BaseFragment implements OnRefreshListener{
	
	// 横幅
	protected int position;
	protected ViewPager bannerViewPager;
	protected LinearLayout llIndicate;
	protected List<View> viewList = new ArrayList<View>();
	protected List<Video> bannerList = new ArrayList<Video>();
	protected ScheduledExecutorService scheduledExecutorService;
	@InjectView(R.id.rl_refresh)
	protected RefreshLayout mRefreshLayout;
	@InjectView(R.id.lv_home)
	protected ListView listView;
	protected HomeAdapter homeAdapter;
	protected List<Area> areaList = new ArrayList<Area>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
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
		View llLayout = mInflater.inflate(R.layout.include_home_banner, listView,false);
		llIndicate = (LinearLayout)llLayout.findViewById(R.id.ll_indicate );
		bannerViewPager = (ViewPager)llLayout.findViewById(R.id.vp_home_banner);
        homeAdapter = new HomeAdapter(getActivity(), areaList);
        listView.addHeaderView(llLayout);
        listView.setAdapter(homeAdapter);
        mRefreshLayout.setEbLoading(false);
        mRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
        mRefreshLayout.setRefreshing(true);
	}
	
	@Override
	public void initEvent() {
		bannerViewPager.addOnPageChangeListener(new BannerChangeListener());
		mRefreshLayout.setOnRefreshListener(this);
	}
	
	@Override
	public void initDatas() {
		if(!Network.isConnected(mContext)){
			mRefreshLayout.setRefreshing(false);
			ShowToast("请链接网络~~~");
			return;
		}
		refresh();
	}
	
	
	
	@Override
	public void onRefresh() {
		areaList.clear();
		refresh();
	}
	
	public void refresh() {
		HttpProxy.getHomeContent(new HttpRequestListener<Area>() {
			@Override
			public void onSuccess(List<Area> result) {
				if (result.size() != 0 && result.get(result.size() - 1) != null) {
					Area banner = result.get(0);
					List<Video> list = banner.getObjectList();
					viewList.clear();
					bannerList.clear();
					llIndicate.removeAllViews();
					if(list == null){
						return;
					}
					for (int i = 0; i < list.size(); i++) {
						View dot = (View)  mInflater.inflate(R.layout.item_brand_indicate, null);
						llIndicate.addView(dot);
						viewList.add(dot);
					}
					if(viewList.size() > 0){
						viewList.get(0).setSelected(true);
						scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
						// 当Activity显示出来后，每两秒切换一次图片显示
						scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 10,TimeUnit.SECONDS);
					}
					bannerList.addAll(list);
					bannerViewPager.setAdapter(new BannerAdapter());
					result.remove(banner);
					areaList.addAll(result);
					homeAdapter.notifyDataSetChanged();
					mRefreshLayout.setRefreshing(false);
				}
			}

			@Override
			public void onFailure(String msg) {
				ShowToast(msg);
				Log.e("onFailure",msg+"");
				mRefreshLayout.setRefreshing(false);
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * @ClassName: BannerChangeListener
	 * @Description: 横幅滑动监听
	 * @author: KCJ
	 * @date:
	 */
	private class BannerChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}

		@Override
		public void onPageSelected(int position) {
			HomeFragment.this.position = position;
			for (int i = 0; i < bannerList.size(); i++) {
				if (i == position) {
                    if(viewList.get(i) != null){
                    	viewList.get(i).setSelected(true);
					}
				} else {
                    if(viewList.get(i) != null){
                    	viewList.get(i).setSelected(false);
					}
				}
			}
		}
	}
	
	private class ScrollTask implements Runnable {

		@Override
		public void run() {
			synchronized (bannerViewPager) {
				position = (position + 1) % viewList.size();
				handler.obtainMessage().sendToTarget();
			}
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			bannerViewPager.setCurrentItem(position);
			for (int i = 0; i < bannerList.size(); i++) {
				if (i == position) {
                    if(viewList.get(i) != null){
                    	viewList.get(i).setSelected(true);
					}
				} else {
                    if(viewList.get(i) != null){
                    	viewList.get(i).setSelected(false);
					}
				}
			}
		};
	};
	
	/**
	 * @ClassName: BannerAdapter
	 * @Description: 横幅适配器
	 * @author: KCJ
	 * @date: 
	 */
	private class BannerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return bannerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			ImageView iv = new ImageView(mContext);
			ImageLoader.getInstance().displayImage(bannerList.get(position).getPic(),iv,
					MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(String imageUri,View view, Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
				}
			});
			((ViewPager) container).addView(iv);
			// 在这个方法里面设置图片的点击事件
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 处理跳转逻辑
//					 Banner item = (Banner) bannerList.get(position);
//					 Intent i = new Intent();
//					 i.setClass(getActivity(), WebInfoActivity.class);
//					 i.putExtra("bannerLink", "www.bilibili.com");
//					 startActivity(i);
					// Log.e("position", "" + position);
				}
			});
			return iv;
		}
		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {}

		@Override
		public void finishUpdate(View arg0) {}
	}
}
