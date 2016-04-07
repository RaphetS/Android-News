package com.ruanshuo.news.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class CommentBean extends BmobObject {
	private String newsId;
	private String commentContent;
	private Integer praiseNum;
	private List<ReplyBean> replyBeans;
	private String uname;
	private String upic;
	private _User user;
	private List<String> praiseUids;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public List<ReplyBean> getReplyBeans() {
		return replyBeans;
	}

	public void setReplyBeans(List<ReplyBean> replyBeans) {
		this.replyBeans = replyBeans;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpic() {
		return upic;
	}

	public void setUpic(String upic) {
		this.upic = upic;
	}

	public _User getUser() {
		return user;
	}

	public void setUser(_User user) {
		this.user = user;
	}

	public List<String> getPraiseUids() {
		return praiseUids;
	}

	public void setPraiseUids(List<String> praiseUids) {
		this.praiseUids = praiseUids;
	}

}
