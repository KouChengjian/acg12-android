package com.acg12.ui.views;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.ui.activity.NewestAlbumInfoActivity;
import com.acg12.ui.adapter.NewestAlbumInfoAdapter;

import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/3/22 18:46
 * Description:
 */
public class NewestAlbumInfoView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView mToolBarView;
    @BindView(R.id.commonRecycleview)
    CommonRecycleview mCommonRecycleview;

    NewestAlbumInfoAdapter mNewestAlbumInfoAdapter;
    PagerSnapHelper mPagerSnapHelper; // RecyclerView帮助类。主要是变成Viewpager的模式需要


    @Override
    public int getLayoutId() {
        return R.layout.activity_newest_album_info;
    }

    @Override
    public void created() {
        super.created();
        mToolBarView.setNavigationOrBreak("预览");
        mCommonRecycleview.setLinearLayoutManager(LinearLayoutManager.HORIZONTAL);
        mNewestAlbumInfoAdapter = new NewestAlbumInfoAdapter(getContext());
        mCommonRecycleview.setRefreshEnabled(false);
        mCommonRecycleview.setAdapter(mNewestAlbumInfoAdapter);
//        mCommonRecycleview.startRefreshing();
        mPagerSnapHelper = new PagerSnapHelper();
        mPagerSnapHelper.attachToRecyclerView(mCommonRecycleview.getIRecycleView());

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, mToolBarView.getToolbar());
        mCommonRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当屏幕停止滚动，
                if (newState == SCROLL_STATE_IDLE) {
                    //更新当前对象在更新信息
                    if (isVisBottom(mCommonRecycleview.getIRecycleView())) {
                        Toastor.ShowToast("加载数据...");
                        ((NewestAlbumInfoActivity) getContext()).requestData();
                    }
                }
            }
        });
    }

    public void bindData(List<Album> result, boolean refresh) {
        if (refresh) {
            mNewestAlbumInfoAdapter.setList(result);
            mCommonRecycleview.notifyChanged();
        } else {
            mNewestAlbumInfoAdapter.addAll(result);
            mCommonRecycleview.notifyChanged(getList().size() - result.size(), getList().size());
        }
        mCommonRecycleview.stopRefreshLoadMore(refresh);
    }

    public String getLastId() {
        return getList().get(getList().size() - 1).getPinId();
    }

    public List<Album> getList() {
        return mNewestAlbumInfoAdapter.getList();
    }

    public Album getObject(int position) {
        return getList().get(position);
    }

    public void stopLoading() {
        mCommonRecycleview.stopLoading();
    }

    public void recycleException() {
        mCommonRecycleview.recycleException();
    }

    public void scrollToPositionWithOffset(int position) {
        mCommonRecycleview.scrollToPositionWithOffset(position);
    }

    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新当前图片所在的对象
     *
     * @return 当前位置
     */
    public int updateCurrObject() {
        LinearLayoutManager l = (LinearLayoutManager) mCommonRecycleview.getIRecycleView().getLayoutManager();
        int firstVisibleItemPosition = l.findFirstVisibleItemPosition();
        return firstVisibleItemPosition;
    }
}
