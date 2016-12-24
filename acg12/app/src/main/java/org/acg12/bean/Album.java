package org.acg12.bean;

import android.widget.ImageView;

import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;


@Table("album")
public class Album extends Param {

	private static final long serialVersionUID = -7180816291844644837L;

	private String pinId = "";
	private String content; // 内容
	private Integer love; // 喜欢的个数  点赞
	private Integer favorites;// 收藏的个数 采集
	private String resType; // 资源类型
	private Integer resWidth; // 资源宽度
	private Integer resHight; // 资源高度
	private String imageUrl;
	private ArrayList<String> urlList; // URL

	private ImageView transitionView; // 转场动画

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
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

	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	public String getPinId() {
		return pinId;
	}

	public void setPinId(String pinId) {
		this.pinId = pinId;
	}

	public ImageView getTransitionView() {
		return transitionView;
	}

	public void setTransitionView(ImageView transitionView) {
		this.transitionView = transitionView;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
