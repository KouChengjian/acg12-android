package com.kcj.animationfriend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kcj.animationfriend.ui.fragment.TabDonghuaFragment;


/**
 * @ClassName: TabDonghuaAdapter
 * @Description: 动画
 * @author: KCJ
 * @date:
 */
public class TabDonghuaAdapter extends FragmentPagerAdapter{

	// 内容标题
	public static final String[] DONG_HUA_TITLE = new String[] {  "MAD·AMV", //"推荐",
				"MMD·3D", "动画短片", "综合" };
		
	public TabDonghuaAdapter(FragmentManager fm) {
		super(fm);
	}

	// 获取项
	@Override
	public Fragment getItem(int position) {
		switch (position) {
//		case 0:
//			return new TabDonghuaFragment(1); // 推荐
		case 0:
			return new TabDonghuaFragment(1); // MAD·AMV
		case 1:
			return new TabDonghuaFragment(2); // MMD·3D
		case 2:
			return new TabDonghuaFragment(3); // 动画短片
		default:
			return new TabDonghuaFragment(4); // 综合
		}
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
	
}
