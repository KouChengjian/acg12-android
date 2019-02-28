package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.acg12.R;
import com.acg12.entity.Palette;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.SearchPaletteAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/6.
 */
public class SearchPaletteView extends ViewImpl {

    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;
    SearchPaletteAdapter mSearchPaletteAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void created() {
        super.created();
        commonRecycleview.setStaggeredGridLayoutManager();
        mSearchPaletteAdapter = new SearchPaletteAdapter(getContext());
        commonRecycleview.setAdapter(mSearchPaletteAdapter);
        commonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener) mPresenter);
        commonRecycleview.setRecycleUpdataListener((CommonRecycleview.IRecycleUpdataListener)mPresenter);
        mSearchPaletteAdapter.setSearchPaletteListener((SearchPaletteAdapter.SearchPaletteListener)mPresenter);
    }

    public void bindData(List<Palette> result, boolean refresh) {
        if (refresh) {
            mSearchPaletteAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            mSearchPaletteAdapter.addAll(result);
            commonRecycleview.notifyChanged(getList().size() - result.size(), getList().size());
        }
        commonRecycleview.stopRefreshLoadMore(refresh);
    }

    public List<Palette> getList() {
        return mSearchPaletteAdapter.getList();
    }

    public Palette getObject(int position) {
        return getList().get(position);
    }

    public void stopLoading() {
        commonRecycleview.stopLoading();
    }

    public void recycleException() {
        commonRecycleview.recycleException();
    }
}
