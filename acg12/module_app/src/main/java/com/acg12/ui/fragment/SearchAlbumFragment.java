package com.acg12.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.acg12.conf.EventConfig;
import com.acg12.conf.event.CommonEnum;
import com.acg12.conf.event.CommonEvent;
import com.acg12.entity.Album;
import com.acg12.lib.constant.Constant;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.activity.AlbumInfoActivity;
import com.acg12.ui.adapter.SearchAlbumAdapter;
import com.acg12.ui.base.SkinBaseFragment;
import com.acg12.ui.views.SearchAlbumView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAlbumFragment extends SkinBaseFragment<SearchAlbumView> implements IRecycleView.LoadingListener, SearchAlbumAdapter.SearchAlbumListener,
        SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener, CommonRecycleview.IRecycleUpdataListener {

    private String title = "";
    private int page = 1;
    private boolean refresh = true;

    public static SearchAlbumFragment newInstance(String title) {
        SearchAlbumFragment fragment = new SearchAlbumFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        title = getArguments().getString("title");
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        EventConfig.get().getCommon().register(this);
        String[] s1 = title.split(" ");
        if (s1.length > 1) {
            title = s1[0];
        }
        onRefresh();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                int position = data.getExtras().getInt("position");
                mView.moveToPosition(position);
            }
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//        Intent intent = new Intent(mContext, PreviewAlbumActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("position", position);
//        PreviewAlbumActivity.mList = mView.getList();
//        intent.putExtras(bundle);
//        startActivityForResult(intent, 1000);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putSerializable("list", (Serializable) mView.getList());
        startAnimActivity(AlbumInfoActivity.class, bundle, 1000);
    }

    @Override
    public void onRecycleReload() {
        onLoadMore();
    }

    @Override
    public void onRefresh() {
        page = 1;
        refresh = true;
        refresh(title, page);
    }

    @Override
    public void onLoadMore() {
        page++;
        refresh = false;
        refresh(title, page);
    }

    @Override
    public void onClickCollect(int position) {
        Album album = mView.getObject(position);
        if (album.getIsCollect() == 1) {
            delCollectAlbum(position, album);
        } else {
            addCollectAlbum(position, album);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void commonEvent(CommonEvent commonEvent) {
        if (CommonEnum.COMMON_NEWEST_ALBUM == commonEvent.getCommonEvent()) {
            List<Album> albums = (ArrayList<Album>) commonEvent.getObject();
            mView.bindData(albums, false);
        }
    }

    public void refresh(String key, int page) {
        HttpRequestImpl.getInstance().searchAlbum(currentUser(), key, page + "", new HttpRequestListener<List<Album>>() {
            @Override
            public void onSuccess(List<Album> result) {
                if (result.size() < Constant.LIMIT_PAGER_20) {
                    mView.stopLoading();
                }
                mView.bindData(result, refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag, msg);
//                ShowToastView(msg);
                mView.recycleException();
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
                if (errorcode == 5010001) {
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
    public void onDestroy() {
        EventConfig.get().getCommon().unregister(this);
        super.onDestroy();
    }
}
