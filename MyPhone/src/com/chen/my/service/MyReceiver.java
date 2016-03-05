/**
 * 广播接收者
 * */
package com.chen.my.service;

import com.chen.my.activity.MyActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver{

	private MyActivity ower=null;
	
	public MyReceiver(MyActivity activity){
		ower=activity;
	}
	/* (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		//ower.receiverAction(context,intent);
		int count = intent.getIntExtra("count", 0);
		//Log.i("ccjie", "my receiver |"+count);
	}

}
