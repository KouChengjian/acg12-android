package com.kcj.animationfriend.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;


public class Comment extends BmobObject implements Serializable{

	private User user;
	private String commentContent;
	private int type;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
