package com.webspider.IPCView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.loading);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// loading
				Intent mainIntent = new Intent(LoadingActivity.this, IPCViewActivity.class);
//				mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
				startActivity(mainIntent);
				finish();
				
			}
		}, 1000);
	}

}
