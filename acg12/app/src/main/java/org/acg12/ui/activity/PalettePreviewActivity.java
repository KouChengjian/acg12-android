package org.acg12.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.acg12.bean.Album;
import org.acg12.bean.Palette;
import org.acg12.config.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.listener.ItemClickSupport;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.fragment.TabAlbumFragment;
import org.acg12.views.PalettePreviewView;
import org.acg12.widget.IRecycleView;

import java.util.List;

public class PalettePreviewActivity extends PresenterActivityImpl<PalettePreviewView> implements IRecycleView.LoadingListener ,
        SwipeRefreshLayout.OnRefreshListener ,ItemClickSupport.OnItemClickListener{

    Palette palette;
    boolean refresh = true;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        palette = (Palette)getIntent().getSerializableExtra("palette");
        mView.bindData(palette);
        refresh(palette.getBoardId() , "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

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
