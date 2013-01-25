package com.webspider.IPCView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class IPCameraInfoActivity extends Activity implements OnClickListener{
	protected ArrayList<Map<String, String>> listData;
	protected ArrayList<Map<String, String>> splitData; 
	private CameraItem mCameraItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.camerasetting);
		
		mCameraItem = (CameraItem)getIntent().getSerializableExtra("camera_info");
		ListView list = (ListView) findViewById(R.id.list);
		initData();
		SettingAdapter adapter = new SettingAdapter(this);
		list.setAdapter(adapter);
	}

	@Override
	public void finish() {
		Map<String, String> mp = null;
		for (int i = 0; i < listData.size(); i++) {
			mp = listData.get(i);
			String itemTitle = mp.get("itemTitle");
			if (itemTitle == "Camera name"){
				mCameraItem.name = mp.get("content");
			}else if (itemTitle == "Hostname"){
				mCameraItem.address = mp.get("content");
			}else if (itemTitle == "Port number"){
				mCameraItem.port = mp.get("content");
			}else if (itemTitle == "User name"){
				mCameraItem.username = mp.get("content");
			}else if (itemTitle == "Password"){
				mCameraItem.password = mp.get("content");
			}
		}
		Intent intent = new Intent();
		intent.putExtra("camera_info", mCameraItem);
		setResult(RESULT_OK, intent);

		super.finish();
	}

	protected void initData(){
		listData = new ArrayList<Map<String, String>>();
		splitData = new ArrayList<Map<String, String>>();
		
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("itemTitle", "Camera Settings");
		listData.add(mp);
		splitData.add(mp);
		// Camera Settings
		mp = new HashMap<String, String>();
		mp.put("itemTitle", "Camera name");
		mp.put("content", mCameraItem.name);
		listData.add(mp);
		mp = new HashMap<String, String>();
		mp.put("itemTitle", "Hostname");
		mp.put("content", mCameraItem.address);
		listData.add(mp);
		mp = new HashMap<String, String>();
		mp.put("itemTitle", "Port number");
		mp.put("content", mCameraItem.port);
		mp.put("isnumber", "1");
		listData.add(mp);
		// Login Settings
		mp = new HashMap<String, String>();
		mp.put("itemTitle", "Login Settings");
		listData.add(mp);
		splitData.add(mp);
		mp = new HashMap<String, String>();
		mp.put("itemTitle", "User name");
		mp.put("content", mCameraItem.username);
		listData.add(mp);
		mp = new HashMap<String, String>();
		mp.put("itemTitle", "Password");
		mp.put("content", mCameraItem.password);
		listData.add(mp);
		
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.setitem){
			final Map<String, String> mp = (Map<String, String>)v.getTag();
			changeItenContent(mp);
		}else if (id == R.id.f_main_back){
			finish();
		}
	}

	class SettingAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		SettingAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (splitData.contains(listData.get(position))) {
				convertView = mInflater.inflate(R.layout.setting_tag, null);
			} else {
				convertView = mInflater.inflate(R.layout.setting_item, null); 
				TextView textView = (TextView)convertView.findViewById(R.id.iteminfo);
				textView.setText(listData.get(position).get("content"));
				ImageView imageView = (ImageView)convertView.findViewById(R.id.setitem);
				imageView.setTag(listData.get(position));
			}
			TextView textView = (TextView) convertView.findViewById(R.id.itemTitle);
			textView.setText(listData.get(position).get("itemTitle"));  
			return convertView;
		}

		@Override
		public boolean isEnabled(int position) {
			 if(splitData.contains(listData.get(position))) {
				 return false;
			 }  
			 return super.isEnabled(position);
		}
		
	}

	private void changeItenContent(final Map<String, String> mp){
		String IsNumber = mp.get("isnumber");
		final boolean isNumber = (IsNumber != null);
		final EditText editText = new EditText(this);
		if (isNumber == true)
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		editText.setText(mp.get("content"));
		new AlertDialog.Builder(this)  
		.setTitle(mp.get("itemTitle"))  
		.setIcon(android.R.drawable.ic_dialog_info)  
		.setView(editText)  
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				/* User clicked OK so do some stuff */
				mp.put("content", editText.getText().toString());
			}
		})  
		.setNegativeButton("取消", null)  
		.show();		
	}
}
