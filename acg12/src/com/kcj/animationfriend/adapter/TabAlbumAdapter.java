package com.kcj.animationfriend.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.ui.fragment.TabAlbumFragment;

/**
 * @ClassName: TabAlbumAdapter
 * @Description: 图片适配器
 * @author: KCJ
 * @date: 
 */
public class TabAlbumAdapter extends FragmentPagerAdapter{

	// 内容标题
	public static final String[] DONG_HUA_TITLE = new String[] {"最新"};
	public Palette palette;
	
	public TabAlbumAdapter(FragmentManager fm , Palette palette) {
		super(fm);
		this.palette = palette;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// 返回页面标题
		return DONG_HUA_TITLE[position % DONG_HUA_TITLE.length].toUpperCase();
	}

	@Override
	public int getCount() {
		// 页面个数
		return DONG_HUA_TITLE.length;
	}

	// 获取项
	@Override
	public Fragment getItem(int position) {
		TabAlbumFragment albumFragment = new TabAlbumFragment();
		Bundle bundle = new Bundle();
        bundle.putSerializable("palette", palette);
        albumFragment.setArguments(bundle);
		return albumFragment;
	}
}
