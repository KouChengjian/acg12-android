package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.acg12.R;
import com.acg12.entity.CollectCaricatureEntity;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.lib.widget.recycle.LayoutStatus;
import com.acg12.ui.adapter.CollectCaricatureAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 15:11
 * Description:
 */
public class CollectCaricatureView extends ViewImpl {

    @BindView(R.id.commonRecycleview)
    CommonRecycleview commonRecycleview;
    CollectCaricatureAdapter mCollectCaricatureAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect_caricature;
    }

    @Override
    public void created() {
        super.created();
        mCollectCaricatureAdapter = new CollectCaricatureAdapter(getContext());
        commonRecycleview.setStaggeredGridLayoutManager();
        commonRecycleview.setAdapter(mCollectCaricatureAdapter);
        commonRecycleview.setDefaultLayoutStatus(LayoutStatus.LAYOUT_STATUS_EMPTY_REFRESH);
        commonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mCollectCaricatureAdapter.setCollectCaricatureListener((CollectCaricatureAdapter.CollectCaricatureListener) mPresenter);
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setRecycleUpdataListener((CommonRecycleview.IRecycleUpdataListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener) mPresenter);
    }

    public void bindData(List<CollectCaricatureEntity> result, boolean refresh) {
        if (refresh) {
            mCollectCaricatureAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            mCollectCaricatureAdapter.addAll(result);
            commonRecycleview.notifyChanged(mCollectCaricatureAdapter.getList().size() - result.size(), mCollectCaricatureAdapter.getList().size());
        }
        commonRecycleview.stopRefreshLoadMore(refresh);
    }

    public CollectCaricatureEntity getObject(int position) {
        return mCollectCaricatureAdapter.getList().get(position);
    }

    public void stopLoading() {
        commonRecycleview.stopLoading();
    }

    public void recycleException() {
        commonRecycleview.recycleException();
    }

    public void updataObject(int position, int isCollect) {
//        CollectCaricatureEntity caricatureEntity = getObject(position);
//        caricatureEntity.setIsCollect(isCollect);
//        commonRecycleview.notifyChanged(position);
    }
}
