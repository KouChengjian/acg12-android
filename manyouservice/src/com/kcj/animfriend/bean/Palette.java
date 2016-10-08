package com.kcj.animfriend.bean;

import java.util.ArrayList;

public class Palette {

	private String boardId = ""; // 花瓣网画板id
	private String name; // 画板名
	private Integer num = 0; // 图片数量
	private String sign; // 图片签名
	private ArrayList<String> urlAlbum = new ArrayList<String>();

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
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

	public ArrayList<String> getUrlAlbum() {
		return urlAlbum;
	}

	public void setUrlAlbum(ArrayList<String> urlAlbum) {
		this.urlAlbum = urlAlbum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardId == null) ? 0 : boardId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Palette other = (Palette) obj;
		if (boardId == null) {
			if (other.boardId != null)
				return false;
		} else if (!boardId.equals(other.boardId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Palette [boardId=" + boardId + ", name=" + name + ", num="
				+ num + ", sign=" + sign + ", urlAlbum=" + urlAlbum + "]";
	}
	
	
}
