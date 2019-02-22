package com.acg12.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.entity.Album;
import com.acg12.lib.constant.Constant;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.activity.PreviewAlbumActivity;
import com.acg12.ui.adapter.CollectAlbumAdapter;
import com.acg12.ui.adapter.NewestAlbumAdapter;
import com.acg12.ui.views.CollectAlbumView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 14:57
 * Description:
 */
public class CollectAlbumFragment extends PresenterFragmentImpl<CollectAlbumView> implements IRecycleView.LoadingListener,
        SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener, CollectAlbumAdapter.CollectAlbumListener {

    private int pageNum = 1;
    private boolean refresh = true;

    public static CollectAlbumFragment newInstance() {
        CollectAlbumFragment fragment = new CollectAlbumFragment();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                int position = data.getExtras().getInt("position");
                List<Album> list = mView.getList();
                list = PreviewAlbumActivity.mList;
                PreviewAlbumActivity.mList = null;
                mView.moveToPosition(position);
            }
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(mContext, PreviewAlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        PreviewAlbumActivity.mList = mView.getList();
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000);
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
    public void onClickCollect(int position) {
        Album album = mView.getAlbum(position);
        if (album.getIsCollect() == 1) {
            delCollectAlbum(position, album);
        } else {
            addCollectAlbum(position, album);
        }
    }

    public void requestData() {
        HttpRequestImpl.getInstance().collectAlbumList(pageNum, Constant.LIMIT_PAGER_20, new HttpRequestListener<List<Album>>() {
            @Override
            public void onSuccess(List<Album> result) {
                if (result.size() != 0 && result.get(result.size() - 1) != null) {
                    if (result.size() < Constant.LIMIT_PAGER_20) {
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

    public void addCollectAlbum(final int position, Album albun) {
        Map<String, Object> params = new HashMap<>();
        params.put("pinId", albun.getPinId());
        params.put("image", albun.getImageUrl());
        params.put("content", albun.getContent());
        params.put("love", albun.getLove());
        params.put("favorites", albun.getFavorites());
        params.put("resWidth", albun.getResWidth());
        params.put("resHight", albun.getResHight());
        startLoading("收藏中...");
        HttpRequestImpl.getInstance().collectAlbumAdd(params, new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                ShowToastView("收藏成功");
                mView.updataObject(position, 1);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToastView(msg);
                LogUtil.e(msg);
                if(errorcode == 5010001){
                    mView.updataObject(position, 1);
                }
            }
        });
    }

    public void delCollectAlbum(final int position, Album albun) {
        Map<String, Object> params = new HashMap<>();
        params.put("pinId", albun.getPinId());
        startLoading("取消收藏中...");
        HttpRequestImpl.getInstance().collectAlbumAdd(params, new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                mView.updataObject(position, 0);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToastView(msg);
                LogUtil.e(msg);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
