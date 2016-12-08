package com.kcj.animationfriend.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @ClassName: Collect
 * @Description: 采集(不管是自己的还是别人的)
 * @author: KCJ
 * @date:  
 */
public class Collect extends BmobObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private User user; // 用户
	private Album album;// 画集
	private Palette palette; // 画板
	private String content; // 内容
	private Integer type; //  0 自己上传  1 采集

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Palette getPalette() {
		return palette;
	}

	public void setPalette(Palette palette) {
		this.palette = palette;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
