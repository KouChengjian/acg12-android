package com.kcj.animationfriend.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Video
 * @Description: 视频信息
 * @author:
 * @date:
 */
public class Video implements Serializable {

	private static final long serialVersionUID = 1078970857241756601L;
	
	private String aid;// 视频av号
	private String typeid;// 视频类型
	private String title;// 视频标题
	private String sbutitle;
	private String play;// 视频播放数
	private String review;// 评论数
	private String videoReview;// 视频弹幕数
	private String favorites;// 视频收藏数
	private String mid;
	private String author;// Up主
	private String description;// 视频简介
	private String create;// 视频发布时间
	private String pic;// 视频封面地址
	private String credit; // 创建时间
	private String coins;// 视频硬币数
	private String duration;// 视频长度

	// 后续添加的字段
	private String urlInfo;// 连接详细信息
	private String updateContent;// 更新信息
	private List<Video> videoList; // 季度
	public  List<Video> relatedList ;// 相关视频

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

	public String getSbutitle() {
		return sbutitle;
	}

	public void setSbutitle(String sbutitle) {
		this.sbutitle = sbutitle;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getVideoReview() {
		return videoReview;
	}

	public void setVideoReview(String videoReview) {
		this.videoReview = videoReview;
	}

	public String getFavorites() {
		return favorites;
	}

	public void setFavorites(String favorites) {
		this.favorites = favorites;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreate() {
		return create;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getCoins() {
		return coins;
	}

	public void setCoins(String coins) {
		this.coins = coins;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

//	public String getVideoAid() {
//		return videoAid;
//	}
//
//	public void setVideoAid(String videoAid) {
//		this.videoAid = videoAid;
//	}

	public String getUrlInfo() {
		return urlInfo;
	}

	public void setUrlInfo(String urlInfo) {
		this.urlInfo = urlInfo;
	}

	public String getUpdateContent() {
		return updateContent;
	}

	public void setUpdateContent(String updateContent) {
		this.updateContent = updateContent;
	}

	public List<Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}

	public List<Video> getRelatedList() {
		return relatedList;
	}

	public void setRelatedList(List<Video> relatedList) {
		this.relatedList = relatedList;
	}
	
}
