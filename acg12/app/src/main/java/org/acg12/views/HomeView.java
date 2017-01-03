package org.acg12.views;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.MainPagerAdapter;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.ui.fragment.TabAlbumFragment;
import org.acg12.ui.fragment.TabAnimatFragment;
import org.acg12.ui.fragment.TabBangumiFragment;
import org.acg12.ui.fragment.TabPaletteFragment;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/10.
 */
public class HomeView extends ViewImpl {

    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.home_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.home_viewpager)
    ViewPager mViewpager;

    Fragment[] fragments;
    MainPagerAdapter mainPagerAdapter;
    TabAlbumFragment tabAlbumFragment;
    TabPaletteFragment tabPaletteFragment;
    TabBangumiFragment tabBangumiFragment;
    TabAnimatFragment tabMADAMVFragment;
    TabAnimatFragment tabMMD3DFragment;
    TabAnimatFragment tabChatFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void created() {
        super.created();
        mToolbar.setNavigationIcon(R.mipmap.ic_action_home);
        mToolbar.inflateMenu(R.menu.menu_main);

        tabAlbumFragment = TabAlbumFragment.newInstance("");
        tabPaletteFragment = new TabPaletteFragment();
        tabBangumiFragment = new TabBangumiFragment();
        tabMADAMVFragment = TabAnimatFragment.newInstance(0);
        tabMMD3DFragment = TabAnimatFragment.newInstance(1);
        tabChatFragment = TabAnimatFragment.newInstance(2);
        fragments = new Fragment[]{tabAlbumFragment , tabPaletteFragment ,tabBangumiFragment ,tabMADAMVFragment ,tabMMD3DFragment, tabChatFragment };

        mainPagerAdapter = new MainPagerAdapter(((AppCompatActivity)getContext()).getSupportFragmentManager() , fragments);
        mViewpager.setAdapter(mainPagerAdapter);
        mViewpager.setOffscreenPageLimit(fragments.length);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , mToolbar );
        mToolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener)mPresenter);
    }
}
