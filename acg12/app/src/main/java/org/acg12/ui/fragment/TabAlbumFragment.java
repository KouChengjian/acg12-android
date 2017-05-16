package org.acg12.ui.fragment;

import android.app.Activity;
import android.content.Intent;
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
import org.acg12.ui.activity.PreviewAlbumActivity;
import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.ui.views.TabAlbumView;
import org.acg12.utlis.LogUtil;
import org.acg12.widget.IRecycleView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TabAlbumFragment extends PresenterFragmentImpl<TabAlbumView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener {

    boolean refresh = true;
    String boardId = "";
    public static List<Album> mList = null;


    public static TabAlbumFragment newInstance(String boardId) {
        TabAlbumFragment fragment = new TabAlbumFragment();
        Bundle args = new Bundle();
        args.putString("boardId", boardId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        boardId = getArguments().getString("boardId");
        mList = mView.getAlbumList();
        refresh(boardId);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constant.START_ACTIVITY_RESULT){
                int position = data.getExtras().getInt("position");
                mView.MoveToPosition(position);
            }
        }

    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(mContext , PreviewAlbumActivity.class  );
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        mList = mView.getAlbumList();
        //bundle.putSerializable("albumList", (Serializable)(mView.getAlbumList()));
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.START_ACTIVITY_RESULT);
    }

    @Override
    public void onRefresh() {
        refresh = true;
        refresh(boardId);
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
                LogUtil.e(mTag , msg);
                ShowToastView(msg);
                mView.stopRefreshLoadMore(refresh);
            }
        });
    }


}
