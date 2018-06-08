package org.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.CommonRecycleview;
import com.acg12.lib.widget.IRecycleView;

import org.acg12.R;
import org.acg12.entity.Video;
import org.acg12.ui.adapter.TabBangumiAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/6.
 */
public class SearchBangunView extends ViewImpl {

    @BindView(R.id.common_recyclerview)
    CommonRecycleview commonRecycleview;
    TabBangumiAdapter tabBangumiAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void created() {
        super.created();
        commonRecycleview.setStaggeredGridLayoutManager();
        tabBangumiAdapter = new TabBangumiAdapter(getContext());
        commonRecycleview.setAdapter(tabBangumiAdapter);
        commonRecycleview.startRefreshing();
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
            tabBangumiAdapter.setList(result);
            commonRecycleview.notifyChanged();
        } else {
            tabBangumiAdapter.addAll(result);
            commonRecycleview.notifyChanged(tabBangumiAdapter.getList().size() - result.size() , tabBangumiAdapter.getList().size());
        }
    }

    public String getBangumiId(int position){
        return tabBangumiAdapter.getList().get(position).getBmId();
    }

    public void stopLoading(){
        commonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        commonRecycleview.stopRefreshLoadMore(refresh);
    }
}
