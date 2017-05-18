package org.acg12.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.acg12.bean.Album;
import org.acg12.bean.Palette;
import org.acg12.config.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.listener.ItemClickSupport;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.PreviewPaletteView;
import org.acg12.widget.IRecycleView;

import java.util.List;

public class PreviewPaletteActivity extends PresenterActivityImpl<PreviewPaletteView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener ,View.OnClickListener{

    Palette palette;
    boolean refresh = true;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        PreviewAlbumActivity.mList = null;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        palette = (Palette)getIntent().getSerializableExtra("palette");
        mView.bindData(palette);
        refresh(palette.getBoardId() , "");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constant.START_ACTIVITY_RESULT){
                int position = data.getExtras().getInt("position");
                List<Album> list = mView.getAlbumList();
                list = PreviewAlbumActivity.mList;
                mView.MoveToPosition(position);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            finish();
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
    public void onRefresh() {
        refresh = true;
        refresh(palette.getBoardId() ,"");
    }

    @Override
    public void onLoadMore() {
        refresh = false;
        refresh(palette.getBoardId() ,mView.getPicId());
    }

    public void refresh(String boardId ,String pinId){
        HttpRequestImpl.getInstance().palettePreview(boardId , pinId ,new HttpRequestListener<List<Album>>() {
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
