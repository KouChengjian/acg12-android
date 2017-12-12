package org.acg12.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import org.acg12.bean.Video;
import org.acg12.listener.HttpRequestListener;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.PlayBungumiView;

public class PlayBungumiActivity extends  PresenterActivityImpl<PlayBungumiView> {

    public Video video;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        replace = false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        video = (Video)getIntent().getExtras().getSerializable("video");
        getVideoUrlOrXml(video.getAid());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getVideoUrlOrXml(String av) {
        HttpRequestImpl.getInstance().playBangumi(av, new HttpRequestListener<Video>() {
            @Override
            public void onSuccess(Video result) {
                mView.setPlayer(result);
//                LogUtil.e(result.toString());
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag , msg);
                ShowToastView(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView.getDanmakuVideoPlayer().release();
        mView.getDanmakuVideoPlayer().getDanmakuView().release();
    }
}
