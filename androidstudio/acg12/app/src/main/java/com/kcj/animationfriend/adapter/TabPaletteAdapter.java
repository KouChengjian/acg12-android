package com.kcj.animationfriend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kcj.animationfriend.ui.fragment.TabPaletteFragment;

/**
 * @ClassName: TabPaletteAdapter
 * @Description: 画板
 * @author: KCJ
 * @date: 
 */
public class TabPaletteAdapter extends FragmentPagerAdapter{

	// 内容标题
	public static final String[] DONG_HUA_TITLE = new String[] {"最新"};
	
	public TabPaletteAdapter(FragmentManager fm) {
		super(fm);
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
		return new TabPaletteFragment();
	}

}
