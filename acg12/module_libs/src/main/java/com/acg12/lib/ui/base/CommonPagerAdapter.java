package com.acg12.lib.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/11/9.
 */
public class CommonPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private String[] DONG_HUA_TITLE = new String[]{};

    public CommonPagerAdapter(FragmentManager fm, Fragment[] fragments , String[] DONG_HUA_TITLE ) {
        super(fm);
        this.fragments = fragments;
        this.DONG_HUA_TITLE = DONG_HUA_TITLE;
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
