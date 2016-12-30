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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import org.acg12.R;

import org.acg12.ui.adapter.MainPagerAdapter;
import org.acg12.ui.base.BaseActivity;
import org.acg12.ui.fragment.HomeFragment;
import org.acg12.ui.fragment.TabAlbumFragment;
import org.acg12.ui.fragment.TabAnimatFragment;
import org.acg12.ui.fragment.TabBangumiFragment;
import org.acg12.ui.fragment.TabPaletteFragment;
import org.acg12.utlis.PixelUtil;
import org.acg12.utlis.SystemBarTintManager;
import org.acg12.utlis.SystemBarUtlis;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener ,NavigationView.OnNavigationItemSelectedListener{

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
    TabAnimatFragment tabMADAMVFragment;
    TabAnimatFragment tabMMD3DFragment;
    TabAnimatFragment tabChatFragment;

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

        tabAlbumFragment = TabAlbumFragment.newInstance("");
        tabPaletteFragment = new TabPaletteFragment();
        tabBangumiFragment = new TabBangumiFragment();
        tabMADAMVFragment = TabAnimatFragment.newInstance(0);
        tabMMD3DFragment = TabAnimatFragment.newInstance(1);
        tabChatFragment = TabAnimatFragment.newInstance(2);
        fragments = new Fragment[]{tabAlbumFragment , tabPaletteFragment ,tabBangumiFragment ,tabMADAMVFragment ,tabMMD3DFragment, tabChatFragment };

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
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()){
            case R.id.nav_star:
                break;
            case R.id.nav_down:
                break;
            case R.id.nav_history:
                break;
            case R.id.nav_color_lens:
                startAnimActivity(SkinLoaderActivity.class);
                break;
            case R.id.nav_settings:
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
