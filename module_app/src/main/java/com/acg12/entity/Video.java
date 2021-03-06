package com.acg12.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/24.
 */
public class Video  extends Param {

    private String aid;// 视频av号
    private String typeid;// 视频类型
    private String title;// 视频标题
    private String sbutitle; // tag标签
    private String play;// 视频播放数
    private String review;// 评论数
    private String videoReview;// 视频弹幕数
    private String favorites;// 视频收藏数
    private String author;// Up主
    private String description;// 视频简介
    private String create;// 视频发布时间
    private String pic;// 视频封面地址
    private String credit; // 创建时间
    private String coins;// 视频硬币数
    private String duration;// 视频长度
    private String playUrl; // 视频url
    private String cid; // 弹幕id

    private String bmId; // 番剧id
    private String updateContent;// 更新信息
    private String urlInfo; // 番剧详情

    private List<Video> episodeList = new ArrayList<>(); // 所有集
    private List<Video> seasonList = new ArrayList<>(); // 所有季度


    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSbutitle() {
        return sbutitle;
    }

    public void setSbutitle(String sbutitle) {
        this.sbutitle = sbutitle;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
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

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    public String getBmId() {
        return bmId;
    }

    public void setBmId(String bmId) {
        this.bmId = bmId;
    }

    public List<Video> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Video> episodeList) {
        this.episodeList = episodeList;
    }

    public List<Video> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(List<Video> seasonList) {
        this.seasonList = seasonList;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Video{" +
                "aid='" + aid + '\'' +
                ", typeid='" + typeid + '\'' +
                ", title='" + title + '\'' +
                ", sbutitle='" + sbutitle + '\'' +
                ", play='" + play + '\'' +
                ", review='" + review + '\'' +
                ", videoReview='" + videoReview + '\'' +
                ", favorites='" + favorites + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", create='" + create + '\'' +
                ", pic='" + pic + '\'' +
                ", credit='" + credit + '\'' +
                ", coins='" + coins + '\'' +
                ", duration='" + duration + '\'' +
                ", playUrl='" + playUrl + '\'' +
                ", cid='" + cid + '\'' +
                ", bmId='" + bmId + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", urlInfo='" + urlInfo + '\'' +
                ", episodeList=" + episodeList +
                ", seasonList=" + seasonList +
                '}';
    }
}
