/**
 * 服务不能自己运行，需要通过调用Context.startService()或Context.bindService()方法启动服务
 * 使用startService()方法启用服务，访问者与服务之间没有关连,即使访问者退出了，服务仍然运行
 * 使用bindService()方法启用服务，访问者与服务绑定在了一起，访问者一旦退出，服务也就终止
 * 
 * 采用Context.startService()方法启动服务，只能调用Context.stopService()方法结束服务
 * 
 * */
package com.chen.my.service;

import java.util.Calendar;

import com.chen.my.activity.sound.SoundActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service{

	private AlarmManager alarmManager=null;
	private Intent intent = new Intent("android.provider.my.PHONE_RECEIVED");
	
	
	private int index=0;
	/* (non-Javadoc)
	 * 该服务不存在需要被创建时被调用，不管startService()还是bindService()都会在启动时调用该方法
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		//Log.i("ccjie", "MyService onCreate()");
		//发送的是普通广播，所有订阅者都有机会获得并进行处理。 
		//Context.sendBroadcast() 
		
		// 发送的是有序广播，系统会根据接收者声明的优先级别按顺序逐个执行接收者，前面的接收者有权终止广播(BroadcastReceiver.abortBroadcast())
		//前面的接收者可以将数据通过setResultExtras(Bundle)方法存放进结果对象，然后传给下一个接收者，
		//下一个接收者通过代码：Bundle bundle = getResultExtras(true))可以获取上一个接收者存入在结果对象中的数据。
		//Context.sendOrderedBroadcast() 
		
		//发送Action为com.example.communication.RECEIVER的广播
//		intent.putExtra("progress", 50);
//		sendBroadcast(intent);  
	}

	/* (non-Javadoc)
	 * 该服务被销毁时调用该方法  
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}


	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String activityName = intent.getStringExtra("activityName");
		if (SoundActivity.class.toString()==activityName){
			Toast.makeText(this, activityName, Toast.LENGTH_LONG).show();;
		}
		//Log.i("ccjie", "MyService onStartCommand..."+startId);
		
		//----------------------------------------------------
//		String value = intent.getStringExtra("serviceCode");
//		 if (null !=value) Log.i("ccjie", "service code:"+value);
//		 value = intent.getStringExtra("startServicesByTime");
//		 if (null !=value) Log.i("ccjie", "in MySerivce startServicesByTime:"+value);
		
//		 new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while(true){
//					index++;
//					Log.i("ccjie", "myservice run "+index);
//					if (index>10) break;
//					//Intent intent = new Intent(""); 
//					MyService.this.intent.putExtra("count", index); 
//					sendBroadcast(MyService.this.intent);
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						Log.i("ccjie", "myservice interruptedexception ");
//					}
//				}
//			}
//		}).start();
		//----------------------------------------------------
		return super.onStartCommand(intent, flags, startId);
	}

	/* (non-Javadoc)
	 * 其他对象通过bindService方法通知该Service时该方法会被调用
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
