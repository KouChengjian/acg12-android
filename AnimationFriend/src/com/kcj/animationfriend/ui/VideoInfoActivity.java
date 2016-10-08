package com.kcj.animationfriend.ui;

import io.vov.vitamio.utils.FileUtils;
import io.vov.vitamio.widget.CenterLayout;
import io.vov.vitamio.widget.OutlineTextView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.DataFormatException;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.android.DanmakuGlobalConfig;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection.Response;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.InjectView;

import com.android.material.widget.ProgressView;
import com.kcj.animationfriend.PlayerService;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.FindScrollTabPagerdAdapter;
import com.kcj.animationfriend.bean.Video;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.util.AppUtil;
import com.kcj.animationfriend.util.CompressionTools;
import com.kcj.animationfriend.util.DeviceUtils;
import com.kcj.animationfriend.util.ImageUtils;
import com.kcj.animationfriend.util.MediaUtils;
import com.kcj.animationfriend.util.PixelUtil;
import com.kcj.animationfriend.util.SharePreferenceUtil;
import com.kcj.animationfriend.view.MediaController;
import com.kcj.animationfriend.view.ScrollTabHolder;
import com.kcj.animationfriend.view.VideoView;
import com.liteutil.async.AsyncTask;
import com.liteutil.util.Log;

/**
 * @ClassName: VideoInfoActivity
 * @Description: 视频信息
 * @author: KCJ
 * @date: 2015-9-25
 */
public class VideoInfoActivity extends BaseActivity implements MediaController.MediaPlayerControl,
    VideoView.SurfaceCallback ,OnClickListener ,ScrollTabHolder , ParameCallBack{
	
	@InjectView(R.id.video_container)
	protected LinearLayout video_container;
	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.video_root)
	protected CenterLayout mViewRoot;
	@InjectView(R.id.rl_video_ctl)
	protected RelativeLayout rl_video_ctl;
	@InjectView(R.id.video)
	protected VideoView mVideoView;      // 播放界面
	@InjectView(R.id.rl_video_load_ic)
	protected RelativeLayout rl_video_load_ic;
	@InjectView(R.id.iv_video_load_start)
	protected ImageView iv_video_load_start;
	@InjectView(R.id.sv_danmaku)
	protected IDanmakuView mDanmakuView; // 弹幕控件
	@InjectView(R.id.video_start) 
	protected View startVideo;           // 初始化
	@InjectView(R.id.video_start_info)
	protected TextView startVideoInfo; 
	@InjectView(R.id.bili_anim) 
	protected ImageView biliAnim; 
	private AnimationDrawable anim;
	@InjectView(R.id.video_loading) 
	protected View mVideoLoadingLayout;  // 缓存
	@InjectView(R.id.video_loading_text) 
	protected TextView mVideoLoadingText;
	@InjectView(R.id.video_load_progress) 
	protected ProgressView video_load_progress;
	@InjectView(R.id.subtitle_container)
	protected View mSubtitleContainer;   // 
	@InjectView(R.id.subtitle_text)
	protected OutlineTextView mSubtitleText;
	@InjectView(R.id.subtitle_image)
	protected ImageView mSubtitleImage;
	// 其他
	@InjectView(R.id.top_view)
	protected LinearLayout top_view;
	@InjectView(R.id.pager)
	protected ViewPager viewPager;
	@InjectView(R.id.indicator)
	protected TabLayout tabLayout;
	protected FindScrollTabPagerdAdapter pagerdAdapter;
	protected List<Video> videoList = new ArrayList<Video>();
	
	// 传递数据
	protected Video video;
	private String av;
	private String page;
	private String mDisplayName;
	private boolean mNeedLock;
	private boolean mFromStart;
	private boolean mSaveUri;
	private float mStartPos;
	private int mLoopCount;
	private int mParentId;
	private String mSubPath;
	private boolean mSubShown;
	private boolean mIsHWCodec = false;
	protected List<Video> videoInfoList = new ArrayList<Video>();
	
	// 播放
	public static final int RESULT_FAILED = -7; // 播放失败
	private int mVideoMode = VideoView.VIDEO_LAYOUT_SCALE;
	private boolean isfirst = true;
	private boolean mSurfaceCreated = false; // surface是否被创建
	private boolean mServiceConnected = false; // 当前activity是否绑定service
	private boolean mCloseComplete = false;
	private PlayerService playerService; // 服务
	private MediaController mMediaController;
	private ServiceConnection vPlayerServiceConnection;
	
	// 参数
	private boolean mHeadsetPlaying = false;
//	private static final long EXIT_INTERVAL_TIME = 2000;
//	private long touchTime = 0;
	private boolean mCreated = false;
	private boolean isload = false;
	private String mBatteryLevel; // 电量
	private String startText = "初始化播放器...";
	private BaseDanmakuParser mParser;
	private String danmakuPath; // 弹幕路径
	private Uri mUri; 
	private AtomicBoolean mOpened = new AtomicBoolean(Boolean.FALSE);
	
	// 监听
	private Object mOpenLock = new Object();
	private static final int OPEN_FILE       = 0;  // 播放视频初始
	private static final int OPEN_START      = 1;  // 开始
	private static final int OPEN_SUCCESS    = 2;  // 成功
	private static final int OPEN_FAILED     = 3;  // 失败
	private static final int HW_FAILED       = 4;  // 失败
	private static final int LOAD_PREFS      = 5;  // 加载控制台
	private static final int BUFFER_START    = 11; // 开始缓存
	private static final int BUFFER_PROGRESS = 12; // 缓存进度
	private static final int BUFFER_COMPLETE = 13; // 缓存完成
	private static final int CLOSE_START     = 21; // 结束启动
	private static final int CLOSE_COMPLETE  = 22; // 结束完成
	private static final int SUBTITLE_TEXT   = 0;  // 字幕-文本
	private static final int SUBTITLE_BITMAP = 1;  // 字幕-位图
	// 广播
	private boolean mReceiverRegistered = false;
	private ScreenReceiver mScreenReceiver;           // 屏幕亮
	private static final IntentFilter SCREEN_FILTER  = new IntentFilter(Intent.ACTION_SCREEN_ON);
	private HeadsetPlugReceiver mHeadsetPlugReceiver; // 判断耳麦拔插事件
	private static final IntentFilter HEADSET_FILTER = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
	private UserPresentReceiver mUserPresentReceiver; // 屏幕解锁
	private static final IntentFilter USER_PRESENT_FILTER = new IntentFilter(Intent.ACTION_USER_PRESENT);
	private BatteryReceiver mBatteryReceiver;         // 检测手机电量情况
	private static final IntentFilter BATTERY_FILTER = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	static {
		SCREEN_FILTER.addAction(Intent.ACTION_SCREEN_OFF);
	}
	protected VideoViewPlayTask videoViewPlayTask;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_video_info1);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)){
			return;
		}
		initViews();
		initEvent();
		initDatas();
	}
	
	@Override
	public void initViews() {
		setTitle(R.string.video_info);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// bindService 方法执行成功
		vPlayerServiceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				playerService = ((PlayerService.LocalBinder) service).getService();
				mServiceConnected = true;
				if (mSurfaceCreated)
					playerServiceHandler.sendEmptyMessage(OPEN_FILE); // 初始化播放器
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				playerService = null;
				mServiceConnected = false;
			}
		};
		// 此activity可以控制声音
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		// 获取getIntent()中的数据
		parseIntent(getIntent());
		// 初始化控件
		getWindow().setBackgroundDrawable(null);
		mVideoView.initialize(this, this, mIsHWCodec); // 播放控件
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		startVideo.setVisibility(View.VISIBLE);
		anim = (AnimationDrawable) biliAnim.getBackground();
		anim.start();
		video_load_progress.start();
		DanmakuGlobalConfig.DEFAULT
		.setDanmakuStyle(DanmakuGlobalConfig.DANMAKU_STYLE_STROKEN, 3)
		.setDuplicateMergingEnabled(false);
		// 注册广播
		manageReceivers();
		// 初始化pagerview
		initPagerView();
		mCreated = true;
		startText = startText + "【完成】\n解析视频地址..." +"【完成】\n解析视频信息..." ;
		startVideoInfo.setText(startText);
	}
	
	@Override
	public void initEvent() {
		iv_video_load_start.setOnClickListener(this);
	}
	
	@Override
	public void initDatas() {
		if(videoViewPlayTask != null){
			videoViewPlayTask.cancel(true);
		}
		videoViewPlayTask = new VideoViewPlayTask();
//		videoViewPlayTask.execute("");
	}
	
	private void parseIntent(Intent i) {
		video = (Video) getIntent().getSerializableExtra("videoItemdata");
		av = video.getAid(); // 视频id
		page = "1"; // 视频页数
		mDisplayName = video.getTitle(); // 视频名称
		mNeedLock = i.getBooleanExtra("lockScreen", false);
		mFromStart = i.getBooleanExtra("fromStart", false);
		mSaveUri = i.getBooleanExtra("saveUri", true);
		mStartPos = i.getFloatExtra("startPosition", -1.0f);
		mLoopCount = i.getIntExtra("loopCount", 1);
		mParentId = i.getIntExtra("parentId", 0);
		mSubPath = i.getStringExtra("subPath");
		mSubShown = i.getBooleanExtra("subShown", true);
		mIsHWCodec = i.getBooleanExtra("hwCodec", false);
		Log.i("L: %b, N: %s, S: %b, P: %f, LP: %d", mNeedLock, mDisplayName,
				mFromStart, mStartPos, mLoopCount);
	}
	
	private void manageReceivers() {
		if (!mReceiverRegistered) {
			mScreenReceiver = new ScreenReceiver();
			registerReceiver(mScreenReceiver, SCREEN_FILTER);
			mUserPresentReceiver = new UserPresentReceiver();
			registerReceiver(mUserPresentReceiver, USER_PRESENT_FILTER);
			mBatteryReceiver = new BatteryReceiver();
			registerReceiver(mBatteryReceiver, BATTERY_FILTER);
			mHeadsetPlugReceiver = new HeadsetPlugReceiver();
			registerReceiver(mHeadsetPlugReceiver, HEADSET_FILTER);
			mReceiverRegistered = true;
		} else {
			try {
				if (mScreenReceiver != null)
					unregisterReceiver(mScreenReceiver);
				if (mUserPresentReceiver != null)
					unregisterReceiver(mUserPresentReceiver);
				if (mHeadsetPlugReceiver != null)
					unregisterReceiver(mHeadsetPlugReceiver);
				if (mBatteryReceiver != null)
					unregisterReceiver(mBatteryReceiver);
			} catch (IllegalArgumentException e) {
			}
			mReceiverRegistered = false;
		}
	}
	
	public void initPagerView(){
		pagerdAdapter = new FindScrollTabPagerdAdapter(getSupportFragmentManager(),video ,this);
		viewPager.setOffscreenPageLimit(1);
		viewPager.setAdapter(pagerdAdapter);
		tabLayout.setupWithViewPager(viewPager);
		viewPager.addOnPageChangeListener(getViewPagerPageChangeListener());
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (!mCreated)
			return;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (!mCreated)
			return;
		if (isInitialized()) {
			Log.i("TAG", "isInitialized");
			KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
			if (!keyguardManager.inKeyguardRestrictedInputMode()) {
				startPlayer(); // 开始播放
			}
		} else {
			Log.i("TAG", "!isInitialized");
			if (mCloseComplete) {
				Log.i("TAG", "mCloseComplete");
				reOpen();
			}
		}
		if (mDanmakuView != null && mDanmakuView.isPrepared()&& mDanmakuView.isPaused()) {
			mDanmakuView.resume();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.e("TAG", "onPause");
		if (!mCreated)
			return;
		if (isInitialized()) {
			if (playerService != null && playerService.isPlaying()) {
				stopPlayer();
			}
		}
		if (mDanmakuView != null && mDanmakuView.isPrepared()) {
			mDanmakuView.pause();
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.e("TAG", "onStop");
		if (!mCreated)
			return;
		if (isInitialized()) {
			playerService.releaseSurface();
		}
		if (mServiceConnected) {
			unbindService(vPlayerServiceConnection);
			mServiceConnected = false;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("TAG", "onDestroy");
		if (!mCreated)
			return;
		if(videoViewPlayTask != null){
			videoViewPlayTask.cancel(true);
		}
		manageReceivers();
		if (isInitialized() && !playerService.isPlaying())
			release();
		if (mMediaController != null)
			mMediaController.release();
		if (mDanmakuView != null) {
			mDanmakuView.release();
			mDanmakuView = null;
		}
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
	public void onConfigurationChanged(Configuration newConfig) {
		if (isInitialized()) {
			setVideoLayout();
			attachMediaController();
		}
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.e("onNewIntent","1111");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_video_load_start:
			rl_video_load_ic.setVisibility(View.GONE);
			anim.start();
			Intent serviceIntent = new Intent(getApplicationContext(),PlayerService.class);
			serviceIntent.putExtra("isHWCodec", mIsHWCodec);
			bindService(serviceIntent, vPlayerServiceConnection,Context.BIND_AUTO_CREATE);
			startText = startText + "【完成】\n缓存中...";
			startVideoInfo.setText(startText);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void adjustScroll(int scrollHeight, int headerHeight) {}

	@Override
	public void onListViewScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount, int pagePosition) {
		if (viewPager.getCurrentItem() == pagePosition) {
			scrollHeader(getScrollY(view));
		}
	}

	@Override
	public void onScrollViewScroll(ScrollView view, int x, int y, int oldX,
			int oldY, int pagePosition) {
		if (viewPager.getCurrentItem() == pagePosition) {
			scrollHeader(view.getScrollY());
		}
	}
	
	@Override
	public void onCall(Object object) {
		if(object instanceof Video){
			Video ob = (Video)object;
			if(ob.getAid() != null && !ob.getAid().isEmpty()){
				video.setAid(ob.getAid());
				av = video.getAid();
			}
			startText = startText + "【完成】\n全舰弹幕填装...";
			startVideoInfo.setText(startText);
			videoViewPlayTask.execute("");
		}
	}
	
    /*____________回调 start ____________*/

	@Override
	public void onSurfaceCreated(SurfaceHolder holder) {
		Log.i("onSurfaceCreated");
		mSurfaceCreated = true;
		if (mServiceConnected)
			playerServiceHandler.sendEmptyMessage(OPEN_FILE);
		if (playerService != null)
			playerService.setDisplay(holder);
	}

	@Override
	public void onSurfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		if (playerService != null) {
			setVideoLayout();
		}		
	}

	@Override
	public void onSurfaceDestroyed(SurfaceHolder holder) {
		if (playerService != null && playerService.isInitialized()) {
			if (playerService.isPlaying()) {
				playerService.stop();
				playerService.setState(PlayerService.STATE_NEED_RESUME);
			}
			playerService.releaseSurface();
			if (playerService.needResume()) {
				playerService.start();
				if (mDanmakuView.isPaused())
					mDanmakuView.resume();
				else
					mDanmakuView.start();
			}
		}
	}

	@Override
	public void start() {
		if (isInitialized())
			playerService.start();
		if (mDanmakuView.isPaused())
			mDanmakuView.resume();
		else
			mDanmakuView.start();
	}

	@Override
	public void pause() {
		if (isInitialized())
			playerService.stop();
		mDanmakuView.pause();
	}

	@Override
	public void stop() {
		onBackPressed();
	}

	@Override
	public void seekTo(long pos) {
		if (isInitialized())
			playerService.seekTo((float) ((double) pos / playerService.getDuration()));
		mDanmakuView.seekTo(pos);
		mDanmakuView.pause();
	}

	@Override
	public boolean isPlaying() {
		if (isInitialized())
			return playerService.isPlaying();
		return false;
	}

	@Override
	public long getDuration() {
		if (isInitialized())
			return playerService.getDuration();
		return 0;
	}

	@Override
	public long getCurrentPosition() {
		if (isInitialized())
			return playerService.getCurrentPosition();
		return (long) (getStartPosition() * playerService.getDuration());
	}

	@Override
	public int getBufferPercentage() {
		if (isInitialized())
			return (int) (playerService.getBufferProgress() * 100);
		return 0;
	}

	@Override
	public void previous() {}

	@Override
	public void next() {
		if (mDanmakuView != null && mDanmakuView.isPrepared()) {
			mDanmakuView.pause();
			mDanmakuView.release();
		}
		if (isInitialized()) {
			playerService.stop();
			playerService.releaseSurface();
			playerService.release();
		}
//		if(videoViewPlayTask != null){
//			videoViewPlayTask.cancel(true);
//		}
//		videoViewPlayTask = new VideoViewPlayTask();
//		videoViewPlayTask.execute("");
		Intent serviceIntent = new Intent(getApplicationContext(),PlayerService.class);
		serviceIntent.putExtra("isHWCodec", mIsHWCodec);
		bindService(serviceIntent, vPlayerServiceConnection,Context.BIND_AUTO_CREATE);
	}

	@Override
	public long goForward() {
		return 0;
	}

	@Override
	public long goBack() {
		return 0;
	}

	@Override
	public void toggleVideoMode(int mode) {
		mVideoMode = mode;
		setVideoLayout();
	}

	@Override
	public void showMenu() {}

	@Override
	public void removeLoadingView() {
		mVideoLoadingLayout.setVisibility(View.GONE);
	}

	private static final int VIDEO_MAXIMUM_HEIGHT = 2048;
	private static final int VIDEO_MAXIMUM_WIDTH = 2048;
	@Override
	public float scale(float scaleFactor) {
		float userRatio = Constant.DEFAULT_ASPECT_RATIO;
		int videoWidth = playerService.getVideoWidth();
		int videoHeight = playerService.getVideoHeight();
		float videoRatio = playerService.getVideoAspectRatio();
		float currentRatio = mVideoView.mVideoHeight / (float) videoHeight;

		currentRatio += (scaleFactor - 1);
		if (videoWidth * currentRatio >= VIDEO_MAXIMUM_WIDTH)
			currentRatio = VIDEO_MAXIMUM_WIDTH / (float) videoWidth;

		if (videoHeight * currentRatio >= VIDEO_MAXIMUM_HEIGHT)
			currentRatio = VIDEO_MAXIMUM_HEIGHT / (float) videoHeight;

		if (currentRatio < 0.5f)
			currentRatio = 0.5f;

		mVideoView.mVideoHeight = (int) (videoHeight * currentRatio);
		mVideoView.setVideoLayout(mVideoMode, userRatio, videoWidth,
				videoHeight, videoRatio);
		return currentRatio;
	}

	@Override
	public void snapshot() {
		if (!com.kcj.animationfriend.util.FileUtils.sdAvailable()) {
			ShowToast(R.string.file_explorer_sdcard_not_available);
		} else {
			Uri imgUri = null;
			Bitmap bitmap = playerService.getCurrentFrame();
			if (bitmap != null) {
				File screenshotsDirectory = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
								+ Constant.SNAP_SHOT_PATH);
				if (!screenshotsDirectory.exists()) {
					screenshotsDirectory.mkdirs();
				}

				File savePath = new File(
						screenshotsDirectory.getPath()
								+ "/"
								+ new SimpleDateFormat("yyyyMMddHHmmss")
										.format(new Date()) + ".jpg");
				if (ImageUtils.saveBitmap(savePath.getPath(), bitmap)) {
					imgUri = Uri.fromFile(savePath);
				}
			}
			if (imgUri != null) {
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
						imgUri));
				ShowToast(getString(R.string.video_screenshot_save_in,
						imgUri.getPath()));
			} else {
				ShowToast(R.string.video_screenshot_failed);
			}
		}
	}

	@Override
	public void setDanmakushow(boolean isShow) {
		if (mDanmakuView != null) {
			if (isShow) {
				mDanmakuView.show();
			} else {
				mDanmakuView.hide();
			}
		}
	}
	
	@Override
	public void rotatingScreen(){
//		setVH();
//		previous();
		next();
	}
	
	/*____________回调 end ____________*/
	
	private int getScrollY(AbsListView view) {
		View child = view.getChildAt(0);
		if (child == null) {
			return 0;
		}

		int firstVisiblePosition = view.getFirstVisiblePosition();
		int top = child.getTop();
		int headerHeight = 0; // 50
		if (firstVisiblePosition >= 1) {
			headerHeight = top_view.getHeight();
		}
		
		return -top + firstVisiblePosition * child.getHeight() + headerHeight;
	}
	
	private void scrollHeader(int scrollY) {
		float translationY = Math.max(-scrollY, -top_view.getHeight()+tabLayout.getHeight());
		Log.i("scrollHeader", scrollY+"=========="+translationY);
		top_view.setTranslationY(translationY);
		// mTopImage.setTranslationY(-translationY / 3);
	}
	
	private void setVideoLayout() {
		mVideoView.setVideoLayout(mVideoMode, Constant.DEFAULT_ASPECT_RATIO,
				playerService.getVideoWidth(), playerService.getVideoHeight(),
				playerService.getVideoAspectRatio());
	}
	
	private void attachMediaController() {
		if (mMediaController != null) {
			mNeedLock = mMediaController.isLocked();
			mMediaController.release();
		}
		mMediaController = new MediaController(this);
		mMediaController.setMediaPlayer(this);
		rl_video_ctl.addView(mMediaController);
		setFileName();
		setBatteryLevel();
	}
	
	private void setFileName() {
		if (mUri != null) {
			String name = null;
			if (mUri.getScheme() == null || mUri.getScheme().equals("file"))
				name = FileUtils.getName(mUri.toString());
			else
				name = mUri.getLastPathSegment();
			if (name == null)
				name = "null";
			if (mDisplayName == null)
				mDisplayName = name;
			mMediaController.setFileName(mDisplayName);
		}
	}
	
	// 设置电量
	private void setBatteryLevel() {
		if (mMediaController != null)
			mMediaController.setBatteryLevel(mBatteryLevel);
	}
	
	private void setVH() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {// 如果是竖屏则设置为横屏
        	pause();
        	toolbar.setVisibility(View.GONE);
        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
            param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
            rl_video_ctl.setLayoutParams(param);
            start();
        } else {
        	pause();
        	toolbar.setVisibility(View.VISIBLE);
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        	RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,PixelUtil.dp2px(250));
            param.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
            rl_video_ctl.setLayoutParams(param);
            quitFullScreen();
            start();
        }
    }
	
	private void quitFullScreen(){
	      final WindowManager.LayoutParams attrs = getWindow().getAttributes();
	      attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
	      getWindow().setAttributes(attrs);
	      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	  }
	
	private boolean isInitialized() {
		return (mCreated && playerService != null && playerService.isInitialized());
	}
	
	/**
	 *@ClassName: mSubHandler
	 *@Description: 弹幕内容
	 *@author: 
	 *@date: 
	 */
	@SuppressLint("HandlerLeak")
	private Handler mSubHandler = new Handler() {
		Bundle data;
		String text;
		byte[] pixels;
		int width = 0, height = 0;
		Bitmap bm = null;
		int oldType = SUBTITLE_TEXT;

		@Override
		public void handleMessage(Message msg) {
			data = msg.getData();
			switch (msg.what) {
			case SUBTITLE_TEXT:
				if (oldType != SUBTITLE_TEXT) {
					mSubtitleImage.setVisibility(View.GONE);
					mSubtitleText.setVisibility(View.VISIBLE);
					oldType = SUBTITLE_TEXT;
				}
				text = data.getString(Constant.SUB_TEXT_KEY);
				mSubtitleText.setText(text == null ? "" : text.trim());
				break;
			case SUBTITLE_BITMAP:
				if (oldType != SUBTITLE_BITMAP) {
					mSubtitleText.setVisibility(View.GONE);
					mSubtitleImage.setVisibility(View.VISIBLE);
					oldType = SUBTITLE_BITMAP;
				}
				pixels = data.getByteArray(Constant.SUB_PIXELS_KEY);
				if (bm == null || width != data.getInt(Constant.SUB_WIDTH_KEY)
						|| height != data.getInt(Constant.SUB_HEIGHT_KEY)) {
					width = data.getInt(Constant.SUB_WIDTH_KEY);
					height = data.getInt(Constant.SUB_HEIGHT_KEY);
					bm = Bitmap.createBitmap(width, height,
							Bitmap.Config.ARGB_8888);
				}
				if (pixels != null)
					bm.copyPixelsFromBuffer(ByteBuffer.wrap(pixels));
				mSubtitleImage.setImageBitmap(bm);
				break;
			}
		}
	};
	
	/**
	 *@ClassName: playerServiceHandler
	 *@Description: 开始操作视频
	 *@author: KCJ
	 *@date: 
	 */
	@SuppressLint("HandlerLeak")
	private Handler playerServiceHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case OPEN_FILE:       // 播放视频初始
				synchronized (mOpenLock) {
					if (!mOpened.get() && playerService != null) {
						mOpened.set(true);
						playerService.setVPlayerListener(vPlayerListener); // 设置播放监听
						if (playerService.isInitialized()) // 初始化时为false
							mUri = playerService.getUri(); // 获取流
						if (mVideoView != null)
							playerService.setDisplay(mVideoView.getHolder()); // 将Holder传给Service
						if (mUri != null)
							playerService.initialize(mUri, mDisplayName, mSaveUri,getStartPosition(), vPlayerListener,mParentId, mIsHWCodec);
					}
				}
				break ;
			case OPEN_START:      // 开始
				mVideoLoadingText.setText(R.string.video_layout_loading);
				setVideoLoadingLayoutVisibility(View.VISIBLE);
				break ;
			case OPEN_SUCCESS:    // 成功
				loadVPlayerPrefs();
				setVideoLoadingLayoutVisibility(View.GONE);
				setVideoLayout();
				playerService.start();
				if (mDanmakuView.isPaused())
					mDanmakuView.resume();
				else
					mDanmakuView.start();
				attachMediaController();
				break ;
			case OPEN_FAILED:     // 失败
				resultFinish(RESULT_FAILED);
				break ;
			case BUFFER_START:    // 开始缓存
				setVideoLoadingLayoutVisibility(View.VISIBLE);
				playerServiceHandler.sendEmptyMessageDelayed(BUFFER_PROGRESS, 1000);
				break ;
			case BUFFER_PROGRESS: // 缓存进度'
				if (playerService.getBufferProgress() >= 100) {
					setVideoLoadingLayoutVisibility(View.GONE);
				} else {
					mVideoLoadingText.setText(getString(
							R.string.video_layout_buffering_progress,
							playerService.getBufferProgress()));
					playerServiceHandler.sendEmptyMessageDelayed(BUFFER_PROGRESS,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                1000);
					stopPlayer();
				}
				break ;
			case BUFFER_COMPLETE: // 缓存完成
				setVideoLoadingLayoutVisibility(View.GONE);
				playerServiceHandler.removeMessages(BUFFER_PROGRESS);
				break ;
			case CLOSE_START:     // 结束启动
				mVideoLoadingText.setText(R.string.closing_file);
				setVideoLoadingLayoutVisibility(View.VISIBLE);
				break ;
			case CLOSE_COMPLETE:  // 结束完成
				mCloseComplete = true;
				break ;
			case HW_FAILED:       // 失败
				if (mVideoView != null) {
					mVideoView.setVisibility(View.GONE);
					mVideoView.setVisibility(View.VISIBLE);
					mVideoView.initialize(VideoInfoActivity.this,
							VideoInfoActivity.this, false);
				}
				break ;
			case LOAD_PREFS:      // 加载控制台
				loadVPlayerPrefs();
				break ;
			}
		}
	};
	
	/**
	 * @ClassName: PlayerService.VPlayerListener
	 * @Description: 
	 * @author: KCJ
	 * @date:  
	 */
	private PlayerService.VPlayerListener vPlayerListener = new PlayerService.VPlayerListener() {
		@Override
		public void onHWRenderFailed() {
			if (Build.VERSION.SDK_INT < 11 && mIsHWCodec) {
				playerServiceHandler.sendEmptyMessage(HW_FAILED);
				playerServiceHandler.sendEmptyMessageDelayed(HW_FAILED, 200);
			}
		}

		/**
		 * 显示文字字幕
		 */
		@Override
		public void onSubChanged(String sub) {
			Message msg = new Message();
			Bundle b = new Bundle();
			b.putString(Constant.SUB_TEXT_KEY, sub);
			msg.setData(b);
			msg.what = SUBTITLE_TEXT;
			mSubHandler.sendMessage(msg);
		}

		/**
		 * 需要显示图片字幕
		 */
		@Override
		public void onSubChanged(byte[] pixels, int width, int height) {
			Message msg = new Message();
			Bundle b = new Bundle();
			b.putByteArray(Constant.SUB_PIXELS_KEY, pixels);
			b.putInt(Constant.SUB_WIDTH_KEY, width);
			b.putInt(Constant.SUB_HEIGHT_KEY, height);
			msg.setData(b);
			msg.what = SUBTITLE_BITMAP;
			mSubHandler.sendMessage(msg);
		}

		@Override
		public void onOpenStart() {
			playerServiceHandler.sendEmptyMessage(OPEN_START);
		}

		@Override
		public void onOpenSuccess() {
			playerServiceHandler.sendEmptyMessage(OPEN_SUCCESS);
		}

		@Override
		public void onOpenFailed() {
			playerServiceHandler.sendEmptyMessage(OPEN_FAILED);
		}

		@Override
		public void onBufferStart() {
			playerServiceHandler.sendEmptyMessage(BUFFER_START);
			stopPlayer();
		}

		@Override
		public void onBufferComplete() {
			Log.i("VideoActivity#onBufferComplete " + playerService.needResume());
			if (isfirst) {
				startVideo.setVisibility(View.GONE);
				anim.stop();
				isfirst = false;
			}
			playerServiceHandler.sendEmptyMessage(BUFFER_COMPLETE);
			if (playerService != null && !playerService.needResume())
				startPlayer();
		}

		@Override
		public void onDownloadRateChanged(int kbPerSec) {
			if (!MediaUtils.isNative(mUri.toString())&& mMediaController != null) {
				mMediaController.setDownloadRate(String.format("%dKB/s",kbPerSec));
			}
		}
		
		@Override
		public void onPlaybackComplete() {
			if (mLoopCount == 0 || mLoopCount-- > 1) {
				playerService.start();
				if (mDanmakuView.isPaused())
					mDanmakuView.resume();
				else
					mDanmakuView.start();
				playerService.seekTo(0);
			} else {
				resultFinish(RESULT_OK);
			}
		}

		@Override
		public void onCloseStart() {
			playerServiceHandler.sendEmptyMessage(CLOSE_START);
		}

		@Override
		public void onCloseComplete() {
			playerServiceHandler.sendEmptyMessage(CLOSE_COMPLETE);
		}

		@Override
		public void onVideoSizeChanged(int width, int height) {
			if (mVideoView != null) {
				setVideoLayout();
			}
		}
	};
	
	
	private float getStartPosition() {
		if (mFromStart)
			return 1.1f;
		if (mStartPos <= 0.0f || mStartPos >= 1.0f)
			return SharePreferenceUtil.getFloat(mUri
					+ Constant.SESSION_LAST_POSITION_SUFIX, 7.7f);
		return mStartPos;
	}
	
	/**
	 *  显示或隐藏缓冲布局
	 */
	private void setVideoLoadingLayoutVisibility(int visibility) {
		if (mVideoLoadingLayout != null) {
			mVideoLoadingLayout.setVisibility(visibility);
		}
	}
	
	/**
	 *  开始播放
	 */
	protected void startPlayer() {
		if (isInitialized() && mScreenReceiver.screenOn
				&& !playerService.isBuffering() && isload) {
			Log.i("VideoActivity#startPlayer");
			if (!playerService.isPlaying()) {
				playerService.start();
				if (mDanmakuView.isPaused())
					mDanmakuView.resume();
				else
					mDanmakuView.start();
			}
		}
	}
	
	/**
	 *  结束播放
	 */
	protected void stopPlayer() {
		if (isInitialized()) {
			playerService.stop();
			if (mDanmakuView != null) {
				if (mDanmakuView.isPrepared() && mDanmakuView.isShown());
				    mDanmakuView.pause();
			}
		}
	}
	
	/**
	 *  加载播放控制
	 */
	private void loadVPlayerPrefs() {
		if (!isInitialized())
			return;
		playerService.setBuffer(Constant.DEFAULT_BUF_SIZE);
		playerService.setVideoQuality(Constant.DEFAULT_VIDEO_QUALITY);
		playerService.setDeinterlace(Constant.DEFAULT_DEINTERLACE);
		playerService.setVolume(Constant.DEFAULT_STEREO_VOLUME,
				Constant.DEFAULT_STEREO_VOLUME);
		playerService.setSubEncoding(Constant.DEFAULT_SUB_ENCODING);
		MarginLayoutParams lp = (MarginLayoutParams) mSubtitleContainer
				.getLayoutParams();
		lp.bottomMargin = (int) Constant.DEFAULT_SUB_POS;
		mSubtitleContainer.setLayoutParams(lp);
		playerService.setSubShown(mSubShown);
		setTextViewStyle(mSubtitleText);
		if (!TextUtils.isEmpty(mSubPath))
			playerService.setSubPath(mSubPath);
		if (mVideoView != null && isInitialized())
			setVideoLayout();
	}
	
	private void setTextViewStyle(OutlineTextView v) {
		v.setTextColor(Constant.DEFAULT_SUB_COLOR);
		v.setTypeface(Constant.getTypeface(Constant.DEFAULT_TYPEFACE_INT),
				Constant.DEFAULT_SUB_STYLE);
		v.setShadowLayer(Constant.DEFAULT_SUB_SHADOWRADIUS, 0, 0,
				Constant.DEFAULT_SUB_SHADOWCOLOR);
	}
	
	
	/**
	 * 播放失败
	 */
	private void resultFinish(int resultCode) {
		applyResult(resultCode);
		if (DeviceUtils.hasICS() && resultCode != RESULT_FAILED) {
			android.os.Process.killProcess(android.os.Process.myPid());
		} else {
			finish();
		}
	}
	
	private void applyResult(int resultCode) {
		playerServiceHandler.removeMessages(BUFFER_PROGRESS);
		Intent i = new Intent();
		i.putExtra("filePath", mUri.toString());
		if (isInitialized()) {
			i.putExtra("position", (double) playerService.getCurrentPosition()
					/ playerService.getDuration());
			i.putExtra("duration", playerService.getDuration());
			// savePosition();
		}
		switch (resultCode) {
		case RESULT_FAILED:
			ShowToast(R.string.video_cannot_play);
			break;
		case RESULT_CANCELED:
		case RESULT_OK:
			break;
		}
		setResult(resultCode, i);
	}
	
	// 释放
	private void release() {
		if (playerService != null) {
			if (DeviceUtils.hasICS()) {
				android.os.Process.killProcess(android.os.Process.myPid());
			} else {
				playerService.release();
				playerService.releaseContext();
			}
		}
	}
	
	public void reOpen() {
		reOpen(mUri, mDisplayName, false);
	}
	
	private void reOpen(Uri path, String name, boolean fromStart) {
		if (isInitialized()) {
			playerService.release();
			playerService.releaseContext();
		}
		Intent i = getIntent();
		if (mMediaController != null)
			i.putExtra("lockScreen", mMediaController.isLocked());
		i.putExtra(
				"startPosition",
				SharePreferenceUtil.getFloat(mUri
						+ Constant.SESSION_LAST_POSITION_SUFIX, 7.7f));
		i.putExtra("fromStart", fromStart);
		i.putExtra("displayName", name);
		i.setData(path);
		parseIntent(i);
		mUri = path;
		if (mViewRoot != null)
			mViewRoot.invalidate();
		if (mOpened != null)
			mOpened.set(false);
	}
	
	/**
	 * @ClassName: VideoViewInitTask
	 * @Description: 初始化播放
	 * @author: KCJ	
	 * @date: 2015-12-15
	 */
	private class VideoViewPlayTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			try {
				Log.e("QAQ--->", "http://www.bilibili.com/m/html5?aid=" + av+ "&page=" + page);
				JSONObject videopathjson = new JSONObject(HttpProxy.getHtmlString("http://www.bilibili.com/m/html5?aid="+av+"&page="+page));
				Log.i("QAQ--->", videopathjson.getString("src").toString());
				danmakuPath = videopathjson.getString("cid").toString();
				mUri = Uri.parse(videopathjson.getString("src").toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mParser = createParser(danmakuPath);
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (mParser != null) {
				mDanmakuView.prepare(mParser);
				mDanmakuView.showFPS(false);
				mDanmakuView.enableDanmakuDrawingCache(false);
			} else {
				startText = startText + "【失败】\n视频缓冲中...";
				startVideoInfo.setText(startText);
			}
			isload = true;
			anim.stop();
			iv_video_load_start.setVisibility(View.VISIBLE);
			startText = startText + "【完成】\n点击缓冲视频...";
			startVideoInfo.setText(startText);
//			Intent serviceIntent = new Intent(getApplicationContext(),PlayerService.class);
//			serviceIntent.putExtra("isHWCodec", mIsHWCodec);
//			bindService(serviceIntent, vPlayerServiceConnection,Context.BIND_AUTO_CREATE);
//			startText = startText + "【完成】\n视频缓冲中...";
//			startVideoInfo.setText(startText);
		}
	}
	
	/**
	 * 加载弹幕
	 */
	private BaseDanmakuParser createParser(String uri) {
		InputStream stream = null;
		if (uri == null) {
			return new BaseDanmakuParser() {
				@Override
				protected Danmakus parse() {
					return new Danmakus();
				}
			};
		}
		try {
			Response rsp = (Response) Jsoup.connect(uri).execute();
			stream = new ByteArrayInputStream(
					CompressionTools.decompressXML(rsp.bodyAsBytes()));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);
		try {
			loader.load(stream);
		} catch (IllegalDataException e) {
			e.printStackTrace();
		}
		BaseDanmakuParser parser = new BiliDanmukuParser();
		IDataSource<?> dataSource = loader.getDataSource();
		parser.load(dataSource);
		return parser;
	}
	
	private ViewPager.OnPageChangeListener getViewPagerPageChangeListener() {
		ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int state) {}

			@Override
			public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
				Log.i("onPageScrolled","onPageScrolled===");
//				int currentItem = viewPager.getCurrentItem();
//				if (positionOffsetPixels > 0) {
//					SparseArrayCompat<ScrollTabHolder> scrollTabHolders = pagerdAdapter.getScrollTabHolders();
//					ScrollTabHolder fragmentContent;
//					if (position < currentItem) {
//						fragmentContent = scrollTabHolders.valueAt(position);
//					} else {
//						fragmentContent = scrollTabHolders.valueAt(position + 1);
//					}
//					Log.i(TAG, "header height " + top_view.getHeight() +
//							 " translationY " + top_view.getTranslationY());
//					fragmentContent.adjustScroll((int) (top_view.getHeight() + top_view.getTranslationY()), top_view.getHeight());
//				}
			}

			@Override
			public void onPageSelected(int position) {
//				Log.i("onPageSelected","onPageSelected===");
//				SparseArrayCompat<ScrollTabHolder> scrollTabHolders = pagerdAdapter.getScrollTabHolders();
//				if (scrollTabHolders == null|| scrollTabHolders.size() != pagerdAdapter.getCount()) {
//					return;
//				}
//				ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
//				currentHolder.adjustScroll((int) (top_view.getHeight() + top_view.getTranslationY()), top_view.getHeight());
			}
			
		};
		return listener;
	}
	
	/**
	 * 屏幕亮
	 */
	private class ScreenReceiver extends BroadcastReceiver {
		private boolean screenOn = true;

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				screenOn = false;
				stopPlayer();
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				screenOn = true;
			}
		}
	}
	
	/**
	 * 判断耳麦拔插事件
	 */
	public class HeadsetPlugReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && intent.hasExtra("state")) {
				int state = intent.getIntExtra("state", -1);
				if (state == 0) {
					mHeadsetPlaying = isPlaying();
					stopPlayer();
				} else if (state == 1) {
					if (mHeadsetPlaying)
						startPlayer();
				}
			}
		};
	}
	
	/**
	 * 屏幕解锁
	 */
	private class UserPresentReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (isRootActivity()) {
				startPlayer();
			}
		}
	}
		
	private boolean isRootActivity() {
		return AppUtil.isRunningForeground(this);
	}
	
	/**
	 * 检测手机电量情况
	 */
	private class BatteryReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
			int percent = scale > 0 ? level * 100 / scale : 0;
			if (percent > 100)
				percent = 100;
			mBatteryLevel = String.valueOf(percent) + "%";
			setBatteryLevel();
		}
	}
	
	/**
	 * 退出
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();
//			if ((currentTime - touchTime) >= EXIT_INTERVAL_TIME) {
//				ShowToast("再按一下退出喵(｡･ω･｡)~");
//				touchTime = currentTime;
//			} else {
				finish();
				if (mDanmakuView != null)
					mDanmakuView.release();
//			}
			return false;
		} else {
			return true;
		}
	}
}
