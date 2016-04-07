package com.ruanshuo.news.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class _User extends BmobUser {
	private String pic;
	private List<String> collectionNewsIds;

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public List<String> getCollectionNewsIds() {
		return collectionNewsIds;
	}

	public void setCollectionNewsIds(List<String> collectionNewsIds) {
		this.collectionNewsIds = collectionNewsIds;
	}

}
