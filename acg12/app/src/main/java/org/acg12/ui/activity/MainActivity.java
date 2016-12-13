package org.acg12.ui.activity;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.kcj.animationfriend.R;

import org.acg12.ui.adapter.MainPagerAdapter;
import org.acg12.ui.base.BaseActivity;
import org.acg12.ui.fragment.HomeFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewpager)
    ViewPager mViewpager;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    Fragment[] fragments;
    MainPagerAdapter mainPagerAdapter;
    HomeFragment homeFragment;
    HomeFragment homeFragment1;
    HomeFragment homeFragment2;
    HomeFragment homeFragment3;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        initViews();
        initEvent();
        initDatas();
    }

    @Override
    public void initViews() {
        super.initViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer,
                R.string.closedrawer);
        drawerLayout.setDrawerListener(drawerToggle);

        homeFragment = new HomeFragment();
        homeFragment1 = new HomeFragment();
        homeFragment2 = new HomeFragment();
        homeFragment3 = new HomeFragment();

        fragments = new Fragment[]{homeFragment , homeFragment1 ,homeFragment2 ,homeFragment3};
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager() , fragments);
        mViewpager.setAdapter(mainPagerAdapter);
        mViewpager.setOffscreenPageLimit(fragments.length);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
