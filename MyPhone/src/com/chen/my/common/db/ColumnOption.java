/**
 * 表格字段属性
 * */
package com.chen.my.common.db;

public class ColumnOption {
	
	private static final String defaultColumnName=TableIF.ID;
	private static final EOption defaultOption=EOption.VCHAE;
	private static final boolean defaultEmpty=true;
	
	/** 表字段名 */
	private String columnName="";
	/** 表字段属性 */
	private EOption option=EOption.NULL;
	/** 表字段属性是否可以为空 */
	private boolean empty=true;
	/** 表字段属性(长度) */
	private int len=0;
	/** 表字值 */
	private String mValue="";
	
	public ColumnOption() {

	}

	public static ColumnOption getNewColumnOption(){
		ColumnOption reutrnVal=new ColumnOption();
		reutrnVal.columnName=defaultColumnName;
		reutrnVal.option=EOption.INT;
		reutrnVal.empty=false;
		reutrnVal.mValue="0";
		
		return reutrnVal;
	}
	
	public static ColumnOption getNewColumnOption(String columnName,EOption option,boolean isEmpty,String mValue){
		ColumnOption reutrnVal=new ColumnOption();
		reutrnVal.columnName=columnName;
		if (option==EOption.NULL){
			reutrnVal.option=EOption.VCHAE;
		}else{
			reutrnVal.option=option;
		}
		reutrnVal.empty=isEmpty;
		reutrnVal.mValue=mValue;
		
		return reutrnVal;
	}

	public static ColumnOption getNewColumnOption(String columnName,EOption option,boolean isEmpty,int mValue){
		ColumnOption reutrnVal=new ColumnOption();
		reutrnVal.columnName=columnName;
		if (option==EOption.NULL){
			reutrnVal.option=EOption.VCHAE;
		}else{
			reutrnVal.option=option;
		}
		reutrnVal.empty=isEmpty;
		reutrnVal.mValue=String.valueOf(mValue);
		
		return reutrnVal;
	}
	//------------------------------------------------------------------
	// get and set
	//------------------------------------------------------------------
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public EOption getOption() {
		return option;
	}
	
	public void setOption(EOption option) {
		this.option = option;
	}
	
	public int getLen() {
		return len;
	}
	
	public void setLen(int len) {
		this.len = len;
	}
	
	public int getIntValue() {
		try{
			return Integer.valueOf(mValue);
		}catch(Exception e){
			return 0;
		}
		
	}
	
	public String getValue() {
		if (null==mValue){
			return "";
		}
		return mValue;
	}
	
	public void setValue(String mValue) {
		this.mValue = mValue;
	}

	public void setValue(int mValue) {
		this.mValue = String.valueOf(mValue);
	}
	
	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
}
