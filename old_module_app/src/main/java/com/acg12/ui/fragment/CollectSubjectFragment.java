package com.acg12.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.acg12.entity.CollectSubjectEntity;
import com.acg12.lib.cons.Constant;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.activity.SearchInfoActivity;
import com.acg12.ui.adapter.CollectSubjectAdapter;
import com.acg12.ui.views.CollectSubjectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectSubjectFragment extends PresenterFragmentImpl<CollectSubjectView> implements IRecycleView.LoadingListener,
        SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener, CommonRecycleview.IRecycleUpdataListener, CollectSubjectAdapter.CollectSubjectListener {


    private int pageNum = 1;
    private boolean refresh = true;

    public static CollectSubjectFragment newInstance() {
        CollectSubjectFragment fragment = new CollectSubjectFragment();
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
        CollectSubjectEntity collectSubjectEntity = mView.getObject(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", collectSubjectEntity.getRelevanceId());
        bundle.putInt("type", collectSubjectEntity.getType());
        bundle.putString("typeName", collectSubjectEntity.getTypeName());
        bundle.putString("title", TextUtils.isEmpty(collectSubjectEntity.getNameCn()) ? collectSubjectEntity.getName() : collectSubjectEntity.getNameCn());
        startAnimActivity(SearchInfoActivity.class, bundle);
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
        CollectSubjectEntity collectSubjectEntity = mView.getObject(position);
        if (collectSubjectEntity.getIsCollect() == 1) {
            delCollect(position, collectSubjectEntity);
        } else {
            addCollect(position, collectSubjectEntity);
        }
    }

    public void requestData() {
        HttpRequestImpl.getInstance().collectSubjectList(pageNum, Constant.LIMIT_PAGER_20, new HttpRequestListener<List<CollectSubjectEntity>>() {
            @Override
            public void onSuccess(List<CollectSubjectEntity> result) {
                if (result.size() < Constant.LIMIT_PAGER_20) {
                    mView.stopLoading();
                }
                mView.bindData(result, refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                ShowToast(msg);
                LogUtil.e(mTag, msg);
                mView.recycleException();
            }
        });
    }

    private void addCollect(final int position, final CollectSubjectEntity collectSubjectEntity) {
        startLoading("收藏中...");
        Map<String, Object> params = new HashMap<>();
        params.put("relevanceId", collectSubjectEntity.getRelevanceId());
        params.put("type", collectSubjectEntity.getType());
        params.put("typeName", collectSubjectEntity.getNameCn());
        params.put("image", collectSubjectEntity.getImage());
        params.put("name", collectSubjectEntity.getName());
        params.put("nameCn", collectSubjectEntity.getNameCn());
        HttpRequestImpl.getInstance().collectSubjectAdd(params, new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                collectSubjectEntity.setIsCollect(1);
                mView.updataObject(position, collectSubjectEntity.getIsCollect());
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToast(msg);
                LogUtil.e(msg);
            }
        });
    }

    private void delCollect(final int position, final CollectSubjectEntity collectSubjectEntity) {
        startLoading("取消收藏中...");
        HttpRequestImpl.getInstance().collectSubjectDel(collectSubjectEntity.getRelevanceId(), new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                collectSubjectEntity.setIsCollect(0);
                mView.updataObject(position, collectSubjectEntity.getIsCollect());
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
