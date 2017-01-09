package org.acg12.views;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.SearchPagerAdapter;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.ui.fragment.SearchAlbumFragment;
import org.acg12.ui.fragment.SearchAnimatFragment;
import org.acg12.ui.fragment.SearchBangunFragment;
import org.acg12.ui.fragment.SearchPaletteFragment;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class SearchView extends ViewImpl {

    @BindView(R.id.search_collapsing)
    CollapsingToolbarLayout searchCollapsing;
    @BindView(R.id.search_toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.search_viewpager)
    ViewPager mViewpager;

    Fragment[] fragments;
    SearchPagerAdapter searchPagerAdapter;
    SearchAlbumFragment searchAlbumFragment;
    SearchPaletteFragment searchPaletteFragment;
    SearchBangunFragment searchBangunFragment;
    SearchAnimatFragment searchAnimatFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        searchCollapsing.setTitle(getContext().getString(R.string.search));
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolbar);
    }

    public void bindData(String title) {
        searchCollapsing.setTitle(title);

        searchAlbumFragment = SearchAlbumFragment.newInstance(title);
        searchPaletteFragment = SearchPaletteFragment.newInstance(title);
        searchBangunFragment = SearchBangunFragment.newInstance(title);
        searchAnimatFragment = SearchAnimatFragment.newInstance(title);
        fragments = new Fragment[]{searchAlbumFragment, searchPaletteFragment, searchBangunFragment, searchAnimatFragment};

        searchPagerAdapter = new SearchPagerAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(searchPagerAdapter);
        mViewpager.setOffscreenPageLimit(fragments.length);
        mTabLayout.setupWithViewPager(mViewpager);
    }

}
