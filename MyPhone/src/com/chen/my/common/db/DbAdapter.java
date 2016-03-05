package com.chen.my.common.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.chen.my.activity.DefaultETable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by chen on 2016/2/20.
 */
public class DbAdapter {
    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase sqliteDatabase;
    private TableIF tif=null;


    public DbAdapter(Context context,TableIF tif) {
        this.context = context;
        this.tif=tif;
    }

    /**
     * Open the Database
     */
    public void open(){
        databaseHelper = new DatabaseHelper(context,tif);
        try
        {
            sqliteDatabase = databaseHelper.getWritableDatabase();
        }catch(SQLiteException e){
            sqliteDatabase = databaseHelper.getReadableDatabase();
        }
    }

    /**
     * Close the Database
     */
    public void close()
    {
        sqliteDatabase.close();
    }

    /**
     * Insert the Data
     * @param title
     * @param body
     * @return
     */
    public long insert(ColumnOption[] mValue){
        ContentValues content = new ContentValues();
        for(ColumnOption columnName:mValue){
        	content.put(columnName.getColumnName(),columnName.getValue());
        }
        for(ColumnOption columnName:tif.getColumnOption()){
        	if (!content.containsKey(columnName.getColumnName())){
        		content.put(columnName.getColumnName(), 
        				DefaultETable.getValue(columnName.getColumnName()));
        	}
        }
        return sqliteDatabase.insert(tif.getTableName(), null, content);
    }
    
    /**
     * add
     * @param mValue
     * @return
     */
    public void add(String[] mValue){
    	try{
    		String sql="insert into " + tif.getTableName()+ " values(null";
        	int len=mValue.length;
        	for(int i=0;i<len;i++){
        		sql+=", ?";
        	}
        	sql+=")";
        	sqliteDatabase.execSQL(sql,mValue);
        	sqliteDatabase.setTransactionSuccessful();
    	}finally{
    		sqliteDatabase.endTransaction();
    	}
    }

    /**
     * Delete the record
     * @param rowId
     * @return
     */
    public boolean delete(String id){
        String whereString = tif.ID + "=?";
        return sqliteDatabase.delete(tif.getTableName(), whereString, new String[]{id})>0;
    }

//    /**
//     * Get all Records
//     * @return
//     */
//    public Cursor getAllInfo()
//    {
//    	List<String> columns=Arrays.asList(SETTING.getColumnNames());
//    	columns.add(0, SETTING.ID);
//    	String[] arr=columns.toArray(new String[]{});
//    	for(String tag:arr){
//    		Log.i("ccjie", "getAllInfo "+ tag);
//    	}
//        return sqliteDatabase.query(SETTING.getTableName(), arr, null, null, null, null, null);
//    }

    public Cursor queryAllInfo()
    {
    	String sql="select * from " + tif.getTableName();
    	sql+= " order by "+ TableIF.ID;
    	return sqliteDatabase.rawQuery(sql, null);
    }

    public Cursor queryAllInfo(String type)
    {
    	String sql="select * from " + tif.getTableName() + " where " + DefaultETable.TYPE.getName()+"="+type ;
    	sql+= " order by "+ TableIF.ID;
    	return sqliteDatabase.rawQuery(sql, null);
    }
    
    /**
     * Get the record by condition
     * @param rowId
     * @return
     * @throws SQLException
     */
    public Cursor getDataByID(String id) {
    	
    	String sql="select * from " + tif.getTableName()+ " where " + tif.ID + " = ?";
    	Log.i("ccji;'l'.e", "id "+ sql);
    	Cursor mCursor=sqliteDatabase.rawQuery(sql,new String[]{id}); 	
//        String whereString = SETTING.ID + " = ? ";
//        Cursor mCursor = sqliteDatabase.query(true, SETTING.getTableName(), SETTING.getColumnNames(), whereString,new String[]{""+id}, null, null, null, null);
        if(mCursor!=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean update(String id ,ColumnOption[] columnes){
        ContentValues contents = new ContentValues();
        
        for(ColumnOption co:columnes){
        	contents.put(co.getColumnName(), co.getValue());
        }
        String whereString = tif.ID + "=?";
        
        return sqliteDatabase.update(tif.getTableName(), contents, whereString, new String[]{id})>0;
    }
//
//    public void xinjianDiary(String title,String body){
//
//        Calendar calendar = Calendar.getInstance();
//        String created = calendar.get(Calendar.YEAR) + "/"
//                + calendar.get(Calendar.MONTH) + "/"
//                + calendar.get(Calendar.DAY_OF_MONTH) + " "
//                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
//                + calendar.get(Calendar.MINUTE);
//
//        String insertSQL = "INSERT INTO " + databaseHelper.DATABSE_TABLE
//                +"(" + KEY_ROWID +"," + KEY_TITLE+"," + KEY_BODY +"," + KEY_CREATED + ")"
//                + " values (?,?,?,?)" ;
//        Object[] args = {null,title,body,created};
//        sqliteDatabase.execSQL(insertSQL, args);
//    }
//
//    public void bianjiDiary(long rowId ,String title,String body){
//        Calendar calendar = Calendar.getInstance();
//        String created = calendar.get(Calendar.YEAR) + "/"
//                + calendar.get(Calendar.MONTH) + "/"
//                + calendar.get(Calendar.DAY_OF_MONTH) + " "
//                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
//                + calendar.get(Calendar.MINUTE);
//
//        String updateSQL = "update " + databaseHelper.DATABSE_TABLE
//                +" set " + KEY_TITLE+"=? ," + KEY_BODY +"=? ," + KEY_CREATED + "=? "
//                + " where " + KEY_ROWID + "= ?" ;
//        Object[] args = {title,body,created,rowId};
//        sqliteDatabase.execSQL(updateSQL, args);
//    }
//
//    public void shanchuDiary(long rowId ){
//        String deleteSQL = "delete from "+ databaseHelper.DATABSE_TABLE +" where " + KEY_ROWID + "= ?" ;
//        Object[] args = {rowId};
//        sqliteDatabase.execSQL(deleteSQL, args);
//    }
//
//    public Cursor qudeAllNotes()
//    {
//        String searchSQL = "select _id , title , body ,created from "+ databaseHelper.DATABSE_TABLE;
//        return sqliteDatabase.rawQuery(searchSQL, null);
//    }
}
