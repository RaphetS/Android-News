package com.ruanshuo.news.bean;

import cn.bmob.v3.BmobObject;

public class DynimicReplyBean extends BmobObject{
	private String drContent;
	private _User user;
	private DynimicBean dynimic;
	public String getDrContent() {
		return drContent;
	}
	public void setDrContent(String drContent) {
		this.drContent = drContent;
	}
	public _User getUser() {
		return user;
	}
	public void setUser(_User user) {
		this.user = user;
	}
	public DynimicBean getDynimic() {
		return dynimic;
	}
	public void setDynimic(DynimicBean dynimic) {
		this.dynimic = dynimic;
	}
	
}
