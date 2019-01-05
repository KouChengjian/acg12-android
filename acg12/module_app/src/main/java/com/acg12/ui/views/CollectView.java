package com.acg12.ui.views;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.acg12.R;
import com.acg12.lib.ui.adapter.CommonPagerAdapter;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.utils.TabLayoutUtil;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.ui.fragment.CollectAlbumFragment;
import com.acg12.ui.fragment.CollectBangunFragment;
import com.acg12.ui.fragment.CollectCaricatureFragment;
import com.acg12.ui.fragment.CollectPaletteFragment;
import com.acg12.ui.fragment.CollectVideoFragment;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class CollectView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView mToolBarView;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    private String[] titles = new String[]{"门诊经营", "医疗技法", "特色病种", "特色验方"};
    private Fragment[] fragments;
    private CommonPagerAdapter mCommonPagerAdapter;
    private CollectAlbumFragment mCollectAlbumFragment;
    private CollectPaletteFragment mCollectPaletteFragment;
    private CollectCaricatureFragment mCollectCaricatureFragment;
    private CollectBangunFragment mCollectBangunFragment;
    private CollectVideoFragment mCollectVideoFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    public void created() {
        super.created();
        mCollectAlbumFragment = CollectAlbumFragment.newInstance();
        mCollectPaletteFragment = CollectPaletteFragment.newInstance();
        mCollectCaricatureFragment = CollectCaricatureFragment.newInstance();
        mCollectBangunFragment = CollectBangunFragment.newInstance();
        mCollectVideoFragment = CollectVideoFragment.newInstance();

        fragments = new Fragment[]{mCollectAlbumFragment, mCollectPaletteFragment, mCollectCaricatureFragment, mCollectBangunFragment, mCollectVideoFragment};
        mCommonPagerAdapter = new CommonPagerAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), fragments, titles);
        mViewpager.setAdapter(mCommonPagerAdapter);
        mViewpager.setOffscreenPageLimit(fragments.length);
        mTabLayout.setupWithViewPager(mViewpager);
        TabLayoutUtil.setIndicator(getContext(), mTabLayout, 5);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, mToolBarView.getToolbar());
    }

}
