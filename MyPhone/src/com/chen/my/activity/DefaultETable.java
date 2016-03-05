package com.chen.my.activity;

import com.chen.my.common.db.EOption;
import com.chen.my.common.db.TableIF;

public enum DefaultETable {
	ID(TableIF.ID,EOption.INT,false,"0"),
	TYPE("type",EOption.INT,false,"0"),
	TITLE("title",EOption.VCHAE,true,""),
	SUBTITLE("subTitle",EOption.VCHAE,true,""),
	TEXT01("text01",EOption.VCHAE,true,""),
	TEXT02("text02",EOption.VCHAE,true,""),
	TEXT03("text03",EOption.VCHAE,true,""),
	TEXT04("text04",EOption.VCHAE,true,""),
	TEXT05("text05",EOption.VCHAE,true,""),
	TEXT06("text06",EOption.VCHAE,true,""),
	TEXT07("text07",EOption.VCHAE,true,""),
	TEXT08("text08",EOption.VCHAE,true,""),
	TEXT09("text09",EOption.VCHAE,true,""),
	INT01("int01",EOption.INT,true,"0"),
	INT02("int02",EOption.INT,true,"0"),
	INT03("int03",EOption.INT,true,"0"),
	INT04("int04",EOption.INT,true,"0"),
	INT05("int05",EOption.INT,true,"0"),
	INT06("int06",EOption.INT,true,"0"),
	INT07("int07",EOption.INT,true,"0"),
	INT08("int08",EOption.INT,true,"0"),
	INT09("int09",EOption.INT,true,"0")
	;
	
	private final String name;
	private final EOption option;
	private final boolean flag;
	private final String value;
	
	private DefaultETable(String name,EOption option,boolean flag,String value){
		this.name=name;
		this.option=option;
		this.flag=flag;
		this.value=value;
		
	}
	
	public String getName(){
		return name;
	}
	public EOption getOption(){
		return option;
	}
	public boolean canEmpty(){
		return flag;
	}
	public String getValue(){
		return value;
	}
	
	public static String getValue(String name){
		if(name==null)return "";
		for(DefaultETable v:DefaultETable.values()){
			if(name.equals(v.name)){
				return v.value;
			}
		}
		return "";
	}
}
