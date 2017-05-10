package org.acg12.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.acg12.R;
import org.acg12.bean.Video;
import org.acg12.listener.HttpRequestListener;
import org.acg12.listener.ItemClickSupport;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.PreviewBangumiView;

public class PreviewBangumiActivity extends PresenterActivityImpl<PreviewBangumiView> implements View.OnClickListener ,
        ItemClickSupport.OnItemClickListener{

    String bangumiId = "";
    boolean hasShow = false;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        replace = false;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        bangumiId = getIntent().getExtras().getString("bangumiId");
        refresh();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        videoUrlOrXml(mView.getEpisode(position));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.iv_video_arrow){
            if(hasShow){
                hasShow = false;
            } else {
                hasShow = true;
            }
            mView.hasShowDuration(hasShow);
        }
    }

    public void refresh(){
        HttpRequestImpl.getInstance().bangumiPreview(bangumiId, new HttpRequestListener<Video>() {
            @Override
            public void onSuccess(Video result) {
                mView.bindData(result);
                mView.stopRefreshLoadMore(true);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag , msg);
                ShowToastView(msg);
                mView.stopRefreshLoadMore(false);
            }
        });
    }

    public void videoUrlOrXml(String bgId) {
        HttpRequestImpl.getInstance().playBangumi(bgId, new HttpRequestListener<Video>() {
            @Override
            public void onSuccess(Video result) {

            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag , msg);
                ShowToastView(msg);
            }
        });
    }

}
