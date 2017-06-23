package org.acg12.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.acg12.bean.Album;
import org.acg12.conf.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.listener.ItemClickSupport;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.activity.PreviewAlbumActivity;
import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.ui.views.SearchAlbumView;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.IRecycleView;

import java.util.List;

public class SearchAlbumFragment extends PresenterFragmentImpl<SearchAlbumView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener{

    String title = "";
    int page = 1;
    boolean refresh = true;

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
        refresh(title , page);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constant.START_ACTIVITY_RESULT){
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
        Intent intent = new Intent(mContext , PreviewAlbumActivity.class  );
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        PreviewAlbumActivity.mList = mView.getAlbumList();
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.START_ACTIVITY_RESULT);
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        refresh(title , page++);
    }

    @Override
    public void onRefresh() {
        refresh = true;
        refresh(title , page);
    }

    public void refresh(String key , int page){
        if(!ViewUtil.isNetConnected(mContext)) return;
        HttpRequestImpl.getInstance().searchAlbum(key, page+"",new HttpRequestListener<List<Album>>() {
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
