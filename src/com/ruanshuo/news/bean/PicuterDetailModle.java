
package com.ruanshuo.news.bean;

import java.io.Serializable;

public class PicuterDetailModle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * title
	 */
	private String title;
	/**
	 * pic
	 */
	private String pic;
	/**
	 * alt
	 */
	private String alt;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
}
