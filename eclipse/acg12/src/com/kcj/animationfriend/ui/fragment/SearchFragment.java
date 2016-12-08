package com.kcj.animationfriend.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.WeakFragmentPagerAdapter;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.view.photo.zoom.ViewPagerFixed;


/**
 * @ClassName: SearchFragment
 * @Description: 搜索
 * @author: KCJ
 * @date: 2015-11-26  15：04 
 */
public class SearchFragment extends BaseFragment{

	@InjectView(R.id.indicator)
    protected TabLayout tabs;
    @InjectView(R.id.vp_personal_pager)
    protected ViewPagerFixed pager;
    private MyPagerAdapter adapter;
    private Fragment[] fragments;
    protected SearchAlbumFragment searchAlbumFragment;
    protected SearchPaletteFragment searchPaletteFragment;
	protected SearchBangunFragment searchSeriesFragment;
	protected SearchVideoFragment searchVideoFragment;
	protected String title;
	
	public static BaseFragment newInstance(String title ) {
    	BaseFragment fragment = new SearchFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("title", title);
    	fragment.setArguments(args);
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_resource, container, false);
		setContentView(view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		title = getArguments().getString("title");
		initViews();
		initEvent();
	}
	
	@Override
	public void initViews() {
		super.initViews();
		searchAlbumFragment   = (SearchAlbumFragment)SearchAlbumFragment.newInstance(title);
		searchPaletteFragment = (SearchPaletteFragment)SearchPaletteFragment.newInstance(title);
		searchSeriesFragment  = (SearchBangunFragment)SearchBangunFragment.newInstance(title); // 搜索系列
		searchVideoFragment   = (SearchVideoFragment) SearchVideoFragment.newInstance(title);  // 搜索视频
		fragments = new Fragment[] { searchAlbumFragment , searchPaletteFragment , searchVideoFragment,searchSeriesFragment };
		adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
		pager.setOffscreenPageLimit(fragments.length);
		pager.setAdapter(adapter);
		tabs.setupWithViewPager(pager);
		tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
	}
	
	@Override
	public void initEvent() {
		super.initEvent();
		pager.addOnPageChangeListener(getViewPagerPageChangeListener());
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	public class MyPagerAdapter extends WeakFragmentPagerAdapter {

        private final String[] TITLES = {"  图片  ","  图集  " ,"  视频  ","  番剧  "  }; 

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }
    }
	
	private ViewPager.OnPageChangeListener getViewPagerPageChangeListener() {
		ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int state) {}

			@Override
			public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {}

			@Override
			public void onPageSelected(int position) {}
			
		};
		return listener;
	}
}
