package org.acg12.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;

import org.acg12.constant.Constant;
import org.acg12.entity.Album;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.activity.PreviewAlbumActivity;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.SearchAlbumView;

import java.util.List;

public class SearchAlbumFragment extends SkinBaseFragment<SearchAlbumView> implements IRecycleView.LoadingListener,
        SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener ,CommonRecycleview.IRecycleUpdataListener{

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
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        title = getArguments().getString("title");
        String[] s1 = title.split(" ");
        if(s1.length > 1){
            title = s1[0];
        }
        refresh(title, page);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.RESULT_ACTIVITY_REG_DEFAULT) {
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
        startActivityForResult(intent, Constant.RESULT_ACTIVITY_REG_DEFAULT);
    }

    @Override
    public void onReload() {
        mView.resetLoading();
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

    public void refresh(String key, int page) {
        HttpRequestImpl.getInstance().searchAlbum(currentUser(), key, page + "", new HttpRequestListener<List<Album>>() {
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
                Log.e(mTag, msg);
//                ShowToastView(msg);
                mView.stopRefreshLoadMore(refresh);
            }
        });
    }

}
