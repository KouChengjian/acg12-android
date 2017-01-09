package org.acg12.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DELL on 2017/1/6.
 */
public class SearchPagerAdapter  extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private final String[] DONG_HUA_TITLE = new String[] {"图片","画集","番剧" ,"动画"};

    public SearchPagerAdapter(FragmentManager fm , Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DONG_HUA_TITLE[position % DONG_HUA_TITLE.length].toUpperCase();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return DONG_HUA_TITLE.length;
    }
}
