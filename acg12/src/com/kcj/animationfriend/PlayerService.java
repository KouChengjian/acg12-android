package com.kcj.animationfriend;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnHWRenderFailedListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnSeekCompleteListener;
import io.vov.vitamio.MediaPlayer.OnTimedTextListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
import io.vov.vitamio.Vitamio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.SurfaceHolder;

import com.kcj.animationfriend.config.Constant;
import com.kcj.animationfriend.util.FileUtils;
import com.liteutil.util.Log;

/**
 * @ClassName: PlayerService
 * @Description: 播放服务
 * @author: KCJ
 * @date:
 */
public class PlayerService extends Service implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
		OnVideoSizeChangedListener, OnErrorListener, OnInfoListener,
		OnSeekCompleteListener, OnTimedTextListener {

	private MediaPlayer mPlayer; // 音频
	private VPlayerListener mListener;
	private Uri mUri;
	private Uri mOldUri; 
	private float mSeekTo = -1f;
	private boolean mFromNotification;
	private String[] mSubPaths;
	private boolean mInitialized; // 播放成功后为true
	private final IBinder mBinder = new LocalBinder();
	private TelephonyManager mTelephonyManager;
	private int mCurrentState;
	private SurfaceHolder mSurfaceHolder;
	public static final int VPLYAER_NOTIFICATION_ID = 1;

	public static final int STATE_PREPARED = -1;
	public static final int STATE_PLAYING = 0; // 开始播放
	public static final int STATE_NEED_RESUME = 1;
	public static final int STATE_STOPPED = 2;
	public static final int STATE_RINGING = 3;

	private int mLastAudioTrack = -1;
	private String mLastSubTrack;
	private int mLastSubTrackId = -1;
	private long mMediaId = -1l;

	protected String mTitle;
	
	public class LocalBinder extends Binder {
		public PlayerService getService() {
			return PlayerService.this;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInitialized = false;
		mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		mTelephonyManager.listen(mPhoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (Vitamio.isInitialized(this)) {
			vplayerInit(intent.getBooleanExtra("isHWCodec", false));
		} else {
			stopSelf();
		}
		Log.d("CREATE OK");
		return super.onStartCommand(intent, flags, startId);
	}

	private void vplayerInit(boolean isHWCodec) {
		mPlayer = new MediaPlayer(PlayerService.this.getApplicationContext(),
				isHWCodec);
		mPlayer.setOnHWRenderFailedListener(new OnHWRenderFailedListener() {
			@Override
			public void onFailed() {
				if (mListener != null)
					mListener.onHWRenderFailed();
			}
		});
		mPlayer.setOnBufferingUpdateListener(PlayerService.this); // 注册一个回调函数，在网络视频流缓冲变化时调用。
		mPlayer.setOnCompletionListener(PlayerService.this);      // 注册一个回调函数，视频播放完成后调用。
		mPlayer.setOnPreparedListener(PlayerService.this);        // 注册一个回调函数，在视频预处理完成后调用。
		mPlayer.setOnVideoSizeChangedListener(PlayerService.this);// 注册一个回调函数，在视频大小已知或更新后调用。
		mPlayer.setOnErrorListener(PlayerService.this);           // 注册一个回调函数，在异步操作调用过程中发生错误时调用。例如视频打开失败。
		mPlayer.setOnInfoListener(PlayerService.this);            // 注册一个回调函数，在有警告或错误信息时调用。例如：开始缓冲、缓冲结束、下载速度变化。
	}

	public void releaseContext() {
		if (mPlayer != null)
			mPlayer.release();
		mPlayer = null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("BIND OK : " + intent.getPackage());
		return mBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		release(true);
		releaseContext();
	}

	/**
	 * 是否初始化
	 */
	public boolean isInitialized() {
		return mInitialized;
	}

	/**
	 * 初始化
	 */
	public boolean initialize(Uri filePath, String displayName,
			boolean saveUri, float startPos, VPlayerListener listener,
			int parentId, boolean isHWCodec) {
		if (mPlayer == null)
			vplayerInit(isHWCodec);
		mTitle = displayName;
		mListener = listener;
		mOldUri = mUri;
		mUri = filePath;
		mSeekTo = startPos;
		mMediaId = -1;
		mLastAudioTrack = -1;
		mLastSubTrackId = -1;
		mLastSubTrack = "";
		setMediaTrack();
		Log.i("%s ==> %s, %s, %s, %s", mOldUri, mUri, mInitialized, mPrepared,
				mVideoSizeKnown);
		mFromNotification = mInitialized && mUri != null&& mUri.equals(mOldUri);
		mListener.onOpenStart();
		if (!mFromNotification)
			openVideo();
		else
			openSuccess();
		return mInitialized;
	}

	private void setMediaTrack() {}

	private void openVideo() {
		if (mUri == null || mPlayer == null)
			return;
		mPlayer.reset(); // 复位
		mInitialized = false;
		mPrepared = false;
		mVideoSizeKnown = false;

		try {
			mPlayer.setScreenOnWhilePlaying(true);
			mPlayer.setDataSource(PlayerService.this, mUri);
			// if (mLastAudioTrack != -1)
			// mPlayer.setInitialAudioTrack(mLastAudioTrack);
			// if (mLastSubTrackId != -1)
			// mPlayer.setInitialSubTrack(mLastSubTrackId);
			if (mSurfaceHolder != null && mSurfaceHolder.getSurface() != null
					&& mSurfaceHolder.getSurface().isValid())
				mPlayer.setDisplay(mSurfaceHolder);
			mPlayer.prepareAsync();
		} catch (IllegalArgumentException e) {
			Log.i("openVideo", e);
		} catch (IllegalStateException e) {
			Log.i("openVideo", e);
		} catch (IOException e) {
			Log.i("openVideo", e);
		}
	}

	public Uri getUri() {
		return mUri;
	}

	public long getMediaId() {
		return mMediaId;
	}

	public int getLastAudioTrack() {
		return mLastAudioTrack;
	}

	public String getLastSubTrack() {
		return mLastSubTrack;
	}

	public int getLastSubTrackId() {
		return mLastSubTrackId;
	}

	public void setVPlayerListener(VPlayerListener listener) {
		mListener = listener;
	}

	public void setState(int state) {
		mCurrentState = state;
	}

	public boolean needResume() {
		return mInitialized
				&& (mCurrentState == STATE_NEED_RESUME || mCurrentState == STATE_PREPARED);
	}

	public boolean ringingState() {
		return mInitialized && mCurrentState == STATE_RINGING;
	}

	/**
	 * 释放
	 */
	public void release() {
		release(true);
	}

	private void release(boolean all) {
		if (mPlayer != null) {
			if (mListener != null)
				mListener.onCloseStart();
			mPlayer.reset();
			mInitialized = false;
			mPrepared = false;
			mVideoSizeKnown = false;
			if (mListener != null)
				mListener.onCloseComplete();
		}
		if (all) {
			mListener = null;
			mUri = null;
		}
	}

	public void stop() {
		if (mInitialized) {
			mPlayer.pause();
		}
	}

	public void start() {
		if (mInitialized) {
			mPlayer.start();
			setState(STATE_PLAYING);
		}
	}

	public void setDisplay(SurfaceHolder surface) {
		mSurfaceHolder = surface;
		if (mPlayer != null)
			mPlayer.setDisplay(surface);
	}

	public void releaseSurface() {
		if (mInitialized)
			mPlayer.releaseDisplay();
	}

	public boolean isPlaying() {
		return (mInitialized && mPlayer.isPlaying());
	}

	public int getVideoWidth() {
		if (mInitialized)
			return mPlayer.getVideoWidth();
		return 0;
	}

	public int getVideoHeight() {
		if (mInitialized)
			return mPlayer.getVideoHeight();
		return 0;
	}

	public float getVideoAspectRatio() {
		if (mInitialized)
			return mPlayer.getVideoAspectRatio();
		return 0f;
	}

	public long getDuration() {
		if (mInitialized)
			return mPlayer.getDuration();
		return 0;
	}

	public long getCurrentPosition() {
		if (mInitialized)
			return mPlayer.getCurrentPosition();
		return 0;
	}

	public Bitmap getCurrentFrame() {
		if (mInitialized)
			return mPlayer.getCurrentFrame();
		return null;
	}

	public float getBufferProgress() {
		if (mInitialized)
			return mPlayer.getBufferProgress();
		return 0f;
	}

	public void seekTo(float percent) {
		if (mInitialized)
			mPlayer.seekTo((int) (percent * getDuration()));
	}

	public String getMetaEncoding() {
		if (mInitialized)
			return mPlayer.getMetaEncoding();
		return null;
	}

	public void setAudioTrack(int num) {
		if (mInitialized)
			mPlayer.selectTrack(num);
		// mPlayer.setAudioTrack(num);
	}

	public int getAudioTrack() {
		if (mInitialized)
			return mPlayer.getAudioTrack();
		return 0;
	}

	public void setSubShown(boolean shown) {
		if (mInitialized)
			mPlayer.setTimedTextShown(shown);// setSubtitleShown ->
												// setTimedTextShown 4.0
	}

	public boolean isBuffering() {
		return (mInitialized && mPlayer.isBuffering());
	}

	public void setBuffer(int bufSize) {
		if (mInitialized)
			mPlayer.setBufferSize(bufSize);
	}

	public void setVolume(float left, float right) {
		if (mInitialized) {
			if (left <= 0f)
				left = 0f;
			else if (left >= 1f)
				left = 1f;
			if (right <= 0f)
				right = 0f;
			else if (right >= 1f)
				right = 1f;
			mPlayer.setVolume(left, right);
		}
	}

	public void setVideoQuality(int quality) {
		if (mInitialized)
			mPlayer.setVideoQuality(quality);
	}

	public void setDeinterlace(boolean deinterlace) {
		if (mInitialized)
			mPlayer.setDeinterlace(deinterlace);
	}

	public void setSubEncoding(String encoding) {
		if (mInitialized) {
			String enc = encoding.equals(Constant.DEFAULT_SUB_ENCODING) ? null
					: encoding;
			mPlayer.setTimedTextEncoding(enc);// setSubEncoding ->
												// setTimedTextEncoding
		}
	}

	public void setSubPath(String subPath) {
		if (mInitialized)
			mPlayer.addTimedTextSource(subPath);
	}

	public static interface VPlayerListener {
		public void onHWRenderFailed(); // 

		public void onVideoSizeChanged(int width, int height); // 视频大小改变

		public void onSubChanged(String text); 

		public void onSubChanged(byte[] pixels, int width, int height); 

		public void onOpenStart();   // 打开视频开始

		public void onOpenSuccess(); // 打开视频成功

		public void onOpenFailed();  // 打开视频失败

		public void onBufferStart(); // 视频缓存开始

		public void onBufferComplete();// 视频缓存结束

		public void onDownloadRateChanged(int kbPerSec); // 视频缓存比率

		public void onPlaybackComplete(); // 播放完成

		public void onCloseStart(); // 结束开始

		public void onCloseComplete(); // 结束完成
	}

	private PhoneStateListener mPhoneListener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
			case TelephonyManager.CALL_STATE_RINGING: // 震动，铃声
				if (isPlaying()) {
					stop();
					setState(STATE_RINGING);
				}
				break;
			default:
				break;
			}
		}
	};

	private boolean mVideoSizeKnown = false;
	private boolean mPrepared = false;

	/**
	 * OnVideoSizeChangedListener  在视频大小已知或更新后调用。
	 */
	@Override
	public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
		mVideoSizeKnown = true;
		if (mListener != null)
			mListener.onVideoSizeChanged(arg1, arg2);
	}

	/**
	 * OnPreparedListener 在视频预处理完成后调用。
	 */
	@Override
	public void onPrepared(MediaPlayer arg0) {
		mPrepared = true;
		openSuccess();
	}

	private void openSuccess() {
		mInitialized = true;
		if (!mFromNotification && mSeekTo > 0 && mSeekTo < 1)
			seekTo(mSeekTo);
		mSeekTo = -1;
		mListener.onOpenSuccess();
		if (!mFromNotification) {
			setSubEncoding(Constant.DEFAULT_SUB_ENCODING);
			if (mUri != null)
				mSubPaths = getSubFiles(mUri.getPath());
			if (mSubPaths != null)
				setSubPath(FileUtils.getCanonical(new File(mSubPaths[0])));
			setSubShown(Constant.DEFAULT_SUB_SHOWN);
		}
	}
	
	private String[] getSubFiles(String videoPath) {
		ArrayList<String> files = new ArrayList<String>();
		for (String ext : MediaPlayer.SUB_TYPES) {
			File s = new File(videoPath.substring(0,
					videoPath.lastIndexOf('.') > 0 ? videoPath.lastIndexOf('.')
							: videoPath.length())
					+ ext);
			if (s.exists() && s.isFile() && s.canRead())
				files.add(s.getAbsolutePath());
		}

		if (files.isEmpty())
			return null;
		else
			return files.toArray(new String[files.size()]);
	}

	/**
	 * OnCompletionListener  视频播放完成后调用。
	 */
	@Override
	public void onCompletion(MediaPlayer arg0) {
		if (mListener != null) {
			mListener.onPlaybackComplete();
		} else {
			release(true);
		}
	}

	/**
	 * OnBufferingUpdateListener  在网络视频流缓冲变化时调用。
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {}

	/**
	 * OnSeekCompleteListener
	 */
	@Override
	public void onSeekComplete(MediaPlayer arg0) {}

	/**
	 * OnInfoListener 在有警告或错误信息时调用。例如：开始缓冲、缓冲结束、下载速度变化。
	 */
	@Override
	public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
		switch (arg1) {
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:
			if (mListener != null)
				mListener.onBufferStart();
			else
				mPlayer.pause();
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
			if (mListener != null)
				mListener.onBufferComplete();
			else
				mPlayer.start();
			break;
		case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
			if (mListener != null)
				mListener.onDownloadRateChanged(arg2);
			break;
		}
		return true;
	}

	/**
	 * OnErrorListener  在异步操作调用过程中发生错误时调用。例如视频打开失败。
	 */
	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		mListener.onOpenFailed();
		return true;
	}

	/**
	 * OnTimedTextListener 需要显示文字字幕。
	 */
	@Override
	public void onTimedText(String text) {
		if (mListener != null)
			mListener.onSubChanged(text);
	}

	/**
	 * OnTimedTextListener 需要显示图片字幕。
	 */
	@Override
	public void onTimedTextUpdate(byte[] pixels, int width, int height) {
		if (mListener != null)
			mListener.onSubChanged(pixels, width, height);
	}
}
