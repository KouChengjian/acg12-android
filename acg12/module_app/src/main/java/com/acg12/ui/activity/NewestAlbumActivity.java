package com.acg12.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.lib.constant.Constant;
import com.acg12.entity.Album;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.adapter.NewestAlbumAdapter;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.NewestAlbumView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每日插画
 */
public class NewestAlbumActivity extends SkinBaseActivity<NewestAlbumView> implements IRecycleView.LoadingListener,
        SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener, NewestAlbumAdapter.NewestAlbumListener {

    private boolean refresh = true;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh("");
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
                List<Album> list = mView.getAlbumList();
                list = PreviewAlbumActivity.mList;
                PreviewAlbumActivity.mList = null;
                mView.MoveToPosition(position);
            }
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(mContext, PreviewAlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        PreviewAlbumActivity.mList = mView.getAlbumList();
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onRefresh() {
        refresh = true;
        refresh("");
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        refresh(mView.getPicId());
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

    public void refresh(String pinId) {
        HttpRequestImpl.getInstance().albumList(currentUser(), pinId, new HttpRequestListener<List<Album>>() {
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
                mView.updataObject(position, 1);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToast(msg);
                LogUtil.e(msg);
                if(errorcode == 5010001){
                    mView.updataObject(position, 1);
                }
            }
        });
    }

    public void delCollectAlbum(final int position, Album albun) {
        startLoading("取消收藏中...");
        HttpRequestImpl.getInstance().collectAlbumDel(albun.getPinId(), new HttpRequestListener<String>() {
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
