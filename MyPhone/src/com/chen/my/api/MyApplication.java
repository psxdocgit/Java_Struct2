package com.chen.my.api;

import java.util.HashMap;

import android.app.Application;

public class MyApplication extends Application{

	private HashMap<String, String> request=null;

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	private HashMap<String,String> getRequest(){
		if (request==null){
			request=new HashMap<String,String>();
		}
		return request;
	}
	public void put(String key,String obj){
		getRequest().put(key, obj);
	}
	
	public String get(String key){
		return getRequest().get(key);
	}
	
}
