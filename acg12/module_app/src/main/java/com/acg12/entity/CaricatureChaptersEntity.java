package com.acg12.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: KCJ
 * Date: 2018/12/28 20:20
 * Description:
 */
public class CaricatureChaptersEntity {

    private int comicId;
    private String title;
    private int index;// 第几集
    private int pagerSize;
    private List<CaricatureChaptersPageEntity> pags = new ArrayList<>();

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<CaricatureChaptersPageEntity> getPags() {
        return pags;
    }

    public void setPags(List<CaricatureChaptersPageEntity> pags) {
        this.pags = pags;
    }

    public int getPagerSize() {
        return pagerSize;
    }

    public void setPagerSize(int pagerSize) {
        this.pagerSize = pagerSize;
    }
}
