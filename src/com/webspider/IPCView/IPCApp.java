package com.webspider.IPCView;


import java.util.ArrayList;
import android.app.Application;
import java.lang.System;
import android.content.Intent;


public class IPCApp extends Application {
	public static IPCApp instance;
	protected ArrayList<CameraItem> mycameras;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		instance = this;
		Intent loadingIntent = new Intent(this, LoadingActivity.class);
		loadingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		loadCameras();
//		startActivity(loadingIntent);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	
	public void loadCameras(){
		CameraItem item = null;
		System.out.println("IPCApp loadCameras");
		mycameras = new ArrayList<CameraItem>();
		for (int i = 0; i < 5; i++){
			item = new CameraItem();
			item.index = i;
			item.name = "Camera " + i;
			item.address = "192.168.199.154";
			item.detail = "something for camera " + i;
			mycameras.add(item);
		}
		
	}
	ArrayList<CameraItem> getCameras(){
		return mycameras;
	}
}
