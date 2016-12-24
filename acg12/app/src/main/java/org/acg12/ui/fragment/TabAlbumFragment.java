package org.acg12.ui.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.acg12.bean.Album;
import org.acg12.config.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.listener.ItemClickSupport;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.activity.AlbumPreviewActivity;
import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.utlis.ViewUtil;
import org.acg12.views.TabAlbumView;
import org.acg12.widget.IRecycleView;

import java.util.List;

public class TabAlbumFragment extends PresenterFragmentImpl<TabAlbumView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener {

    boolean refresh = true;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        refresh("");
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Album album = mView.getAlbum(position);
            album.getTransitionView().setTransitionName(album.getContent());
            Bundle bundle = new Bundle();
            bundle.putString("transitionName",album.getTransitionView().getTransitionName());
            bundle.putString("imageUrl",album.getImageUrl());
            //ViewUtil.startTransitionActivity(mContext , AlbumPreviewActivity.class , bundle ,album.getTransitionView());
            Intent intent = new Intent();
            intent.setClass(mContext, AlbumPreviewActivity.class);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                    (Activity)mContext , album.getTransitionView() , album.getTransitionView().getTransitionName()).toBundle());
        }
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

    public void refresh(String pinId){
        HttpRequestImpl.getInstance().albumList(pinId, new HttpRequestListener<List<Album>>() {
            @Override
            public void onSuccess(List<Album> result) {
                if (result.size() != 0 && result.get(result.size() - 1) != null) {
                    if (result.size() < Constant.LIMIT_PAGER) {
                        mView.stopLoading();
                    }
                    mView.bindData(result , refresh);
                }
                mView.stopRefreshLoadMore(refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag , msg);
                ShowToastView(msg);
                mView.stopRefreshLoadMore(refresh);
            }
        });
    }


}
