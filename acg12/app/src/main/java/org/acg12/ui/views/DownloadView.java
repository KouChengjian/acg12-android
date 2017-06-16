package org.acg12.ui.views;

import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.listener.ItemClickSupport;
import org.acg12.net.download.DownLoad;
import org.acg12.net.download.DownLoadCallback;
import org.acg12.net.download.DownloadManger;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.DownloadAdapter;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.DUtil;
import org.acg12.utlis.IOUtils;
import org.acg12.utlis.LogUtil;
import org.acg12.utlis.PixelUtil;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.IRecycleView;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class DownloadView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mRecyclerView)
    IRecycleView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_load_null)
    ViewStub layoutLoadNull;
    ImageView loadNullImageview;
    TextView loadNullTextview;

    DownloadAdapter downloadAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.download));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        downloadAdapter = new DownloadAdapter(getContext());
        mRecyclerView.setAdapter(downloadAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , toolbar);
        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mRecyclerView.setLoadingListener((IRecycleView.LoadingListener) mPresenter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener((ItemClickSupport.OnItemLongClickListener)mPresenter);
    }

    public void bindData(List<DownLoad> result , boolean refresh){
        if (refresh) {
            downloadAdapter.setList(result);
        } else {
            downloadAdapter.addAll(result);
        }
        downloadAdapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(){
        downloadAdapter.notifyDataSetChanged();
    }

    public DownLoad getDownLoad(int position){
        return downloadAdapter.getList().get(position);
    }

    public List<DownLoad> getList(){
        return downloadAdapter.getList();
    }

    public void stopLoading(){
        mRecyclerView.noMoreLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        if (refresh)
            mSwipeRefreshLayout.setRefreshing(false);
        else
            mRecyclerView.loadMoreComplete();
        loadNull();
    }

    private void loadNull() {
        List<DownLoad> mlist = downloadAdapter.getList();
        if (mlist != null && !mlist.isEmpty()) {
            if (loadNullImageview != null && loadNullTextview != null) {
                ViewUtil.setText(loadNullTextview, "");
                if (loadNullImageview.getVisibility() == View.VISIBLE) {
                    loadNullImageview.setVisibility(View.GONE);
                }
            }
        } else {
            if (loadNullImageview == null && loadNullTextview == null) {
                View view = layoutLoadNull.inflate();
                loadNullImageview = (ImageView) view.findViewById(R.id.iv_load_null);
                loadNullTextview = (TextView) view.findViewById(R.id.tv_load_null);
            }
            //loadNullImageview.setImageResource(R.mipmap.ic_error);
            ViewUtil.setText(loadNullTextview, "暂时没有信息");
            if (loadNullImageview.getVisibility() == View.GONE) {
                loadNullImageview.setVisibility(View.VISIBLE);
            }
        }
    }
}
