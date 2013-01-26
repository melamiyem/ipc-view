package com.webspider.IPCView;

import java.util.ArrayList;

import com.webspider.IPCView.IPCListActivity.CameraAdapter.ViewHolder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class IPCListActivity  extends Activity implements OnClickListener, 
	OnItemClickListener{
	protected ListView cameras;
	protected CameraAdapter cameraAdapter = null;
	protected ArrayList<CameraItem> mycameras = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameralist);
		
		System.out.print("IPCListActivity::onCreate");
	
		mycameras = IPCApp.instance.getCameras();
		cameraAdapter = new CameraAdapter(this);
//		UpdateList();
		cameras = (ListView) findViewById(R.id.cameralist);
		cameras.setAdapter(cameraAdapter);
		cameras.setOnItemClickListener(this);
		

	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.f_main_back:
			break;
		case R.id.f_camera_add:
		{
			Intent intent = new Intent(IPCListActivity.this, IPCameraInfoActivity.class);
			CameraItem item = new CameraItem();
			intent.putExtra("camera_info", item);
			startActivityForResult(intent, 0);		
			break;
		}
		case R.id.cameraview:
		{
			CameraItem item = (CameraItem)v.getTag();
			Intent intent = new Intent(IPCListActivity.this, IPCameraInfoActivity.class);
			intent.putExtra("camera_info", item);
			startActivityForResult(intent, 0);				
			break;
		}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){ 
			CameraItem itemChanged = (CameraItem)data.getSerializableExtra("camera_info");
			if (itemChanged.index != -1)
				mycameras.set(itemChanged.index, itemChanged);
			else if (!itemChanged.isEmpty()){
				itemChanged.index = mycameras.size();
				mycameras.add(itemChanged);
			}
            BaseAdapter sAdapter = (BaseAdapter)cameras.getAdapter();
            sAdapter.notifyDataSetChanged();
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// TODO Auto-generated method stub
		if (v.getTag() instanceof ViewHolder) {
			ViewHolder holder = (ViewHolder) v.getTag();
			if (holder != null){
				CameraItem item = (CameraItem)holder.cameraview.getTag();
				Intent intent = new Intent(IPCListActivity.this, IPCPlayerActivity.class);
				intent.putExtra("camera_info", item);
				startActivity(intent);				
			}
		}
	}

	class CameraAdapter extends BaseAdapter {
		protected Context context;
		protected LayoutInflater mInflater;
		
		public CameraAdapter(Context context) {
			super();
			this.context = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			System.out.print("getCount " + mycameras.size());
			return mycameras.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			System.out.print("getview " + position);
			ViewHolder holder = null;
			if (convertView == null){
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.camera_item, null);
				holder.cameraimg = (ImageView)convertView.findViewById(R.id.cameraimg);
				holder.cameratitle = (TextView)convertView.findViewById(R.id.cameratitle);
				holder.camerainfo = (TextView)convertView.findViewById(R.id.camerainfo);
				holder.cameraview = (ImageView)convertView.findViewById(R.id.cameraview);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			CameraItem item = mycameras.get(position);
			holder.cameraimg.setBackgroundResource((Integer)R.drawable.friend_head_one);
			holder.cameratitle.setText(item.name);
			holder.camerainfo.setText(item.detail);
			holder.cameraview.setTag(item);
			return convertView;
		}

		public final class ViewHolder{
			ImageView cameraimg;
			TextView cameratitle;
			TextView camerainfo;
			ImageView cameraview;
			
		}
	}


}
