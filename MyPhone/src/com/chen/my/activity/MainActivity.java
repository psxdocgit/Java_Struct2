package com.chen.my.activity;

import com.chen.my.activity.note.NoteActivity;
import com.chen.my.activity.sound.SoundActivity;
import com.chen.my.common.action.MyAction;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends MyActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void noteClick(View view){
		MyAction.StartNextActivity(this, NoteActivity.class,null);
	}
	
	public void soundClick(View view){
		MyAction.StartNextActivity(this, SoundActivity.class,null);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
