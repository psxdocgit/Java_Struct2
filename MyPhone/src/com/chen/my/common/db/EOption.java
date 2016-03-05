package com.chen.my.common.db;

public enum EOption {
	NULL(""),
	INT("integer"),
	CHAE("varchar"),
	VCHAE("varchar");
	
	/** 值 */
	private String mValue="";
	
	private EOption(String mValue){
		this.mValue=mValue;
	}
	
	/**
	 * 属性名
	 * @param eo
	 * @return
	 */
	public static EOption getOption(String eo){
		if (null==eo){
			return NULL;
		}
		for(EOption ep:EOption.values()){
			if (eo.equals(ep.mValue)){
				return ep;
			}
		}
		return NULL;
	}
	
	public String toString(){
		return mValue;
	}
}
