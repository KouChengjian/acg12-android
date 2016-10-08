package com.kcj.animationfriend.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.fragment.VideoHomeInfoIntroduceFragment;
import com.kcj.animationfriend.view.ScrollTabHolder;

public class HomeScrollTabPagerdAdapter extends FragmentPagerAdapter {

	private Video video;
	private ParameCallBack parameCallBack;
	private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
	private static final String[] DONG_HUA_TITLE = new String[] { "视频详情"};

	public HomeScrollTabPagerdAdapter(FragmentManager fm,Video video ,ParameCallBack parameCallBack) {
		super(fm);
		this.video = video;
		this.parameCallBack = parameCallBack;
		mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
	}

	public void init(){
		
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// 返回页面标题
        if(video.getUrlInfo() == null){
        	return DONG_HUA_TITLE[position % DONG_HUA_TITLE.length].toUpperCase();
		}else{
			return DONG_HUA_TITLE[position % DONG_HUA_TITLE.length].toUpperCase();
		}
		
	}

	@Override
	public int getCount() {
		// 页面个数
        if(video.getUrlInfo() == null){
        	return DONG_HUA_TITLE.length;
		}else{
			return DONG_HUA_TITLE.length;
		}
	}

	// 获取项
	@Override
	public Fragment getItem(int position) {
		switch (position) {
		default:
			VideoHomeInfoIntroduceFragment pinnePagerdFragment = (VideoHomeInfoIntroduceFragment) VideoHomeInfoIntroduceFragment.newInstance(parameCallBack);
			Bundle bundle = new Bundle();
	        bundle.putSerializable("video", video);
	        pinnePagerdFragment.setArguments(bundle);
			return pinnePagerdFragment; 
		}
	}

	public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
		return mScrollTabHolders;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Object object = super.instantiateItem(container, position);
		mScrollTabHolders.put(position, (ScrollTabHolder) object);
		return object;
	}
	
}
