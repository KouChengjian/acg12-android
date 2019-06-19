package com.acg12.entity;

import com.litesuits.orm.db.annotation.Column;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/3/2 16:38
 * Description:
 */
public class CollectPaletteEntity  {

    private long id;

    private String boardId;

    private String title;

    private String sign;

    private int num;

    private String cover;

    private String thumImage1;

    private String thumImage2;

    private String thumImage3;

    /** 是否收藏 */
    private int isCollect;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getThumImage1() {
        return thumImage1;
    }

    public void setThumImage1(String thumImage1) {
        this.thumImage1 = thumImage1;
    }

    public String getThumImage2() {
        return thumImage2;
    }

    public void setThumImage2(String thumImage2) {
        this.thumImage2 = thumImage2;
    }

    public String getThumImage3() {
        return thumImage3;
    }

    public void setThumImage3(String thumImage3) {
        this.thumImage3 = thumImage3;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }
}
