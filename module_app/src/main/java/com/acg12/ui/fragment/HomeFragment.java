package com.acg12.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.po.HomeEntity;
import com.acg12.lib.utils.PixelUtil;
import com.acg12.ui.adapter.HomeAdapter;
import com.acg12.ui.base.BaseMvpFragment;
import com.acg12.ui.contract.HomeContract;
import com.acg12.ui.presenter.HomePresenter;
import com.acg12.utils.CollapsingToolbarLayoutState;

import butterknife.BindView;


/**
 * Created with Android Studio.
 * UserEntity kcj
 * Date 2019/06/20
 * Description: 自动生成
 */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.search_appbar)
    AppBarLayout appbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.home_toolbar)
    Toolbar toolbar;

    @BindView(R.id.layout_container)
    protected RelativeLayout layout_container;
    @BindView(R.id.iv_home_cover)
    ImageView iv_home_cover;
    @BindView(R.id.btn_home_search)
    View btn_home_search;
    @BindView(R.id.btn_newest_news)
    TextView btn_newest_news;
    @BindView(R.id.btn_newest_illustration)
    TextView btn_newest_illustration;
    @BindView(R.id.common_recyclerview)
    RecyclerView commonRecycleview;

    MenuItem searchMenu;

    HomeAdapter homeAdapter;
    CollapsingToolbarLayoutState state = CollapsingToolbarLayoutState.EXPANDED;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        toolbar.setNavigationIcon(R.mipmap.ic_action_home);
        toolbar.inflateMenu(R.menu.menu_main);
        searchMenu = toolbar.getMenu().findItem(R.id.menu_main_search);
        searchMenu.setVisible(false);
        ViewGroup.LayoutParams layoutParams = layout_container.getLayoutParams();
        layoutParams.height = PixelUtil.getScreenWidthPx(getContext()) / 4 * 3;
        layout_container.setLayoutParams(layoutParams);

        homeAdapter = new HomeAdapter(getContext());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        commonRecycleview.setLayoutManager(staggeredGridLayoutManager);
        commonRecycleview.setAdapter(homeAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        swipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(getContext(), 50), PixelUtil.dp2px(getContext(), 24));
        swipeRefreshLayout.setRefreshing(true);

        mPresenter.requestIndex();
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showProgressDialog(String msg) {
    }

    @Override
    public void dismissProgressDialog() {
    }

    @Override
    public void requestIndexSuccess(HomeEntity home) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
