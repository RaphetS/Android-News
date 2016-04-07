package com.ruanshuo.news.bean;

import cn.bmob.v3.BmobObject;

public class ReplyBean extends BmobObject {
	private String replyContent;
	private _User user;
	private String commentId;
	private String replyUname;
	private String replyUpic;
	private String commentUserId;

	public String getReplyUname() {
		return replyUname;
	}

	public void setReplyUname(String replyUname) {
		this.replyUname = replyUname;
	}

	public String getReplyUpic() {
		return replyUpic;
	}

	public void setReplyUpic(String replyUpic) {
		this.replyUpic = replyUpic;
	}

	public _User getUser() {
		return user;
	}

	public void setUser(_User user) {
		this.user = user;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(String commentUserId) {
		this.commentUserId = commentUserId;
	}

}
