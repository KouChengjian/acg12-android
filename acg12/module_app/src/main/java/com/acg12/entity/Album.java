package com.acg12.entity;

import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Table;

@Table("album")
public class Album extends Param {

	private static final long serialVersionUID = -7180816291844644837L;

	private String pinId = "";
	private String content;    // 内容
	private int love;      // 喜欢的个数 点赞
	private int favorites; // 收藏的个数 采集
	private String resType;    // 资源类型
	private int resWidth;  // 资源宽度
	private int resHight;  // 资源高度
	@SerializedName("image")
	private String imageUrl;
	private int isCollect; // 是否收藏

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public int getFavorites() {
		return favorites;
	}

	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public int getResWidth() {
		return resWidth;
	}

	public void setResWidth(int resWidth) {
		this.resWidth = resWidth;
	}

	public int getResHight() {
		return resHight;
	}

	public void setResHight(int resHight) {
		this.resHight = resHight;
	}

	public String getPinId() {
		return pinId;
	}

	public void setPinId(String pinId) {
		this.pinId = pinId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}
}
