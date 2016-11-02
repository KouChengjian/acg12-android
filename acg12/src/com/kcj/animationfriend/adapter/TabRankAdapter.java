package com.kcj.animationfriend.adapter;

import com.kcj.animationfriend.ui.fragment.TabRankFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @ClassName: TabRankAdapter
 * @Description: 排行榜适配器
 * @author: KCJ
 * @date: 
 */
public class TabRankAdapter extends FragmentPagerAdapter {

	// 内容标题
	public static final String[] DONG_HUA_TITLE = new String[] {"新番","动画", "音乐", "娱乐", "鬼畜" };
	
	public TabRankAdapter(FragmentManager fm) {
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
		switch (position) {
		case 0:
			return new TabRankFragment(1); // 新番
		case 1:
			return new TabRankFragment(2); // 动画
		case 2:
			return new TabRankFragment(3); // 音乐
		case 3:
			return new TabRankFragment(4); // 娱乐
		default:
			return new TabRankFragment(5); // 鬼畜
		}
	}
}
