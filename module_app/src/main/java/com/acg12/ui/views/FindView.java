package com.acg12.ui.views;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.ui.adapter.HomePagerAdapter;
import com.acg12.ui.fragment.TabAlbumFragment;
import com.acg12.ui.fragment.TabAnimatFragment;
import com.acg12.ui.fragment.TabBangumiFragment;
import com.acg12.ui.fragment.TabPaletteFragment;
import com.acg12.widget.SearchPopWindow;

import com.acg12.R;
import com.acg12.ui.adapter.HomePagerAdapter;
import com.acg12.ui.fragment.TabAlbumFragment;
import com.acg12.ui.fragment.TabAnimatFragment;
import com.acg12.ui.fragment.TabBangumiFragment;
import com.acg12.ui.fragment.TabPaletteFragment;
import com.acg12.widget.SearchPopWindow;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/1/15.
 */

public class FindView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.home_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.home_viewpager)
    ViewPager mViewpager;

    Fragment[] fragments;
    HomePagerAdapter mainPagerAdapter;
    TabAlbumFragment tabAlbumFragment;
    TabPaletteFragment tabPaletteFragment;
    TabBangumiFragment tabBangumiFragment;
    TabAnimatFragment tabMADAMVFragment;
    TabAnimatFragment tabMMD3DFragment;
    TabAnimatFragment tabChatFragment;

    SearchPopWindow searchPopWindow;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_home);
        toolbar.inflateMenu(R.menu.menu_main);

        tabAlbumFragment = TabAlbumFragment.newInstance("");
        tabPaletteFragment = TabPaletteFragment.newInstance();
        tabBangumiFragment = TabBangumiFragment.newInstance();
        tabMADAMVFragment = TabAnimatFragment.newInstance(0);
        tabMMD3DFragment = TabAnimatFragment.newInstance(1);
        tabChatFragment = TabAnimatFragment.newInstance(2);
        fragments = new Fragment[]{tabAlbumFragment, tabPaletteFragment, tabBangumiFragment, tabMADAMVFragment, tabMMD3DFragment, tabChatFragment};

        mainPagerAdapter = new HomePagerAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(mainPagerAdapter);
        mViewpager.setOffscreenPageLimit(fragments.length);
        mTabLayout.setupWithViewPager(mViewpager);

        searchPopWindow = new SearchPopWindow((AppCompatActivity) getContext());
        searchPopWindow.setOnPopupShowOrDismiss((SearchPopWindow.OnPopupShowOrDismiss) mPresenter);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolbar);
        toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) mPresenter);
    }

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    public void showPopupWindow() {
        searchPopWindow.showPopupWindow(toolbar);
    }

    public void dismissPopupWindow() {
        searchPopWindow.dismissPopupWindow();
    }
}
