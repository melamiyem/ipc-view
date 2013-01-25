package com.webspider.IPCView;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class IPCViewActivity extends TabActivity implements OnClickListener {
	protected String[] menus = { "列表", "其他", "设置" };
	protected TabHost tabHost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
		tabHost = getTabHost();
		tabHost.addTab(newTabSpec(0, new Intent(this,
				IPCListActivity.class)));
		tabHost.addTab(newTabSpec(1, new Intent(this,
				IPCOtherActivity.class)));
		tabHost.addTab(newTabSpec(2, new Intent(this,
				IPCSettingActivity.class)));
    }
	public TabSpec newTabSpec(int index, Intent intent) {
		TabSpec tabSpec = tabHost.newTabSpec(menus[index]);
		tabSpec.setIndicator(menus[index]);
		tabSpec.setContent(intent);
		return tabSpec;
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.friend_tab:
			tabHost.setCurrentTabByTag(menus[0]);
			break;
		case R.id.search_tab:
			tabHost.setCurrentTabByTag(menus[1]);
			break;
		case R.id.setting_tab:
			tabHost.setCurrentTabByTag(menus[3]);
			break;
		}		
	}
}