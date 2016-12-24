package org.acg12.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import org.acg12.R;
import org.acg12.bean.Home;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.HomeAdapter;
import org.acg12.utlis.PixelUtil;
import org.acg12.widget.IRecycleView;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/10.
 */
public class HomeView extends ViewImpl {

    @BindView(R.id.mRecyclerView)
    IRecycleView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    HomeAdapter homeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void created() {
        super.created();

        View header = LayoutInflater.from(getContext()).inflate(R.layout.item_home, null);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager); //new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView.addHeaderView(header);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        homeAdapter = new HomeAdapter(getContext());

        mRecyclerView.setAdapter(homeAdapter);


        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
        mSwipeRefreshLayout.setRefreshing(true);

        for (int i = 0, j = 20; i < j; i++) {
            homeAdapter.add(new Home());
        }
        homeAdapter.notifyDataSetChanged();
    }

    public void ss() {
        mRecyclerView.loadMoreComplete();
    }

}
