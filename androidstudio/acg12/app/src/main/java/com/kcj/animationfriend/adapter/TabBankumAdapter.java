package com.kcj.animationfriend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kcj.animationfriend.ui.fragment.TabBankumFragment;

/**
 * @ClassName: TabBankumAdapter
 * @Description: 番剧
 * @author: KCJ
 * @date:
 */
public class TabBankumAdapter extends FragmentPagerAdapter {

	// 内容标题
	public static final String[] DONG_HUA_TITLE = new String[] {  "连载动画", // "推荐",
			"完结动画", "资讯", "官方延伸", "国产动画" };

	public TabBankumAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// 返回页面标题
		return DONG_HUA_TITLE[position % DONG_HUA_TITLE.length].toUpperCase();
//		switch (position) {
//		case 0:
//			return "连载动画";
//		default:
//			return "资讯";
//		}
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
			return new TabBankumFragment(1); // 连载动画
		case 1:
			return new TabBankumFragment(2); // 完结动画
		case 2:
			return new TabBankumFragment(3); // 资讯
		case 3:
			return new TabBankumFragment(4); // 官方延伸
		default:
			return new TabBankumFragment(5); // 国产动画
		}
	}
	
}
