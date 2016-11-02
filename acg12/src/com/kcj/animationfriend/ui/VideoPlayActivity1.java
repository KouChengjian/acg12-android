package com.kcj.animationfriend.ui;

import io.vov.vitamio.utils.FileUtils;
import io.vov.vitamio.widget.OutlineTextView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcj.animationfriend.PlayerService;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.ui.base.BaseActivity;
import com.kcj.animationfriend.util.AppUtil;
import com.kcj.animationfriend.util.CompressionTools;
import com.kcj.animationfriend.util.DeviceUtils;
import com.kcj.animationfriend.util.ImageUtils;
import com.kcj.animationfriend.util.MediaUtils;
import com.kcj.animationfriend.util.SharePreferenceUtil;
import com.kcj.animationfriend.view.MediaController;
import com.kcj.animationfriend.view.VideoView;
import com.liteutil.async.AsyncTask;
import com.liteutil.util.Log;

/**
 * @ClassName: VideoPlayActivity
 * @Description: 视频播放
 * @author: KCJ
 * @date:2015-9-27
 */
public class VideoPlayActivity1 extends BaseActivity implements MediaController.MediaPlayerControl, VideoView.SurfaceCallback {

	public static final int RESULT_FAILED = -7;

	// 屏幕解锁
	private static final IntentFilter USER_PRESENT_FILTER = new IntentFilter(Intent.ACTION_USER_PRESENT);
	// 屏幕亮
	private static final IntentFilter SCREEN_FILTER  = new IntentFilter(Intent.ACTION_SCREEN_ON);
	// 判断耳麦拔插事件
	private static final IntentFilter HEADSET_FILTER = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
	// 检测手机电量情况
	private static final IntentFilter BATTERY_FILTER = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

	private IDanmakuView mDanmakuView; // 弹幕控件
	private BaseDanmakuParser mParser;
	private String danmakuPath;
	private String av;
	private String page;
	private boolean isload = false;
	private boolean isfirst = true;
	private View startVideo;
	private TextView startVideoInfo;
	private String startText = "初始化播放器...";
	private ImageView biliAnim;
	private AnimationDrawable anim;

	private boolean mCreated = false;
	private boolean mNeedLock;
	private String mDisplayName;
	private String mBatteryLevel;
	private boolean mFromStart;
	private int mLoopCount;
	private boolean mSaveUri;
	private int mParentId;
	private float mStartPos;
	private String mSubPath;
	private boolean mSubShown;
	private View mViewRoot;
	private VideoView mVideoView;
	private View mVideoLoadingLayout;
	private TextView mVideoLoadingText;
	private View mSubtitleContainer;
	private OutlineTextView mSubtitleText;
	private ImageView mSubtitleImage;
	private Uri mUri;
	private ScreenReceiver mScreenReceiver;
	private HeadsetPlugReceiver mHeadsetPlugReceiver;
	private UserPresentReceiver mUserPresentReceiver;
	private BatteryReceiver mBatteryReceiver;
	private boolean mReceiverRegistered = false;
	private boolean mHeadsetPlaying = false;
	private boolean mCloseComplete = false;
	private boolean mIsHWCodec = false;

	private MediaController mMediaController;
	private PlayerService playerService; // 服务
	private ServiceConnection vPlayerServiceConnection;

	static {
		SCREEN_FILTER.addAction(Intent.ACTION_SCREEN_OFF);
	}
	
	/* 退出的间隔时间 */
	private static final long EXIT_INTERVAL_TIME = 2000;
	private long touchTime = 0;
	
	private int mVideoMode = VideoView.VIDEO_LAYOUT_SCALE;
	
	private AtomicBoolean mOpened = new AtomicBoolean(Boolean.FALSE);
	private boolean mSurfaceCreated = false; // surface是否被创建
	private boolean mServiceConnected = false; // 当前activity是否绑定service
	private Object mOpenLock = new Object();
	private static final int OPEN_FILE = 0; // 播放视频初始
	private static final int OPEN_START = 1; // 开始
	private static final int OPEN_SUCCESS = 2; // 成功
	private static final int OPEN_FAILED = 3; // 失败
	private static final int HW_FAILED = 4; // 失败
	private static final int LOAD_PREFS = 5; // 加载控制台
	private static final int BUFFER_START = 11; // 开始缓存
	private static final int BUFFER_PROGRESS = 12; // 缓存进度
	private static final int BUFFER_COMPLETE = 13; // 缓存完成
	private static final int CLOSE_START = 21; // 结束启动
	private static final int CLOSE_COMPLETE = 22; // 结束完成
	private static final int SUBTITLE_TEXT = 0; // 字幕-文本
	private static final int SUBTITLE_BITMAP = 1; // 字幕-位图

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)){
			return;
		}
		vPlayerServiceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				playerService = ((PlayerService.LocalBinder) service).getService();
				mServiceConnected = true;
				if (mSurfaceCreated)
					playerServiceHandler.sendEmptyMessage(OPEN_FILE);
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
		loadView(R.layout.activity_videoplay);
		// 注册广播
		manageReceivers();
        // 初始化控件 - 弹幕
		findViews();

		mCreated = true;
		startText = startText + "【完成】\n解析视频地址...【完成】\n全舰弹幕填装...";
		startVideoInfo.setText(startText);
		new VideoViewInitTask().execute();
	}

	private void findViews() {
		mDanmakuView = (IDanmakuView) findViewById(R.id.sv_danmaku);
		startVideo = (View) findViewById(R.id.video_start);
		startVideo.setVisibility(View.VISIBLE);
		startVideoInfo = (TextView) findViewById(R.id.video_start_info);
		biliAnim = (ImageView) findViewById(R.id.bili_anim);
		anim = (AnimationDrawable) biliAnim.getBackground();
		anim.start();
		DanmakuGlobalConfig.DEFAULT.setDanmakuStyle(DanmakuGlobalConfig.DANMAKU_STYLE_STROKEN, 3)
		.setDuplicateMergingEnabled(false);
	}
	
	private void loadView(int id) {
		setContentView(id);
		getWindow().setBackgroundDrawable(null);
		mViewRoot = findViewById(R.id.video_root);
		mVideoView = (VideoView) findViewById(R.id.video);
		mVideoView.initialize(this, this, mIsHWCodec);
		mSubtitleContainer = findViewById(R.id.subtitle_container);
		mSubtitleText = (OutlineTextView) findViewById(R.id.subtitle_text);
		mSubtitleImage = (ImageView) findViewById(R.id.subtitle_image);
		mVideoLoadingText = (TextView) findViewById(R.id.video_loading_text);
		mVideoLoadingLayout = findViewById(R.id.video_loading);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void parseIntent(Intent i) {
		av = i.getStringExtra("av"); // 视频id
		page = i.getStringExtra("page"); // 视频页数
		mNeedLock = i.getBooleanExtra("lockScreen", false);
		mDisplayName = i.getStringExtra("displayName"); // 视频名称
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

	@Override
	public void onStart() {
		super.onStart();
		if (!mCreated)
			return;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!mCreated)
			return;
		if (isInitialized()) {
			KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
			if (!keyguardManager.inKeyguardRestrictedInputMode()) {
				startPlayer();
			}
		} else {
			if (mCloseComplete) {
				reOpen();
			}
		}
		if (mDanmakuView != null && mDanmakuView.isPrepared()&& mDanmakuView.isPaused()) {
			mDanmakuView.resume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
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
	public void onDestroy() {
		super.onDestroy();
		if (!mCreated)
			return;
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
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("WORKAROUND_FOR_BUG_19917_KEY",
				"WORKAROUND_FOR_BUG_19917_VALUE");
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (isInitialized()) {
			setVideoLayout();
			attachMediaController();
		}
		super.onConfigurationChanged(newConfig);
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
		//rl_video_ctl.addView(mMediaController);
		setFileName();
		setBatteryLevel();
	}

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
	public void onSurfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (playerService != null) {
			setVideoLayout();
		}
	}

	@Override
	public void onSurfaceDestroyed(SurfaceHolder holder) {
		Log.i("onSurfaceDestroyed");
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

	private void resultFinish(int resultCode) {
		applyResult(resultCode);
		if (DeviceUtils.hasICS() && resultCode != RESULT_FAILED) {
			android.os.Process.killProcess(android.os.Process.myPid());
		} else {
			finish();
		}
	}

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

	public void reOpen() {
		reOpen(mUri, mDisplayName, false);
	}

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

	protected void stopPlayer() {
		if (isInitialized()) {
			playerService.stop();
			if (mDanmakuView != null) {
				if (mDanmakuView.isPrepared() && mDanmakuView.isShown());
				mDanmakuView.pause();
			}
		}
	}

	private void setBatteryLevel() {
		if (mMediaController != null)
			mMediaController.setBatteryLevel(mBatteryLevel);
	}

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
	 *@author: 
	 *@date: 
	 */
	@SuppressLint("HandlerLeak")
	private Handler playerServiceHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case OPEN_FILE:
				synchronized (mOpenLock) {
					if (!mOpened.get() && playerService != null) {
						mOpened.set(true);
						playerService.setVPlayerListener(vPlayerListener); // 设置播放监听
						if (playerService.isInitialized()) // 初始化时为false
							mUri = playerService.getUri(); // 获取流
						if (mVideoView != null)
							playerService.setDisplay(mVideoView.getHolder()); // 将Holder传给Service
						if (mUri != null)
							playerService.initialize(mUri, mDisplayName, mSaveUri,
									getStartPosition(), vPlayerListener,
									mParentId, mIsHWCodec);
					}
				}
				break;
			case OPEN_START:
				mVideoLoadingText.setText(R.string.video_layout_loading);
				setVideoLoadingLayoutVisibility(View.VISIBLE);
				break;
			case OPEN_SUCCESS:
				loadVPlayerPrefs();
				setVideoLoadingLayoutVisibility(View.GONE);
				setVideoLayout();
				playerService.start();
				if (mDanmakuView.isPaused())
					mDanmakuView.resume();
				else
					mDanmakuView.start();
				attachMediaController();
				break;
			case OPEN_FAILED:
				resultFinish(RESULT_FAILED);
				break;
			case BUFFER_START:
				setVideoLoadingLayoutVisibility(View.VISIBLE);
				playerServiceHandler.sendEmptyMessageDelayed(BUFFER_PROGRESS, 1000);
				break;
			case BUFFER_PROGRESS:
				if (playerService.getBufferProgress() >= 100) {
					setVideoLoadingLayoutVisibility(View.GONE);
				} else {
					mVideoLoadingText.setText(getString(
							R.string.video_layout_buffering_progress,
							playerService.getBufferProgress()));
					playerServiceHandler.sendEmptyMessageDelayed(BUFFER_PROGRESS,
							1000);
					stopPlayer();
				}
				break;
			case BUFFER_COMPLETE:
				setVideoLoadingLayoutVisibility(View.GONE);
				playerServiceHandler.removeMessages(BUFFER_PROGRESS);
				break;
			case CLOSE_START:
				mVideoLoadingText.setText(R.string.closing_file);
				setVideoLoadingLayoutVisibility(View.VISIBLE);
				break;
			case CLOSE_COMPLETE:
				mCloseComplete = true;
				break;
			case HW_FAILED:
				if (mVideoView != null) {
					mVideoView.setVisibility(View.GONE);
					mVideoView.setVisibility(View.VISIBLE);
					mVideoView.initialize(VideoPlayActivity1.this,
							VideoPlayActivity1.this, false);
				}
				break;
			case LOAD_PREFS:
				loadVPlayerPrefs();
				break;
			}
		}
	};

	/**
	 *  显示或隐藏缓冲布局
	 */
	private void setVideoLoadingLayoutVisibility(int visibility) {
		if (mVideoLoadingLayout != null) {
			mVideoLoadingLayout.setVisibility(visibility);
		}
	}

	/** ---------------------   MediaController.MediaPlayerControl Start   -----------------------*/
	@Override
	public void showMenu() {}

	private float getStartPosition() {
		if (mFromStart)
			return 1.1f;
		if (mStartPos <= 0.0f || mStartPos >= 1.0f)
			return SharePreferenceUtil.getFloat(mUri
					+ Constant.SESSION_LAST_POSITION_SUFIX, 7.7f);
		return mStartPos;
	}

	@Override
	public int getBufferPercentage() {
		if (isInitialized())
			return (int) (playerService.getBufferProgress() * 100);
		return 0;
	}

	@Override
	public long getCurrentPosition() {
		if (isInitialized())
			return playerService.getCurrentPosition();
		return (long) (getStartPosition() * playerService.getDuration());
	}

	@Override
	public long getDuration() {
		if (isInitialized())
			return playerService.getDuration();
		return 0;
	}

	@Override
	public boolean isPlaying() {
		if (isInitialized())
			return playerService.isPlaying();
		return false;
	}

	@Override
	public void pause() {
		if (isInitialized())
			playerService.stop();
		mDanmakuView.pause();
	}

	@Override
	public void seekTo(long arg0) {
		if (isInitialized())
			playerService.seekTo((float) ((double) arg0 / playerService.getDuration()));
		mDanmakuView.seekTo(arg0);
		mDanmakuView.pause();
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
	public void previous() {
	}

	@Override
	public void next() {
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

	@SuppressLint("SimpleDateFormat")
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
	public void toggleVideoMode(int mode) {
		mVideoMode = mode;
		setVideoLayout();
	}

	@Override
	public void stop() {
		onBackPressed();
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
	public void rotatingScreen() {
		
	}

	@Override
	public void removeLoadingView() {
		mVideoLoadingLayout.setVisibility(View.GONE);
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

		@Override
		public void onSubChanged(String sub) {
			Message msg = new Message();
			Bundle b = new Bundle();
			b.putString(Constant.SUB_TEXT_KEY, sub);
			msg.setData(b);
			msg.what = SUBTITLE_TEXT;
			mSubHandler.sendMessage(msg);
		}

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

		@Override
		public void onDownloadRateChanged(int kbPerSec) {
			if (!MediaUtils.isNative(mUri.toString())
					&& mMediaController != null) {
				mMediaController.setDownloadRate(String.format("%dKB/s",
						kbPerSec));
			}
		}
	};

	/**
	 * @ClassName: VideoViewInitTask
	 * @Description: 
	 * @author: KCJ
	 * @date:  
	 */
	private class VideoViewInitTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... arg0) {
			Log.i("VideoViewInitTask", "开始解析视频地址");
			try {
				Log.e("QAQ--->", "http://www.bilibili.com/m/html5?aid=" + av+ "&page=" + page + "=====");
				JSONObject videopathjson = new JSONObject(HttpProxy.getHtmlString("http://www.bilibili.com/m/html5?aid="+av+"&page="+page));
				Log.e("QAQ--->", videopathjson.getString("src").toString());
				danmakuPath = videopathjson.getString("cid").toString();
				mUri = Uri.parse(videopathjson.getString("src").toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Log.i("VideoViewInitTask", "开始加载弹幕");
			mParser = createParser(danmakuPath);
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Log.d("TAG", "加载弹幕成功");
			if (mParser != null) {
				mDanmakuView.prepare(mParser);
				mDanmakuView.showFPS(false);
				mDanmakuView.enableDanmakuDrawingCache(false);
			} else {
				startText = startText + "【失败】\n视频缓冲中...";
				startVideoInfo.setText(startText);
			}
			isload = true;
			Intent serviceIntent = new Intent(getApplicationContext(),PlayerService.class);
			serviceIntent.putExtra("isHWCodec", mIsHWCodec);
			bindService(serviceIntent, vPlayerServiceConnection,Context.BIND_AUTO_CREATE);
			startText = startText + "【完成】\n视频缓冲中...";
			startVideoInfo.setText(startText);
		}
	}
	
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

		ILoader loader = DanmakuLoaderFactory
				.create(DanmakuLoaderFactory.TAG_BILI);

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

	/**
	 * 退出
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();

			if ((currentTime - touchTime) >= EXIT_INTERVAL_TIME) {
				ShowToast("再按一下退出喵(｡･ω･｡)~");
				touchTime = currentTime;
			} else {
				finish();
				if (mDanmakuView != null)
					mDanmakuView.release();
			}

			return false;
		} else {
			return true;
		}
	}
}
