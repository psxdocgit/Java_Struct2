package com.chen.my.common.db;

public interface TableIF {
	public static final String ID="_id";
	public String getTableName();
	public ColumnOption[] getColumnOption();
	public ColumnOption get(String name,int value);
	public ColumnOption get(String name,String value);
	public int getTableVersion();
}
