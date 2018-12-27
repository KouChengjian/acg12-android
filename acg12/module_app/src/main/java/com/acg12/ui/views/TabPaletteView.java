package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.acg12.entity.Palette;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.TabPaletteAdapter;

import com.acg12.R;
import com.acg12.entity.Palette;
import com.acg12.ui.adapter.TabPaletteAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/23.
 */
public class TabPaletteView extends ViewImpl {

    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;
    TabPaletteAdapter tabPaletteAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_palette;
    }

    @Override
    public void created() {
        super.created();
        commonRecycleview.setStaggeredGridLayoutManager();
        tabPaletteAdapter = new TabPaletteAdapter(getContext());
        commonRecycleview.setAdapter(tabPaletteAdapter);
        commonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void bindData(List<Palette> result , boolean refresh){
        if (refresh) {
            tabPaletteAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            tabPaletteAdapter.addAll(result);
            commonRecycleview.notifyChanged(tabPaletteAdapter.getList().size() - result.size() , tabPaletteAdapter.getList().size());
        }

    }

    public String getBoardId(){
        return tabPaletteAdapter.getList().get(tabPaletteAdapter.getList().size()-1).getBoardId();
    }

    public Palette getPalette(int position){
        return tabPaletteAdapter.getList().get(position);
    }

    public void stopLoading(){
        commonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        commonRecycleview.stopRefreshLoadMore(refresh);
    }
}
