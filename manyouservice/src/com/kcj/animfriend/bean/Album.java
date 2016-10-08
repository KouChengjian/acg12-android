package com.kcj.animfriend.bean;

import java.util.ArrayList;

public class Album {

	private String pinId = "";
	private String content; // 内容
	private ArrayList<String> urlList; // URl  
	private Integer resWidth; // 资源宽度
	private Integer resHight; // 资源高度
	private Integer love; // 喜欢的个数  点赞
	private Integer favorites;// 收藏的个数 采集

	public String getPinId() {
		return pinId;
	}

	public void setPinId(String pinId) {
		this.pinId = pinId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	public Integer getResWidth() {
		return resWidth;
	}

	public void setResWidth(Integer resWidth) {
		this.resWidth = resWidth;
	}

	public Integer getResHight() {
		return resHight;
	}

	public void setResHight(Integer resHight) {
		this.resHight = resHight;
	}

	public Integer getLove() {
		return love;
	}

	public void setLove(Integer love) {
		this.love = love;
	}

	public Integer getFavorites() {
		return favorites;
	}

	public void setFavorites(Integer favorites) {
		this.favorites = favorites;
	}

	@Override
	public String toString() {
		return "Album [pin_id=" + pinId + ", content=" + content
				+ ", urlList=" + urlList + ", resWidth=" + resWidth
				+ ", resHight=" + resHight + ", love=" + love + ", favorites="
				+ favorites + "]";
	}
	
}
