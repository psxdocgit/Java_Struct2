package com.struct2.action;

public class Link extends BaseAction{
	
	public String username="";
	public String link="";
	
	public String doing() {
		if (username!=null && username.equals("111")){
			this.addFieldError("username", "XXXXXXXXXXXXXX");
			return INPUT;
		}
		return super.doing();
    }
	
	public void validate() {
		// ここで秘薦チェックとかを佩う。
		this.addFieldError("username", "XXXXXXXXXXXXXX");
		return;

		}
}
