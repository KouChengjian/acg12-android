package org.acg12.entity;

import com.acg12.common.entity.Param;

import java.util.ArrayList;

public class Palette extends Param {
	

	private String boardId = ""; // 花瓣网画板id
	private String name; // 画板名
	private Integer num = 0; // 图片数量
	private String sign; // 图片签名

	private ArrayList<String> urlAlbum = new ArrayList<String>();
	
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

	public ArrayList<String> getUrlAlbum() {
		return urlAlbum;
	}

	public void setUrlAlbum(ArrayList<String> urlAlbum) {
		this.urlAlbum = urlAlbum;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	
}
