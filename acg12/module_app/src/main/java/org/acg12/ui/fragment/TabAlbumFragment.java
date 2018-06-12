package org.acg12.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.IRecycleView;

import org.acg12.conf.Constant;
import org.acg12.entity.Album;
import org.acg12.net.impl.HomeRequestImpl;
import org.acg12.ui.activity.PreviewAlbumActivity;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.TabAlbumView;

import java.util.List;

public class TabAlbumFragment extends SkinBaseFragment<TabAlbumView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener {

    boolean refresh = true;
    String boardId = "";

    public static TabAlbumFragment newInstance(String boardId) {
        TabAlbumFragment fragment = new TabAlbumFragment();
        Bundle args = new Bundle();
        args.putString("boardId", boardId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        PreviewAlbumActivity.mList = null;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        boardId = getArguments().getString("boardId");
        refresh(boardId);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constant.RESULT_ACTIVITY_REG_DEFAULT){
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
        startActivityForResult(intent, Constant.RESULT_ACTIVITY_REG_DEFAULT);
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
        HomeRequestImpl.getInstance().albumList(currentUser(),pinId, new HttpRequestListener<List<Album>>() {
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
