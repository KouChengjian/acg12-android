package com.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.acg12.entity.Palette;
import com.acg12.lib.constant.Constant;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.activity.PreviewPaletteActivity;
import com.acg12.ui.adapter.SearchPaletteAdapter;
import com.acg12.ui.base.SkinBaseFragment;
import com.acg12.ui.views.SearchPaletteView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPaletteFragment extends SkinBaseFragment<SearchPaletteView> implements IRecycleView.LoadingListener, SearchPaletteAdapter.SearchPaletteListener,
        SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener, CommonRecycleview.IRecycleUpdataListener {

    private String title = "";
    private int page = 1;
    private boolean refresh = true;

    public static SearchPaletteFragment newInstance(String title) {
        SearchPaletteFragment fragment = new SearchPaletteFragment();
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("palette", mView.getObject(position));
        startAnimActivity(PreviewPaletteActivity.class, bundle);
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
        Palette palette = mView.getObject(position);
        if (palette.getIsCollect() == 1) {
            delCollectPalette(position, palette);
        } else {
            addCollectPalette(position, palette);
        }
    }

    public void refresh(String key, int page) {
        HttpRequestImpl.getInstance().searchPalette(currentUser(), key, page + "", new HttpRequestListener<List<Palette>>() {
            @Override
            public void onSuccess(List<Palette> result) {
                if (result.size() < Constant.LIMIT_PAGER) {
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

    public void addCollectPalette(final int position, Palette palette) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("pinId", albun.getPinId());
//        params.put("image", albun.getImageUrl());
//        params.put("content", albun.getContent());
//        params.put("love", albun.getLove());
//        params.put("favorites", albun.getFavorites());
//        params.put("resWidth", albun.getResWidth());
//        params.put("resHight", albun.getResHight());
//        startLoading("收藏中...");
//        HttpRequestImpl.getInstance().collectAlbumAdd(params, new HttpRequestListener<String>() {
//            @Override
//            public void onSuccess(String result) {
//                stopLoading();
//                mView.updataObject(position, 1);
//            }
//
//            @Override
//            public void onFailure(int errorcode, String msg) {
//                stopLoading();
//                ShowToast(msg);
//                LogUtil.e(msg);
//                if (errorcode == 5010001) {
//                    mView.updataObject(position, 1);
//                }
//            }
//        });
    }

    public void delCollectPalette(final int position, Palette palette) {
//        startLoading("取消收藏中...");
//        HttpRequestImpl.getInstance().collectAlbumDel(albun.getPinId(), new HttpRequestListener<String>() {
//            @Override
//            public void onSuccess(String result) {
//                stopLoading();
//                mView.updataObject(position, 0);
//            }
//
//            @Override
//            public void onFailure(int errorcode, String msg) {
//                stopLoading();
//                ShowToast(msg);
//                LogUtil.e(msg);
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
