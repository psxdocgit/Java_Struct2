package com.struct2.action;

import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class BaseAction extends ActionSupport implements BaseActionInterface {

	public String doing(){
		try{
			//�޴���
		}catch(Throwable t){
			throw t;
		}
		return SUCCESS;
	}
	
}
