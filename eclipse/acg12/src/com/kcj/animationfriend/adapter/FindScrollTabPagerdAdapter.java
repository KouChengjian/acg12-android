package com.kcj.animationfriend.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.fragment.VideoFindInfoIntroduceFragment;
import com.kcj.animationfriend.ui.fragment.VideoFindInfoRelatedFragment;
import com.kcj.animationfriend.view.ScrollTabHolder;

public class FindScrollTabPagerdAdapter extends FragmentPagerAdapter {

	public Video video;
	public ParameCallBack parameCallBack;
	public SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
	public static final String[] DONG_HUA_TITLE = new String[] { "视频详情"};

	public FindScrollTabPagerdAdapter(FragmentManager fm,Video video ,ParameCallBack parameCallBack) {
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
		case 0:
			VideoFindInfoIntroduceFragment pinnePagerdFragment = (VideoFindInfoIntroduceFragment) VideoFindInfoIntroduceFragment.newInstance(parameCallBack);
			Bundle bundle = new Bundle();
	        bundle.putSerializable("video", video);
	        pinnePagerdFragment.setArguments(bundle);
			return pinnePagerdFragment; 
		default:
			VideoFindInfoRelatedFragment pinnePagerdVideoFragment = new VideoFindInfoRelatedFragment();
			Bundle bundle1 = new Bundle();
	        bundle1.putSerializable("video", video);
	        pinnePagerdVideoFragment.setArguments(bundle1);
			return pinnePagerdVideoFragment;  
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
