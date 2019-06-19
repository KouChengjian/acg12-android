package com.acg12.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.entity.CollectCaricatureEntity;
import com.acg12.lib.cons.Constant;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.activity.CaricatureInfoActivity;
import com.acg12.ui.adapter.CollectCaricatureAdapter;
import com.acg12.ui.views.CollectCaricatureView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 14:58
 * Description:
 */
public class CollectCaricatureFragment extends PresenterFragmentImpl<CollectCaricatureView> implements IRecycleView.LoadingListener, SwipeRefreshLayout.OnRefreshListener
        , ItemClickSupport.OnItemClickListener, CommonRecycleview.IRecycleUpdataListener, CollectCaricatureAdapter.CollectCaricatureListener {

    private int pageNum = 1;
    private boolean refresh = true;

    public static CollectCaricatureFragment newInstance() {
        CollectCaricatureFragment fragment = new CollectCaricatureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        }, 2 * 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        CollectCaricatureEntity caricatureEntity = mView.getObject(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", caricatureEntity.getComicId());
        bundle.putInt("type", caricatureEntity.getType());
        bundle.putString("title", caricatureEntity.getTitle());
        startAnimActivity(CaricatureInfoActivity.class, bundle);
    }

    @Override
    public void onRefresh() {
        refresh = true;
        pageNum = 1;
        requestData();
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        pageNum++;
        requestData();
    }

    @Override
    public void onRecycleReload() {
        onRefresh();
    }

    @Override
    public void onClickCollect(int position) {
        CollectCaricatureEntity album = mView.getObject(position);
        if (album.getIsCollect() == 1) {
            delCollectCaricature(position, album);
        } else {
            delCollectCaricature(position, album);
        }
    }

    public void requestData() {
        HttpRequestImpl.getInstance().collectCaricatureList(pageNum, Constant.LIMIT_PAGER_20, new HttpRequestListener<List<CollectCaricatureEntity>>() {
            @Override
            public void onSuccess(List<CollectCaricatureEntity> result) {
                if (result.size() < Constant.LIMIT_PAGER_20) {
                    mView.stopLoading();
                }
                mView.bindData(result, refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                mView.recycleException();
                ShowToast(msg);
                LogUtil.e(mTag, msg);
            }
        });
    }

    public void addCollectCaricature(final int position, CollectCaricatureEntity albun) {
        Map<String, Object> params = new HashMap<>();
        params.put("comicId", albun.getComicId());
        params.put("type", albun.getType());
        params.put("cover", albun.getCover());
        params.put("title", albun.getTitle());
        startLoading("收藏中...");
        HttpRequestImpl.getInstance().collectCaricatureAdd(params, new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                mView.updataObject(position, 1);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToast(msg);
                LogUtil.e(msg);
                if (errorcode == 5010001) {
                    mView.updataObject(position, 1);
                }
            }
        });
    }

    public void delCollectCaricature(final int position, CollectCaricatureEntity caricatureEntity) {
        startLoading("取消收藏中...");
        HttpRequestImpl.getInstance().collectCaricatureDel(caricatureEntity.getComicId(), new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                mView.updataObject(position, 0);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToast(msg);
                LogUtil.e(msg);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
