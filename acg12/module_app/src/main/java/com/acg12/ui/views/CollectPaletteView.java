package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.entity.CollectPaletteEntity;
import com.acg12.entity.Palette;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.lib.widget.recycle.LayoutStatus;
import com.acg12.ui.adapter.CollectAlbumAdapter;
import com.acg12.ui.adapter.CollectPaletteAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 15:07
 * Description:
 */
public class CollectPaletteView extends ViewImpl {

    @BindView(R.id.commonRecycleview)
    CommonRecycleview mCommonRecycleview;
    CollectPaletteAdapter mCollectPaletteAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collect_palette;
    }

    @Override
    public void created() {
        super.created();
        mCommonRecycleview.setStaggeredGridLayoutManager();
        mCollectPaletteAdapter = new CollectPaletteAdapter(getContext());
        mCommonRecycleview.setDefaultLayoutStatus(LayoutStatus.LAYOUT_STATUS_EMPTY_REFRESH);
        mCommonRecycleview.setAdapter(mCollectPaletteAdapter);
        mCommonRecycleview.startRefreshing();

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mCollectPaletteAdapter.setSearchPaletteListener((CollectPaletteAdapter.SearchPaletteListener) mPresenter);
        mCommonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        mCommonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mCommonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener) mPresenter);
        mCommonRecycleview.setRecycleUpdataListener((CommonRecycleview.IRecycleUpdataListener)mPresenter);
    }

    public void bindData(List<CollectPaletteEntity> result, boolean refresh) {
        if (refresh) {
            mCollectPaletteAdapter.setList(result);
            mCommonRecycleview.notifyChanged();
        } else {
            mCollectPaletteAdapter.addAll(result);
            mCommonRecycleview.notifyChanged(getList().size() - result.size(), getList().size());
        }
        mCommonRecycleview.stopRefreshLoadMore(true);
    }

    public List<CollectPaletteEntity> getList() {
        return mCollectPaletteAdapter.getList();
    }

    public CollectPaletteEntity getObject(int position) {
        return getList().get(position);
    }


    public void stopLoading() {
        mCommonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        mCommonRecycleview.stopRefreshLoadMore(refresh);
    }

    public void recycleException() {
        mCommonRecycleview.recycleException();
    }

    public void updataObject(int position, int isCollect) {
        CollectPaletteEntity album = getObject(position);
//        album.setIsCollect(isCollect);
//        mCommonRecycleview.notifyChanged(position);
    }

}
