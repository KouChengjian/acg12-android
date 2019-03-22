package com.acg12.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.constant.Constant;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.activity.CaricatureInfoActivity;
import com.acg12.ui.adapter.SearchCaricatureAdapter;
import com.acg12.ui.views.SearchCaricatureView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/28 10:49
 * Description: 搜索漫画
 */
public class SearchCaricatureFragment extends PresenterFragmentImpl<SearchCaricatureView> implements IRecycleView.LoadingListener, SwipeRefreshLayout.OnRefreshListener
        , ItemClickSupport.OnItemClickListener, CommonRecycleview.IRecycleUpdataListener, SearchCaricatureAdapter.SearchCaricatureListener {

    private String title = "";
    private int page = 1;
    private boolean refresh = true;

    public static SearchCaricatureFragment newInstance(String title) {
        SearchCaricatureFragment fragment = new SearchCaricatureFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        title = getArguments().getString("title");
        refresh(title, page);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        CaricatureEntity caricatureEntity = mView.getObject(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", caricatureEntity.getComicId());
        bundle.putInt("type", caricatureEntity.getType());
        bundle.putString("title", caricatureEntity.getTitle());
        startAnimActivity(CaricatureInfoActivity.class, bundle);
    }

    @Override
    public void onRecycleReload() {
        refresh(title, page);
    }

    @Override
    public void onLoadMore() {
        page++;
        refresh = false;
        refresh(title, page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        refresh = true;
        refresh(title, page);
    }

    @Override
    public void onClickCollect(int position) {
        CaricatureEntity caricatureEntity = mView.getObject(position);
        if (caricatureEntity.getIsCollect() == 1) {
            delCollectCaricature(position, caricatureEntity);
        } else {
            addCollectCaricature(position, caricatureEntity);
        }
    }

    public void refresh(String key, int page) {
        HttpRequestImpl.getInstance().searchCaricatureList(key, page + "", new HttpRequestListener<List<CaricatureEntity>>() {
            @Override
            public void onSuccess(List<CaricatureEntity> result) {
                if (result.size() < Constant.LIMIT_PAGER) {
                    mView.stopLoading();
                }
                mView.bindData(result, refresh);
                mView.stopRefreshLoadMore(refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                mView.stopRefreshLoadMore(refresh);
                Log.e(mTag, msg);
//                ShowToastView(msg);
            }
        });
    }

    public void addCollectCaricature(final int position, CaricatureEntity albun) {
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

    public void delCollectCaricature(final int position, CaricatureEntity caricatureEntity) {
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
    public void onDestroyView() {
        super.onDestroyView();
    }
}
