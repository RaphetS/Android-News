package com.ruanshuo.news.bean;

public class NewsData {
	private int id;
	private String titel;
	private String img;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "NewsData [id=" + id + ", titel=" + titel + ", img=" + img + "]";
	}
	
}
