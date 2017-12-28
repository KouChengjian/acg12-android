package org.acg12.ui.views;

import android.app.Activity;
import android.view.View;

import com.acg12.kk.ui.ViewImpl;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;

import org.acg12.R;
import org.acg12.entity.Video;
import org.acg12.listener.SampleListener;
import org.acg12.widget.DanmakuVideoPlayer;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/13.
 */
public class PlayBungumiView extends ViewImpl {

    @BindView(R.id.danmaku_player)
    DanmakuVideoPlayer danmakuVideoPlayer;

//    private OrientationUtils orientationUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_play_bungumi;
    }

    @Override
    public void created() {
        super.created();
        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
        //必须在setUp之前设置


        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        //String url = "https://res.exexm.com/cw_145225549855002";
        danmakuVideoPlayer.setUp("", true, null, "");

        //增加封面
//        ImageView imageView = new ImageView(getContext());
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.kk_bg_loading_pic);
//        danmakuVideoPlayer.setThumbImageView(imageView);

        danmakuVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        danmakuVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        danmakuVideoPlayer.getFullscreenButton().setVisibility(View.GONE);
//        danmakuVideoPlayer.

        //外部辅助的旋转，帮助全屏
//        orientationUtils = new OrientationUtils((Activity) getContext(), danmakuVideoPlayer);
        //初始化不打开外部的旋转
//        orientationUtils.setEnable(false);

        danmakuVideoPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        danmakuVideoPlayer.setRotateViewAuto(false);
        danmakuVideoPlayer.setLockLand(false);
        danmakuVideoPlayer.setShowFullAnimation(false);
        danmakuVideoPlayer.setNeedLockFull(true);


        //detailPlayer.setOpenPreView(true);


//        danmakuVideoPlayer.startPlayLogic();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        danmakuVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
//                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                //danmakuVideoPlayer.startWindowFullscreen(DanmkuVideoActivity.this, true, true);
            }
        });

        danmakuVideoPlayer.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
//                orientationUtils.setEnable(true);
//                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
//                if (orientationUtils != null) {
//                    orientationUtils.backToProtVideo();
//                }
            }

            @Override
            public void onClickStop(String url, Object... objects) {
                super.onClickStop(url, objects);
                GSYVideoManager.onPause();
            }

            @Override
            public void onClickResume(String url, Object... objects) {
                super.onClickResume(url, objects);
                GSYVideoManager.onResume();
            }
        });

        danmakuVideoPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
//                if (orientationUtils != null) {
//                    配合下方的onConfigurationChanged
//                    orientationUtils.setEnable(!lock);
//                }
            }
        });

        danmakuVideoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
    }

    public void setPlayer(Video result){
        danmakuVideoPlayer.setVideStartInfo("【完成】\n全舰弹幕填装...");
        danmakuVideoPlayer.initDanmaku(result);
    }

    public DanmakuVideoPlayer getDanmakuVideoPlayer(){
        return danmakuVideoPlayer;
    }


}
