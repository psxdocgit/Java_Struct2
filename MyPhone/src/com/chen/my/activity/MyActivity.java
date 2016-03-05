package com.chen.my.activity;

import java.util.Map;

import com.chen.my.common.db.ColumnOption;
import com.chen.my.common.db.DbAdapter;
import com.chen.my.common.db.EOption;
import com.chen.my.common.db.TableIF;
import com.chen.my.service.MyReceiver;
import com.chen.my.service.MyService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MyActivity extends Activity {

	private DbAdapter dbAdapter = null;
	private MyReceiver receiver=null;
	
	/* 恢复(non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
	}

	/* 暂停(non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		dbClose();
		unregisterMyReceiver();
	}
	
	
	/**
	 * start service
	 * @param map
	 */
	protected final void startMyService(Map<String,String> map){
		Intent i = new Intent(this, MyService.class);  
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		if (null != map) {
			for (String key : map.keySet()) {
				i.putExtra(key, map.get(key));
			}
		}
		startService(i);
	}
	
	/**
	 *  广播接收
	 */
	protected final void registerMyReceiver(){
		if(null==receiver){
			IntentFilter filter = new IntentFilter("android.provider.my.PHONE_RECEIVED");   
			receiver = new MyReceiver(this);   
			registerReceiver(receiver, filter);
		}

	}
	
	/**
	 * 广播取消
	 */
	protected void unregisterMyReceiver(){
		if(null!=receiver){
			unregisterReceiver(receiver);
		}
		receiver=null;
	}
	
	/**
	 * 广播接收事件
	 * @param context
	 * @param intent
	 */
	public void receiverAction(Context context, Intent intent){
		//String message = intent.getStringExtra("data");
		return;
	}
	
	
	/**
	 * DB Helper
	 * @return
	 */
	protected DbAdapter getDBHelper(){
		if(null==dbAdapter){
			dbAdapter = new DbAdapter(this, getTableIF());
			dbAdapter.open();
			
		}
		return dbAdapter;
	}
	
	/**
	 * close DB
	 */
	protected void dbClose(){
		if (null!=dbAdapter){
			dbAdapter.close();
		}
		dbAdapter=null;
	}
	/**
	 * 表接口
	 * @return
	 */
	protected TableIF getTableIF(){
		return new DefaultTable();
	}
	
	/**
	 * 默认表
	 * @author chen
	 *
	 */
	public class DefaultTable implements TableIF{

		@Override
		public String getTableName() {
			return "DafaultTable";
		}

		@Override
		public int getTableVersion() {
			return 1;
		}

		@Override
		public ColumnOption[] getColumnOption() {

			return new ColumnOption[]{
					ColumnOption.getNewColumnOption(
							DefaultETable.TYPE.getName()
							,DefaultETable.TYPE.getOption()
							,DefaultETable.TYPE.canEmpty()
							,DefaultETable.TYPE.getValue()
							),
					ColumnOption.getNewColumnOption("title",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("subTitle",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("text01",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("text02",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("text03",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("text04",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("text05",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("text06",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("text07",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("text08",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("text09",EOption.VCHAE,true,""),
					ColumnOption.getNewColumnOption("int01",EOption.INT,true,0),
					ColumnOption.getNewColumnOption("int02",EOption.INT,true,0),
					ColumnOption.getNewColumnOption("int03",EOption.INT,true,0),
					ColumnOption.getNewColumnOption("int04",EOption.INT,true,0),
					ColumnOption.getNewColumnOption("int05",EOption.INT,true,0),
					ColumnOption.getNewColumnOption("int06",EOption.INT,true,0),
					ColumnOption.getNewColumnOption("int07",EOption.INT,true,0),
					ColumnOption.getNewColumnOption("int08",EOption.INT,true,0),
					ColumnOption.getNewColumnOption("int09",EOption.INT,true,0)
			};
		}
		
		public ColumnOption get(String name,String value){
			if (null==name){
				return null;
			}
			
			for(ColumnOption co:getColumnOption()){
				if(name.equals(co.getColumnName())){
					co.setValue(value);
					return co;
				}
			}
			
			return null;
		}
		
		public ColumnOption get(String name,int value){
			return get(name,String.valueOf(value));
		}
	}
}
