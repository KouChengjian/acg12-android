package com.kcj.animationfriend.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.kcj.animationfriend.util.DeviceUtils;
import com.kcj.animationfriend.util.PixelUtil;
import com.liteutil.util.Log;

/** 
 * @ClassName: CommonGestures
 * @Description: 通用手势（视频操作）
 * @author: 
 * @date: 2015-10-25 11:40
 */
public class CommonGestures {
	
	public static final int SCALE_STATE_BEGIN = 0;
	public static final int SCALE_STATE_SCALEING = 1;
	public static final int SCALE_STATE_END = 2;

	private boolean mGestureEnabled;

	private GestureDetectorCompat mDoubleTapGestureDetector;
	private GestureDetectorCompat mTapGestureDetector;
	private ScaleGestureDetector mScaleDetector;

	int  playingTime  = 10000;
	int videoTotalTime= 20000;
    
	private Activity mContext;

	public CommonGestures(Activity ctx) {
		mContext = ctx;
		// 左右滑动 和 双击
		mDoubleTapGestureDetector = new GestureDetectorCompat(mContext, new DoubleTapGestureListener());
		// 单击 长按
		mTapGestureDetector = new GestureDetectorCompat(mContext, new TapGestureListener());
		// 缩放
		mScaleDetector = new ScaleGestureDetector(mContext, new ScaleDetectorListener());
	}

	
	public boolean onTouchEvent(MotionEvent event) {
		if (mListener == null)
			return false;
//		mTapGestureDetector.onTouchEvent(event);
//		mScaleDetector.onTouchEvent(event);
//		mDoubleTapGestureDetector.onTouchEvent(event);
		
		// 单击 长按
		if (mTapGestureDetector.onTouchEvent(event))
			return true;

		// 缩放
		if (event.getPointerCount() > 1) {
			try {
				if (mScaleDetector != null && mScaleDetector.onTouchEvent(event))
					return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}			

		// 左右滑动 和 双击
		if (mDoubleTapGestureDetector.onTouchEvent(event))
			return true;

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_UP:
			mListener.onGestureEnd();
			break;
		}
		return false;
	}

	private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onSingleTapConfirmed(MotionEvent event) {
			if (mListener != null)
				mListener.onSingleTap();
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			if (mListener != null && mGestureEnabled)
				mListener.onLongPress();
		}
	}

	@SuppressLint("NewApi")
	private class ScaleDetectorListener implements ScaleGestureDetector.OnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			if (mListener != null && mGestureEnabled)
				mListener.onScale(detector.getScaleFactor(), SCALE_STATE_SCALEING); // 返回从前一个伸缩事件至当前伸缩事件的伸缩比率
			return true;
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			if (mListener != null && mGestureEnabled)
				mListener.onScale(0F, SCALE_STATE_END);
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			if (mListener != null && mGestureEnabled)
				mListener.onScale(0F, SCALE_STATE_BEGIN);
			return true;
		}
	}

	private class DoubleTapGestureListener extends GestureDetector.SimpleOnGestureListener {
		private boolean mDown = false;
		
		@Override
		public boolean onDown(MotionEvent event) {
			mDown = true;
			return super.onDown(event);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (mListener != null && mGestureEnabled && e1 != null && e2 != null) {
				if (mDown) {
					mListener.onGestureBegin();
					mDown = false;
				}
				float mOldX = e1.getX(), mOldY = e1.getY();
				int windowWidth = DeviceUtils.getScreenWidth(mContext);
				int windowHeight = DeviceUtils.getScreenHeight(mContext);
				if (Math.abs(e2.getY(0) - mOldY) * 2 > Math.abs(e2.getX(0) - mOldX)) {
					if (mOldX > windowWidth * 4.0 / 5) {
						mListener.onRightSlide((mOldY - e2.getY(0)) / windowHeight);
					} else if (mOldX < windowWidth / 5.0) {
						mListener.onLeftSlide((mOldY - e2.getY(0)) / windowHeight);
					}
				}
				if (Math.abs(distanceX) >= Math.abs(distanceY)) {
//					Log.e("distance", distanceX+"===="+distanceY);
					if (distanceX >= PixelUtil.dp2px(0)) {// 快退，用步长控制改变速度，可微调
//		                gesture_iv_progress.setImageResource(R.drawable.souhu_player_backward);
		                if (playingTime > 3) {// 避免为负
		                    playingTime -= 3;// scroll方法执行一次快退3秒
		                    
		                } else {
		                    playingTime = 0;
		                }
//		                Log.e("time", playingTime+"====");
		            } else if (distanceX <= -PixelUtil.dp2px(0)) {// 快进
//		                gesture_iv_progress.setImageResource(R.drawable.souhu_player_forward);
		                if (playingTime < videoTotalTime - 16) {// 避免超过总时长
		                    playingTime += 3;// scroll执行一次快进3秒
		                } else {
		                    playingTime = videoTotalTime ;
		                }
//		                Log.e("time", playingTime+"====");
		            }
		            if (playingTime < 0) {
		                playingTime = 0;
		            }
		            
//		            tv_pro_play.seekTo(playingTime);
//		            geture_tv_progress_time.setText(DateTools.getTimeStr(playingTime) + "/" + DateTools.getTimeStr(videoTotalTime));    
		        } 
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onDoubleTap(MotionEvent event) {
			if (mListener != null && mGestureEnabled)
				mListener.onDoubleTap();
			return super.onDoubleTap(event);
		}
		
	}

	public void setTouchListener(TouchListener l, boolean enable) {
		mListener = l;
		mGestureEnabled = enable;
	}

	private TouchListener mListener;

	public interface TouchListener {
		public void onGestureBegin(); // 手势开始

		public void onGestureEnd(); // 手势结束

		public void onLeftSlide(float percent); // 左

		public void onRightSlide(float percent); // 右

		public void onSingleTap(); // 单击

		public void onDoubleTap();// 双击

		public void onScale(float scaleFactor, int state); // 缩放视频

		public void onLongPress(); // 长按
	}
}
