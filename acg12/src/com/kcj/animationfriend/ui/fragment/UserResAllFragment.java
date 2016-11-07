package com.kcj.animationfriend.ui.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.WeakFragmentPagerAdapter;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.view.photo.zoom.ViewPagerFixed;

/**
 * @ClassName: ResourceFragment
 * @Description: 资源
 * @author: KCJ
 * @date:   
 */
public class UserResAllFragment extends BaseFragment implements OnClickListener{
	
	@InjectView(R.id.rl_personal_tab)
	protected LinearLayout rl_personal_tab; // 资源
	@InjectView(R.id.indicator)
    protected TabLayout tabs;
    @InjectView(R.id.vp_personal_pager)
    protected ViewPagerFixed pager;
    private MyPagerAdapter adapter;
    private Fragment[] fragments;
    //private UserResDLFragment   userResDLFragment;
    //private UserResULFragment   userResULFragment;
    private UserResPaleFragment userResPaleFragment;
    private UserResCltFragment  userResCltFragment;
    
    private User user;
    public static ParameCallBack parameCallBack;
    
    public static BaseFragment newInstance(User user ,ParameCallBack callBack) {
    	parameCallBack = callBack;
    	BaseFragment fragment = new UserResAllFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("user", user);
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
		user = (User)getArguments().getSerializable("user");
		initViews();
		initEvent();
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public void initViews(){
        //userResDLFragment   = (UserResDLFragment)  UserResDLFragment.newInstance(user);  // 下载
        //userResULFragment   = (UserResULFragment)  UserResULFragment.newInstance(user);  // 上传
        userResCltFragment  = (UserResCltFragment) UserResCltFragment.newInstance(user); // 采集
        userResPaleFragment = (UserResPaleFragment)UserResPaleFragment.newInstance(user);// 画板
        // userResDLFragment,userResULFragment,
		fragments = new Fragment[] { userResCltFragment ,userResPaleFragment};
		adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
		pager.setOffscreenPageLimit(fragments.length);
		pager.setAdapter(adapter);
		tabs.setupWithViewPager(pager);
		tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
	}
	
	@Override
	public void initEvent() {
		// pager.setScanScroll(false);
		pager.addOnPageChangeListener(getViewPagerPageChangeListener());
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {}
	}	
	
	public class MyPagerAdapter extends WeakFragmentPagerAdapter {

        private final String[] TITLES = {"采集收藏","我的图集"  }; //,"上传列表""下载列表",

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
			public void onPageSelected(int position) {
				parameCallBack.onCall(position);
			}
			
		};
		return listener;
	}
}
