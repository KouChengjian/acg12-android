package com.kcj.animationfriend.bean;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * @ClassName: Palette
 * @Description: 画板(关联用户和画集)
 * @author: KCJ
 * @date:  
 */
public class Palette extends BmobObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String boardId = ""; // 花瓣网画板id
	private User user; // 用户
	private String name; // 画板名
	private Integer num = 0; // 图片数量
	private String sign; // 图片签名
	private BmobRelation collectBR; // 画板关联用户所有的图片(包括采集的)
	private ArrayList<String> urlAlbum = new ArrayList<String>();
	
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public BmobRelation getCollectBR() {
		return collectBR;
	}

	public void setCollectBR(BmobRelation collectBR) {
		this.collectBR = collectBR;
	}

	public ArrayList<String> getUrlAlbum() {
		return urlAlbum;
	}

	public void setUrlAlbum(ArrayList<String> urlAlbum) {
		this.urlAlbum = urlAlbum;
	}

//	public Boolean getMyAttention() {
//		return myAttention;
//	}
//
//	public void setMyAttention(Boolean myAttention) {
//		this.myAttention = myAttention;
//	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	
}
