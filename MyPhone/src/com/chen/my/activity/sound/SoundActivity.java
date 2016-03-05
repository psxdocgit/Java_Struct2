package com.chen.my.activity.sound;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.chen.my.activity.DefaultETable;
import com.chen.my.activity.MainActivity;
import com.chen.my.activity.MyActivity;
import com.chen.my.activity.R;
import com.chen.my.activity.note.NoteActivity;
import com.chen.my.common.action.MyAction;
import com.chen.my.common.db.ColumnOption;
import com.chen.my.common.db.TableIF;
import com.chen.my.service.MyService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

public class SoundActivity extends MyActivity{

	private AlarmManager alarmManager=null;
	
	private static final long INTERVAL = 1000 * 60 * 60 * 24;// 24h
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);
		setViewsEnabledByModel(1);
		TimePicker.class.cast(findViewById(R.id.sound_timePicker1)).setIs24HourView(true);
		SeekBar.class.cast(findViewById(R.id.sound_seekBar1)).setMax(100);
		searchBtnClick();
		//service start 
//		Map<String,String> map=new HashMap();
//		map.put("serviceCode","SOUND");
		//startMyService(map);
//		registerMyReceiver();
//		for(int i=1;i<24;i++){
//			startServicesByTime(i,0);
//			startServicesByTime(1,10);
//			startServicesByTime(i,20);
//			startServicesByTime(i,30);
//			startServicesByTime(i,40);
//			startServicesByTime(i,50);
//		}
	}

	@Override
	public void receiverAction(Context context, Intent intent) {
		super.receiverAction(context, intent);
		//String value=intent.getStringExtra("count");
		//Log.i("ccjie", "sound activity"+value.toString());
	}
	
	
	public void btnClick(View view){
		switch(view.getId()){
		case R.id.sound_OK_btn:
			setViewsEnabledByModel(1);
			saveBtnClick();
			break;
		case R.id.sound_EDIT_btn:
			setViewsEnabledByModel(2);
			break;
		case R.id.sound_CANCEL_btn:
			setViewsEnabledByModel(1);
			searchBtnClick();
			break;
		case R.id.sound_BACK_btn:
			MyAction.StartNextActivity(SoundActivity.this, MainActivity.class,null);
			break;
		default:
			setViewsEnabledByModel(1);
			break;
		}
	}

	private void saveBtnClick(){
		int hour=0;
		int minute=0;
		int sound=0;
		hour=TimePicker.class.cast(findViewById(R.id.sound_timePicker1)).getCurrentHour();
		minute=TimePicker.class.cast(findViewById(R.id.sound_timePicker1)).getCurrentMinute();
		sound=SeekBar.class.cast(findViewById(R.id.sound_seekBar1)).getProgress();
		
		ColumnOption[] mValue={
				getTableIF().get(DefaultETable.TYPE.getName(), "02"),  //业务
				getTableIF().get(DefaultETable.INT01.getName(), 1),  //顺序
				getTableIF().get(DefaultETable.INT02.getName(), 1),  //星期:1-7
				getTableIF().get(DefaultETable.INT03.getName(), hour),  //小时
				getTableIF().get(DefaultETable.INT04.getName(), minute),  //分钟
				getTableIF().get(DefaultETable.INT05.getName(), 1),  //铃声Tpye
				getTableIF().get(DefaultETable.INT06.getName(), sound)  //音量
				};
		
		Cursor cur=this.getDBHelper().queryAllInfo("02");
		if (cur==null || !cur.moveToFirst()){
			getDBHelper().insert(mValue);
		}else{
			String id=cur.getString(cur.getColumnIndex(TableIF.ID));
			getDBHelper().update(id,mValue);
		}
		if (null!=cur)cur.close();
		//cancelServicesByTime();
		startServicesByTime(hour,minute);
		
		Toast.makeText(this, "Save...", Toast.LENGTH_SHORT).show();
	}
	
	private void searchBtnClick(){
		int hour=0;
		int minute=0;
		int sound=0;
		
		Cursor cur=this.getDBHelper().queryAllInfo("02");
		if (cur==null || !cur.moveToFirst()){
		}else{
			hour=cur.getInt(cur.getColumnIndex(DefaultETable.INT03.getName()));
			minute=cur.getInt(cur.getColumnIndex(DefaultETable.INT04.getName()));
			sound=cur.getInt(cur.getColumnIndex(DefaultETable.INT06.getName()));
//			hour=TimePicker.class.cast(findViewById(R.id.sound_timePicker1)).getCurrentHour();
//			minute=TimePicker.class.cast(findViewById(R.id.sound_timePicker1)).getCurrentMinute();
//			sound=SeekBar.class.cast(findViewById(R.id.sound_seekBar1)).getProgress()*100;
			
		}

		Log.i("ccjie", hour+"-"+minute+"-"+sound);
		
		TimePicker.class.cast(findViewById(R.id.sound_timePicker1)).setCurrentHour(hour);
		TimePicker.class.cast(findViewById(R.id.sound_timePicker1)).setCurrentMinute(minute);
		SeekBar.class.cast(findViewById(R.id.sound_seekBar1)).setProgress(sound);
		if (null!=cur)cur.close();
		Toast.makeText(this, "Search...", Toast.LENGTH_SHORT).show();
	}
	
	private void setViewsEnabledByModel(int model){
		boolean enabled1=false;
		boolean enabled2=false;
		
		switch(model){
		case 1:
			//照会
			enabled1=false;
			enabled2=true;
			break;
		case 2:
			//更改
			enabled1=true;
			enabled2=false;
			break;
		default:
			enabled1=false;
			enabled2=false;
			break;
		}
		
		int[] enabledView1={
				R.id.sound_OK_btn,
				R.id.sound_CANCEL_btn,
				R.id.sound_switch1,
				R.id.sound_switch2,
				R.id.sound_seekBar1
				};
		int[] enabledView2={
				R.id.sound_EDIT_btn
		};
		
		for(int id:enabledView1){
			findViewById(id).setEnabled(enabled1);
		}
		
		for(int id:enabledView2){
			findViewById(id).setEnabled(enabled2);
		}
	}
	
	private void startServicesByTime(int hour,int minute){
		//Intent intent = new Intent("android.provider.my.PHONE_RECEIVED");
		Intent intent = new Intent(this, MyService.class);
		intent.putExtra("activityName", this.getClass().toString());
		intent.putExtra("hour", hour);
		intent.putExtra("minute", minute);

		PendingIntent pi = PendingIntent.getService(SoundActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//设置当前时间
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

//	    getAlarmManager().setRepeating(AlarmManager.RTC_WAKEUP,0,5000, pi);
	    //设置AlarmManager在Calendar对应的时间启动MyService
        // 提示闹钟设置完毕
        getAlarmManager().set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pi);
	    
	}
	
	/**
	 * 闹钟取消
	 */
	private void cancelServicesByTime(){
		Intent intent = new Intent(this, MyService.class);
		PendingIntent pi = PendingIntent.getService(SoundActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//闹钟取消
		alarmManager.cancel(pi);
	    
	}
	
	private AlarmManager getAlarmManager(){
		if(null==alarmManager){
			alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		}
		return alarmManager;
	}
	
}
