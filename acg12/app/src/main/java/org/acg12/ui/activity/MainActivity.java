package org.acg12.ui.activity;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.acg12.R;

import org.acg12.ui.adapter.MainPagerAdapter;
import org.acg12.ui.base.BaseActivity;
import org.acg12.ui.fragment.HomeFragment;
import org.acg12.ui.fragment.TabAlbumFragment;
import org.acg12.ui.fragment.TabBangumiFragment;
import org.acg12.ui.fragment.TabPaletteFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener{

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
    TabAlbumFragment tabAlbumFragment;
    TabPaletteFragment tabPaletteFragment;
    TabBangumiFragment tabBangumiFragment;
    HomeFragment homeFragment3;

    public static long firstTime;

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
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer, R.string.closedrawer);

        tabAlbumFragment = new TabAlbumFragment();
        tabPaletteFragment = new TabPaletteFragment();
        tabBangumiFragment = new TabBangumiFragment();
        homeFragment3 = new HomeFragment();

        fragments = new Fragment[]{tabAlbumFragment , new HomeFragment() ,new HomeFragment() ,new HomeFragment()};
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager() , fragments);
        mViewpager.setAdapter(mainPagerAdapter);
        mViewpager.setOffscreenPageLimit(fragments.length);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        drawerLayout.addDrawerListener(drawerToggle);
        mToolbar.setOnMenuItemClickListener(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggleDrawer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_search:

                break;
        }
        return false;
    }

    private void toggleDrawer() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            ShowToast(R.string.double_click_logout);
        }
        firstTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
