package com.ruanshuo.news.bean;

public class NewsItem {
//	private int id;
//
//	/**
//	 * 标题
//	 */
//	private String title;
//	/**
//	 * 链接
//	 */
//	private String link;
//	/**
//	 * 发布日期
//	 */
//	private String time;
//	/**
//	 * 图片的链接
//	 */
//	private String img;
//
//	/**
//	 * 类型  
//	 * 
//	 */
//	private int newsType;
//
//	public int getNewsType()
//	{
//		return newsType;
//	}
//
//	public void setNewsType(int newsType)
//	{
//		this.newsType = newsType;
//	}
//
//	public String getTitle()
//	{
//		return title;
//	}
//
//	public void setTitle(String title)
//	{
//		this.title = title;
//	}
//
//	public String getLink()
//	{
//		return link;
//	}
//
//	public void setLink(String link)
//	{
//		this.link = link;
//	}
//
//	public int getId()
//	{
//		return id;
//	}
//
//	public void setId(int id)
//	{
//		this.id = id;
//	}
//
//	public String getTime()
//	{
//		return time;
//	}
//
//	public void setTime(String time)
//	{
//		this.time = time;
//	}
//
//	public String getImg()
//	{
//		return img;
//	}
//
//	public void setImg(String img)
//	{
//		this.img = img;
//	}
//	@Override
//	public String toString()
//	{
//		return "NewsItem [id=" + id + ", title=" + title + ", link=" + link + ", time=" + time + ", img=" + img
//				+ ", newsType=" + newsType + "]";
//	}

	
	private String c_click;
	private String detail;
	private String goods_id;
	private String goods_name;
	private String goods_thumb;
	
	private String add_time;
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getC_click() {
		return c_click;
	}
	public void setC_click(String c_click) {
		this.c_click = c_click;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_thumb() {
		return goods_thumb;
	}
	public void setGoods_thumb(String goods_thumb) {
		this.goods_thumb = goods_thumb;
	}
	@Override
	public String toString() {
		return "NewsItem [c_click=" + c_click + ", detail=" + detail + ", goods_id=" + goods_id + ", goods_name="
				+ goods_name + ", goods_thumb=" + goods_thumb + ", add_time=" + add_time + "]";
	}
	
	
}