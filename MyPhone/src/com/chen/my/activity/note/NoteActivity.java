package com.chen.my.activity.note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chen.my.activity.DefaultETable;
import com.chen.my.activity.MainActivity;
import com.chen.my.activity.MyActivity;
import com.chen.my.activity.R;
import com.chen.my.common.action.MyAction;
import com.chen.my.common.db.ColumnOption;
import com.chen.my.common.db.TableIF;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * @author chen
 *
 */
public class NoteActivity extends MyActivity {

	List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
	/** 0:一栏;
	 * 1:新规入力
	 * 2:更新入力
	 * 3:照会
	 **/
	private int model=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUIByModel(model,"","");
	}

	
	/**
	 * 
	 * @param model
	 */
	private void setUIByModel() {
		setUIByModel(0, "", "");
	}
	
	private void setUIByModel(int model) {
		setUIByModel(model, "", "");
	}
	
	private void setUIByModel(int model, String title, String text) {

		switch (model) {
		case 1:
			setContentView(R.layout.activity_note2);
			EditText.class.cast(findViewById(R.id.node2ETTitle)).setText("");
			EditText.class.cast(findViewById(R.id.node2ETText)).setText("");
			break;
		case 2:
			setContentView(R.layout.activity_note2);
			EditText.class.cast(findViewById(R.id.node2ETTitle)).setText(title);
			EditText.class.cast(findViewById(R.id.node2ETText)).setText(text);
			break;
		case 3:
			setContentView(R.layout.activity_note2);
			EditText.class.cast(findViewById(R.id.node2ETTitle)).setText(title);
			EditText.class.cast(findViewById(R.id.node2ETText)).setText(text);
			
			//Search Cancel is enable
			EditText.class.cast(findViewById(R.id.node2ETTitle)).setEnabled(false);
			EditText.class.cast(findViewById(R.id.node2ETText)).setEnabled(false);
			Button.class.cast(findViewById(R.id.note2SaveBtn)).setEnabled(false);
			Button.class.cast(findViewById(R.id.note2SearchBtn)).setEnabled(false);
			Button.class.cast(findViewById(R.id.note2DelBtn)).setEnabled(false);
			break;
		default:
			setContentView(R.layout.activity_note);
			AddViewToListView();
		}
	}
	
	private void setUIByModel(int model, String title, String text,String id){
		setUIByModel(model, title, text);
		switch (model){
		case 1:
			EditText.class.cast(findViewById(R.id.note2ETid)).setText("");
		case 2:
			EditText.class.cast(findViewById(R.id.note2ETid)).setText(id);
		default:
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	} 
	
	public void note2BackBtnClick(View view) {
		boolean checked=false;
		if(model==3){
			checked=true;
		}
		model=0;
		setUIByModel();
		CheckBox.class.cast(findViewById(R.id.noteCB)).setChecked(checked);
		return;
	}
	
	public void note2SaveBtnClick(View view) {
		//String[] { "title", "text1", "text2", "text3", "writeDate" };
		String title=EditText.class.cast(findViewById(R.id.node2ETTitle)).getText().toString();
		String text=EditText.class.cast(findViewById(R.id.node2ETText)).getText().toString();
		ColumnOption[] mValue={
				getTableIF().get(DefaultETable.TYPE.getName(), "01"),  //业务
				getTableIF().get(DefaultETable.TITLE.getName(), title),  //主题
				getTableIF().get(DefaultETable.TEXT01.getName(), text),  //内容
				getTableIF().get(DefaultETable.INT01.getName(), 19840313)  //时间
				};
		switch(model){
		case 1:
			getDBHelper().insert(mValue);
			break;
		case 2:
			String id=EditText.class.cast(findViewById(R.id.note2ETid)).getText().toString();
			getDBHelper().update(id,mValue);
			break;
		default:
			break;
		}
		
		Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
		return;
	}

	/**
	 * @param view
	 */
	public void note2SearchBtnClick(View view){
		String title="";
		String text="";
		switch(model){
		case 1:
			break;
		case 2:
			String id=EditText.class.cast(findViewById(R.id.note2ETid)).getText().toString();
			Cursor cur=getDBHelper().getDataByID(id);
			if(cur!=null){
				title=cur.getString(cur.getColumnIndex(DefaultETable.TITLE.getName()));
				text=cur.getString(cur.getColumnIndex(DefaultETable.TEXT01.getName()));
			}
			break;
		default:
			break;
		}

		EditText.class.cast(findViewById(R.id.node2ETTitle)).setText(title);
		EditText.class.cast(findViewById(R.id.node2ETText)).setText(text);
		Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 返回到一栏
	 * @param view
	 */
	public void note2DelBtnClick(View view) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
                NoteActivity.this);
         builder.setTitle("提示");
         builder.setMessage("是否删除后返回?");
         builder.setIcon(R.drawable.ic_launcher);
         builder.setNegativeButton("确定", new OnClickListener() {
			/* 
			 * 确定  action
			 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
			 */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (model) {
				case 2:
					String id=EditText.class.cast(findViewById(R.id.note2ETid)).getText().toString();
					getDBHelper().delete(id);		
					break;
				default:
					break;
				}
				model=0;
				setUIByModel();
				return;
			}
        	 
         });
         
		builder.setPositiveButton("否定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//Log.i("ccjie", "cancel3");
				dialog.dismiss();
				return;
			}
         });
		
		builder.show();
	}

	public void noteBackBtnClick(View view){
		MyAction.StartNextActivity(NoteActivity.this, MainActivity.class,null);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * add view to ListView
	 */
	public void AddViewToListView(){
		ListView lv = ListView.class.cast(findViewById(R.id.noteLv));
		data.clear();
		Cursor cur=getDBHelper().queryAllInfo("1");
		if (cur!=null){
			while(cur.moveToNext()){
				HashMap<String, Object> item = new HashMap<String, Object>();  
	            item.put(TableIF.ID, cur.getString(cur.getColumnIndex(TableIF.ID)));  
	            item.put("listItemName", cur.getString(cur.getColumnIndex(TableIF.ID)));  
	            item.put("listItemText", cur.getString(cur.getColumnIndex(DefaultETable.TITLE.getName())));    
	            data.add(item);
			}
		}
		
		//PLUS Item 
		HashMap<String, Object> item = new HashMap<String, Object>();  
        item.put(TableIF.ID, "-99");  
        item.put("listItemName", " ");  
        item.put("listItemText", "   PLUS(+)");    
        data.add(item);
        
		//创建SimpleAdapter适配器将数据绑定到item显示控件上
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.list_item,   
                new String[]{"listItemName", "listItemText"}, new int[]{R.id.listItemName, R.id.listItemText});  
		//实现列表的显示  
	    lv.setAdapter(adapter);  
	    //条目点击事件  
	    lv.setOnItemClickListener(new ItemClickListener());
	}
	      
    /**
     * 获取点击事件
     * @author chen
     *
     */
    private final class ItemClickListener implements OnItemClickListener{
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
            ListView listView = (ListView) parent;  
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);  
            String _id = data.get("_id").toString();
            String title = "";
            String text = "";
            
            if ("-99".equals(_id)){
            	model=1;
            }else{
            	model=2;
            	Cursor cur=getDBHelper().getDataByID(_id);
            	if(cur!=null){
            		title=cur.getString(cur.getColumnIndex(DefaultETable.TITLE.getName()));
            		text=cur.getString(cur.getColumnIndex(DefaultETable.TEXT01.getName()));
            		cur.close();
            	}
            }
            
            boolean onlyLook=CheckBox.class.cast(findViewById(R.id.noteCB)).isChecked();
            if (onlyLook && model==1){
            	Toast.makeText(NoteActivity.this, "照会不可新规", Toast.LENGTH_SHORT).show();
            	return;
            }else if(onlyLook && model==2){
            	model=3;
            }
            NoteActivity.this.setUIByModel(model, title, text,_id);
        }  
    }
    
}
