package com.chen.my.common.action;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class MyAction {
	
	private static final String ACTIVITY_MAP = "activity_map";
	private static final int ACTIVITY_CREATE = 0;

    public static <T extends Activity> boolean StartNextActivity(T activity1,Class<? extends Activity> clazz,Map<String,String> map){
        //Log.i("ccjie",activity1.getClass().toString() + " TO " + clazz.toString());
        try{
            Intent intent = new Intent();
            //在Intent对象当中添加一个键值对  
            if(map!=null){
            	for(String key:map.keySet()){
            		intent.putExtra(key, map.get(key));
            	}
            }
            
//            //取得从上一个Activity当中传递过来的Intent对象  
//            Intent intent = getIntent();  
//            //从Intent当中根据key取得value  
//            String value = intent.getStringExtra("testIntent");
            
            intent.setClass(activity1, clazz);
            activity1.startActivityForResult(intent, ACTIVITY_CREATE);
            activity1.finish();
        }catch (Throwable t){
            return false;
        }
        return true;
    }
}
