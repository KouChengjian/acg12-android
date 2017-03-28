package com.acg12.player.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.acg12.player.listener.MediaPlayerListener;

/**
 * Created by kouchengjian on 17/3/25.
 */
public class BaseVideoPlayer extends FrameLayout implements MediaPlayerListener{

    public static final int SMALL_ID = 84778;

    protected static final int FULLSCREEN_ID = 85597;

    protected static long CLICK_QUIT_FULLSCREEN_TIME = 0;

    protected boolean mActionBar = false;//是否需要在利用window实现全屏幕的时候隐藏actionbar

    protected boolean mStatusBar = false;//是否需要在利用window实现全屏幕的时候隐藏statusbar

    protected boolean mHideKey = true;//是否隐藏虚拟按键

    protected boolean mCache = false;//是否播边边缓冲

    private boolean mShowFullAnimation = true;//是否使用全屏动画效果

    protected boolean mNeedShowWifiTip = true; //是否需要显示流量提示

    protected int[] mListItemRect;//当前item框的屏幕位置

    protected int[] mListItemSize;//当前item的大小

    protected int mCurrentState = -1; //当前的播放状态

    protected int mRotate = 0; //针对某些视频的旋转信息做了旋转处理

    private int mSystemUiVisibility;

    protected float mSpeed = 1;//播放速度，只支持6.0以上

    protected boolean mRotateViewAuto = true; //是否自动旋转

    protected boolean mIfCurrentIsFullscreen = false;//当前是否全屏

    protected boolean mLockLand = false;//当前全屏是否锁定全屏

    protected boolean mLooping = false;//循环

    protected boolean mHadPlay = false;//是否播放过

    protected boolean mCacheFile = false; //是否是缓存的文件

    public BaseVideoPlayer(Context context) {
        super(context);
    }

    public BaseVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onPrepared() {

    }

    @Override
    public void onAutoCompletion() {

    }

    @Override
    public void onCompletion() {

    }

    @Override
    public void onBufferingUpdate(int percent) {

    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public void onError(int what, int extra) {

    }

    @Override
    public void onInfo(int what, int extra) {

    }

    @Override
    public void onVideoSizeChanged() {

    }

    @Override
    public void onBackFullscreen() {

    }

    @Override
    public void onVideoPause() {

    }

    @Override
    public void onVideoResume() {

    }
}
