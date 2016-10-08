package com.kcj.animationfriend.bean;

import java.io.Serializable;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * @ClassName: User
 * @Description: 用户信息
 * @author kcj
 * @date
 */
public class User extends BmobChatUser implements Serializable{

	private static final long serialVersionUID = 1078970857241756601L;
	private String signature; // 签名
	private String sex; // 性别
	private BmobRelation relationBR; // 关注 画板(Palette)
	private BmobRelation favourBR;   // 点赞只有在资源显示中读取所点赞的

	/**
	 * 显示数据拼音的首字母
	 */
	private String sortLetters;
	/**
	 * 地理坐标
	 */
	private BmobGeoPoint location;//
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public BmobRelation getRelationBR() {
		return relationBR;
	}

	public void setRelationBR(BmobRelation relationBR) {
		this.relationBR = relationBR;
	}

	public BmobRelation getFavourBR() {
		return favourBR;
	}

	public void setFavourBR(BmobRelation favourBR) {
		this.favourBR = favourBR;
	}

	public String getSortLetters() {
		return sortLetters;
	}
	
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public BmobGeoPoint getLocation() {
		return location;
	}

	public void setLocation(BmobGeoPoint location) {
		this.location = location;
	}

}
