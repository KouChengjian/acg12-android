package org.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.widget.CommonRecycleview;
import com.acg12.lib.widget.IRecycleView;

import org.acg12.R;
import org.acg12.ui.adapter.NewestNewsAdapter;
import org.acg12.utlis.RecycleViewHeaderUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/26.
 */

public class NewestNewsView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.commonRecycleview)
    CommonRecycleview mCommonRecycleview;
    @BindView(R.id.layout_header)
    RelativeLayout  layout_header;
    @BindView(R.id.tv_header_title)
    TextView tv_header_title;

    LinearLayoutManager layoutManager ;
    NewestNewsAdapter newestNewsAdapter;
    RecycleViewHeaderUtil mRecycleViewHeaderUtil;

    @Override
    public int getLayoutId() {
        return R.layout.activity_newest_news;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("每日快报");

        layoutManager = mCommonRecycleview.setLinearLayoutManager();
        newestNewsAdapter = new NewestNewsAdapter(getContext());
        mCommonRecycleview.setAdapter(newestNewsAdapter);
        mCommonRecycleview.startRefreshing();

        mRecycleViewHeaderUtil = new RecycleViewHeaderUtil(layoutManager ,layout_header ,tv_header_title);

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , toolbar);
        mCommonRecycleview.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        mCommonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mCommonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
//        mCommonRecycleview.getIRecycleView().addOnScrollListener(mRecycleViewHeaderUtil);
    }

    public void bindData(List result , boolean refresh){
        if (refresh) {
            newestNewsAdapter.setList(result);
            mRecycleViewHeaderUtil.setList(result);
            mCommonRecycleview.notifyChanged();
        } else {
            newestNewsAdapter.addAll(result);
            mRecycleViewHeaderUtil.addList(result);
            mCommonRecycleview.notifyChanged(newestNewsAdapter.getList().size() - result.size() , newestNewsAdapter.getList().size());
        }
    }

    public List getAlbumList() {
        return newestNewsAdapter.getList();
    }

    public Object getObject(int position) {
        return getAlbumList().get(position);
    }

    public void stopLoading(){
        mCommonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        mCommonRecycleview.stopRefreshLoadMore(refresh);
    }
}
