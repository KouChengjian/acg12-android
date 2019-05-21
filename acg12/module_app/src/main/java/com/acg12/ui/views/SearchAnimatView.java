package com.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.FrameLayout;

import com.acg12.entity.Video;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.ui.adapter.TabAnimatAdapter;

import com.acg12.R;
import com.acg12.entity.Video;
import com.acg12.ui.adapter.TabAnimatAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/6.
 */
public class SearchAnimatView extends ViewImpl {

    @BindView(R.id.video_full_container)
    FrameLayout videoFullContainer;
    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;
    TabAnimatAdapter tabAnimatAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void created() {
        super.created();

        commonRecycleview.setLinearLayoutManager();
        tabAnimatAdapter = new TabAnimatAdapter(getContext());
        commonRecycleview.setAdapter(tabAnimatAdapter);
        commonRecycleview.startRefreshing();

//        listVideoUtil = BaseConfig.ListVideoUtilInstance();
//        listVideoUtil.setFullViewContainer(videoFullContainer);
//        tabAnimatAdapter.setListVideoUtil(listVideoUtil);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        commonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        commonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        commonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void bindData(List<Video> result , boolean refresh){
        if (refresh) {
            tabAnimatAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            tabAnimatAdapter.addAll(result);
            commonRecycleview.notifyChanged(tabAnimatAdapter.getList().size() - result.size() , tabAnimatAdapter.getList().size());
        }
    }

    public Video getVideo(int position){
        return tabAnimatAdapter.getList().get(position);
    }

    public void stopLoading(){
        commonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        commonRecycleview.stopRefreshLoadMore(refresh);
    }

//    public ListVideoUtil getListVideoUtil(){
//        return listVideoUtil;
//    }

}
