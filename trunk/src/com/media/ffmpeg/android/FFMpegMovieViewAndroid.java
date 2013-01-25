package com.media.ffmpeg.android;

import java.io.IOException;

import com.media.ffmpeg.FFMpegPlayer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;

public class FFMpegMovieViewAndroid extends SurfaceView {
	private static final String TAG = "FFMpegMovieViewAndroid";

	private FFMpegPlayer mPlayer;
	private MediaController mMediaController;

	public FFMpegMovieViewAndroid(Context context) {
		super(context);
		initVideoView();
	}

	public FFMpegMovieViewAndroid(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initVideoView();
	}

	public FFMpegMovieViewAndroid(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initVideoView();
	}

	private void initVideoView() {
		mPlayer = new FFMpegPlayer();
		Log.d(TAG, "InitVideoView to addCallback");
		SurfaceHolder surfHolder = getHolder();
		surfHolder.addCallback(mSHCallback);
	}

	private void attachMediaController() {
		mMediaController = new MediaController(getContext());
		View anchorView = this.getParent() instanceof View ? (View) this
				.getParent() : this;
		mMediaController.setMediaPlayer(mMediaPlayerControl);
		mMediaController.setAnchorView(anchorView);
		mMediaController.setEnabled(true);
	}

	public void setVideoPath(String filePath) throws IllegalArgumentException,
			IllegalStateException, IOException {
		mPlayer.setDataSource(filePath);
	}

	/**
	 * initzialize player
	 */
	private void openVideo(SurfaceHolder surfHolder) {
		try {
			mPlayer.setDisplay(surfHolder);

			// Add by yarpee
			// 必须在prepare前设置listener
			mPlayer.setOnVideoSizeChangedListener(new FFMpegPlayer.OnVideoSizeChangedListener() {
				public void onVideoSizeChanged(FFMpegPlayer mp, int width,
						int height) {
					// 注意此处不能用mp，mp为null
					int videoWidth = mPlayer.getVideoWidth();
					int videoHeight = mPlayer.getVideoHeight();
					if (videoHeight != 0 && videoWidth != 0) {
						Log.d(TAG, "setFixedSize width:" + videoWidth
								+ " height:" + videoHeight);
						getHolder().setFixedSize(videoWidth, videoHeight);
					}
				}
			});

			mPlayer.prepare();
		} catch (IllegalStateException e) {
			Log.e(TAG, "Couldn't prepare player: " + e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, "Couldn't prepare player: " + e.getMessage());
		}
	}

	private void startVideo() {
		attachMediaController();
		mPlayer.start();
	}

	private void release() {
		Log.d(TAG, "releasing player");

		mPlayer.suspend();

		Log.d(TAG, "released");
	}

	public boolean onTouchEvent(android.view.MotionEvent event) {
		if (!mMediaController.isShowing()) {
			mMediaController.show(3000);
		}
		return true;
	}

	SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
			Log.d(TAG, "surfaceChanged to startVideo");
//			release();
//			startVideo();
		}

		public void surfaceCreated(SurfaceHolder holder) {
			openVideo(holder);
			startVideo();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			release();
			mPlayer.stop();
			if (mMediaController.isShowing()) {
				mMediaController.hide();
			}
		}
	};

	MediaPlayerControl mMediaPlayerControl = new MediaPlayerControl() {

		public void start() {
			mPlayer.resume();
		}

		public void seekTo(int pos) {
			// Log.d(TAG, "want seek to");
		}

		public void pause() {
			mPlayer.pause();
		}

		public boolean isPlaying() {
			return mPlayer.isPlaying();
		}

		public int getDuration() {
			return mPlayer.getDuration();
		}

		public int getCurrentPosition() {
			return mPlayer.getCurrentPosition();
		}

		public int getBufferPercentage() {
			// Log.d(TAG, "want buffer percentage");
			return 0;
		}

		@Override
		public boolean canPause() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canSeekBackward() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canSeekForward() {
			// TODO Auto-generated method stub
			return false;
		}
	};
}
