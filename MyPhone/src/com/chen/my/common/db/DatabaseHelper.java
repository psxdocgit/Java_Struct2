package com.chen.my.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chen on 2016/2/20.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private TableIF tif = null;
	private String DATABASE_CREATE = "";

	public DatabaseHelper(Context context, TableIF tif) {
		super(context, tif.getTableName(), null, tif.getTableVersion());
		this.tif = tif;
		DATABASE_CREATE = "create table " + tif.getTableName() + " (" + TableIF.ID
				+ " integer primary key autoincrement";
		for (ColumnOption columnN :tif.getColumnOption() ) {
			DATABASE_CREATE += ", " 
							+ columnN.getColumnName() + " "
							+ columnN.getOption().toString();
			if (!columnN.isEmpty()){
				DATABASE_CREATE += "not null";
			}
		}
		DATABASE_CREATE += " )";
		
		//Log.i("ccjie", DATABASE_CREATE);
	}

	/** when api first instanced,it run.  */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + tif.getTableName());
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		onCreate(db);
	}

}
