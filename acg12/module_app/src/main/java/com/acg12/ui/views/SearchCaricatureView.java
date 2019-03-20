package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.SearchCaricatureAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/28 10:55
 * Description:
 */
public class SearchCaricatureView extends ViewImpl {

    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;

    SearchCaricatureAdapter mCaricatureAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_caricature;
    }

    @Override
    public void created() {
        super.created();
        mCaricatureAdapter = new SearchCaricatureAdapter(getContext());
        commonRecycleview.setStaggeredGridLayoutManager();
        commonRecycleview.setAdapter(mCaricatureAdapter);
        commonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mCaricatureAdapter.setSearchCaricatureListener((SearchCaricatureAdapter.SearchCaricatureListener)mPresenter);
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void bindData(List<CaricatureEntity> result , boolean refresh){
        if (refresh) {
            mCaricatureAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            mCaricatureAdapter.addAll(result);
            commonRecycleview.notifyChanged(mCaricatureAdapter.getList().size() - result.size() , mCaricatureAdapter.getList().size());
        }
    }

    public CaricatureEntity getObject(int position){
        return mCaricatureAdapter.getList().get(position);
    }

    public void stopLoading(){
        commonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        commonRecycleview.stopRefreshLoadMore(refresh);
    }

    public void updataObject(int position, int isCollect) {
        CaricatureEntity caricatureEntity = getObject(position);
        caricatureEntity.setIsCollect(isCollect);
        commonRecycleview.notifyChanged(position);
    }
}
