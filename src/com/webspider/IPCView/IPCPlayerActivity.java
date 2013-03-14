package com.webspider.IPCView;

import java.io.IOException;

import com.media.ffmpeg.FFMpeg;
import com.media.ffmpeg.FFMpegException;
import com.media.ffmpeg.android.FFMpegMovieViewAndroid;

import android.os.Bundle;
import android.util.Log;

public class IPCPlayerActivity extends IPCNoTitleActivity{
	private static String TAG="IPCPlayerActivity";
	private FFMpegMovieViewAndroid mMovieView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CameraItem mCameraItem = (CameraItem)getIntent().getSerializableExtra("camera_info");

		String filePath = "rtsp://" + mCameraItem.address;
		if (mCameraItem.port != null && !mCameraItem.port.isEmpty()){
			filePath += ":" + mCameraItem.port;
		}
		filePath += "?http";
		Log.i(TAG, "To play the source " + filePath);
		try{
			FFMpeg ffmpeg = new FFMpeg();
			mMovieView = ffmpeg.getMovieView(this);
			try{
				mMovieView.setVideoPath(filePath);
			}catch(IllegalArgumentException e){
				Log.e(TAG, "Can't set view " + e.getMessage());
				finish();
			}catch(IllegalStateException e){
				Log.e(TAG, "Can't set view " + e.getMessage());
				finish();
			}catch(IOException e){
				Log.e(TAG, "Can't set view " + e.getMessage());
				finish();
			}
			setContentView(mMovieView);
		}catch(FFMpegException e){
			Log.d(TAG, "Error when initial FFMpeg: " + e.getMessage());
			finish();
		}
	}

}
