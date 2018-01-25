package org.acg12.ui.views;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.acg12.kk.ui.ViewImpl;
import com.acg12.kk.ui.base.PresenterHelper;

import org.acg12.R;
import org.acg12.ui.adapter.SearchPagerAdapter;
import org.acg12.ui.fragment.SearchAlbumFragment;
import org.acg12.ui.fragment.SearchAnimatFragment;
import org.acg12.ui.fragment.SearchBangunFragment;
import org.acg12.ui.fragment.SearchPaletteFragment;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class SearchInfoView extends ViewImpl {

    @BindView(R.id.search_toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.search_tabLayout)
    protected TabLayout mTabLayout;
    @BindView(R.id.search_viewpager)
    protected ViewPager mViewpager;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    protected Fragment[] fragments;
    SearchPagerAdapter searchPagerAdapter;
    SearchAlbumFragment searchAlbumFragment;
    SearchPaletteFragment searchPaletteFragment;
    SearchBangunFragment searchBangunFragment;
    SearchAnimatFragment searchAnimatFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search_info;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.search));
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setTitleEnabled(false);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolbar);
    }

    public void bindData(String title) {
        toolbar.setTitle(title);

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

    public TabLayout getTabLayout(){
        return mTabLayout;
    }

}
