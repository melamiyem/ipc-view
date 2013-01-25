package com.webspider.IPCView;

import java.io.Serializable;

public class CameraItem implements Serializable {
	protected int index;
	protected String name;
	protected String address;
	protected String port;
	protected String detail;
	protected String username;
	protected String password;
	protected int img;
	public CameraItem(){
		index = -1;
	}
	
	public boolean isEmpty(){
		return name.isEmpty() || address.isEmpty();
	}
}
