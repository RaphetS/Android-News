package com.ruanshuo.news.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class DynimicBean extends BmobObject{
	private String dynimicContent;
	private List<String> dynimicPics;
	private _User user;
	private DynimicReplyBean dReplys;
	public String getDynimicContent() {
		return dynimicContent;
	}
	public void setDynimicContent(String dynimicContent) {
		this.dynimicContent = dynimicContent;
	}
	public List<String> getDynimicPics() {
		return dynimicPics;
	}
	public void setDynimicPics(List<String> dynimicPics) {
		this.dynimicPics = dynimicPics;
	}
	public _User getUser() {
		return user;
	}
	public void setUser(_User user) {
		this.user = user;
	}
	public DynimicReplyBean getdReplys() {
		return dReplys;
	}
	public void setdReplys(DynimicReplyBean dReplys) {
		this.dReplys = dReplys;
	}
	
}
