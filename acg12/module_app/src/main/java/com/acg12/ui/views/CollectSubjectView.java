package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.entity.CollectSubjectEntity;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.CollectAlbumAdapter;
import com.acg12.ui.adapter.CollectSubjectAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/28 17:32
 * Description:
 */
public class CollectSubjectView extends ViewImpl {

    @BindView(R.id.commonRecycleview)
    CommonRecycleview mCommonRecycleview;
    CollectSubjectAdapter mCollectSubjectAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect_subject;
    }

    @Override
    public void created() {
        super.created();
        mCommonRecycleview.setLinearLayoutManager();
        mCollectSubjectAdapter = new CollectSubjectAdapter(getContext());
        mCommonRecycleview.setAdapter(mCollectSubjectAdapter);
        mCommonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mCollectSubjectAdapter.setCollectSubjectListener((CollectSubjectAdapter.CollectSubjectListener) mPresenter);
        mCommonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        mCommonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mCommonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener) mPresenter);
        mCommonRecycleview.setRecycleUpdataListener((CommonRecycleview.IRecycleUpdataListener)mPresenter);
    }

    public void bindData(List<CollectSubjectEntity> result, boolean refresh) {
        if (refresh) {
            mCollectSubjectAdapter.setList(result);
            mCommonRecycleview.notifyChanged();
        } else {
            mCollectSubjectAdapter.addAll(result);
            mCommonRecycleview.notifyChanged(getList().size() - result.size(), getList().size());
        }
        mCommonRecycleview.stopRefreshLoadMore(true);
    }

    public List<CollectSubjectEntity> getList() {
        return mCollectSubjectAdapter.getList();
    }

    public CollectSubjectEntity getObject(int position) {
        return getList().get(position);
    }

    public void stopLoading() {
        mCommonRecycleview.stopLoading();
    }

    public void recycleException() {
        mCommonRecycleview.recycleException();
    }

    public void updataObject(int position, int isCollect) {
        CollectSubjectEntity collectSubjectEntity = getObject(position);
        collectSubjectEntity.setIsCollect(isCollect);
        mCommonRecycleview.notifyChanged(position);
    }
}
