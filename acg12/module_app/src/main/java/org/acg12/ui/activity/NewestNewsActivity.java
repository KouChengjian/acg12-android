package org.acg12.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.IRecycleView;

import org.acg12.conf.Constant;
import org.acg12.entity.News;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.base.SkinBaseActivity;
import org.acg12.ui.views.NewestNewsView;

import java.util.List;

public class NewestNewsActivity extends SkinBaseActivity<NewestNewsView> implements IRecycleView.LoadingListener,
        SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener {

    private boolean refresh = true;
    private int page = 0;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 2 * 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        News news = (News)mView.getObject(position);
        Bundle bundle = new Bundle();
        bundle.putString("url",news.getLink());
        startAnimActivity(WebviewActivity.class , bundle);
    }

    @Override
    public void onRefresh() {
        refresh = true;
        page = 0;
        refresh();
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        page++;
        refresh();
    }

    public void refresh() {
        HttpRequestImpl.getInstance().newsList(currentUser(), page + "", new HttpRequestListener<List<News>>() {
            @Override
            public void onSuccess(List<News> result) {
                if (result.size() != 0 && result.get(result.size() - 1) != null) {
                    if (result.size() < Constant.LIMIT_PAGER) {
                        mView.stopLoading();
                    }
                    mView.bindData(result, refresh);
                }
                mView.stopRefreshLoadMore(refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                LogUtil.e(mTag, msg);
                ShowToastView(msg);
                mView.stopRefreshLoadMore(refresh);
            }
        });
    }
}
