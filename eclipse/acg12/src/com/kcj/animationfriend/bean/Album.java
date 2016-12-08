package com.kcj.animationfriend.bean;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

import com.liteutil.annotation.Column;
import com.liteutil.annotation.PrimaryKey;
import com.liteutil.annotation.Table;
import com.liteutil.orm.db.enums.AssignType;

/**
 * @ClassName: Album
 * @Description: 画集 相册(一个记录)
 * @author: KCJ
 * @date:  
 */
@Table("AlbumCache")
public class Album extends BmobObject implements Serializable{

	@PrimaryKey(AssignType.AUTO_INCREMENT)
	@Column("_id")
    protected long id;
	
	private static final long serialVersionUID = 1L;
	private User user; // 用户
	private Palette palette; // 画板
	private String content; // 内容
	private Integer love; // 喜欢的个数  点赞
	private Integer favorites;// 收藏的个数 采集
	private String resType; // 资源类型
	private Integer resWidth; // 资源宽度
	private Integer resHight; // 资源高度
	private ArrayList<BmobFile> proFileList; // 其他资源文件
	private ArrayList<String> urlList; // URl
	private BmobRelation commentBR; // 图片关联评论
	// 本地的数据
	private Boolean myLove = false; // 是否点赞
	private String pinId = "";  
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public ArrayList<BmobFile> getProFileList() {
		return proFileList;
	}

	public void setProFileList(ArrayList<BmobFile> proFileList) {
		this.proFileList = proFileList;
	}

	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	public BmobRelation getCommentRelat() {
		return commentBR;
	}

	public void setCommentRelat(BmobRelation commentBR) {
		this.commentBR = commentBR;
	}
	
	public Boolean getMyLove() {
		return myLove;
	}

	public void setMyLove(Boolean myLove) {
		this.myLove = myLove;
	}

	public String getPinId() {
		return pinId;
	}

	public void setPinId(String pinId) {
		this.pinId = pinId;
	}
	
}
