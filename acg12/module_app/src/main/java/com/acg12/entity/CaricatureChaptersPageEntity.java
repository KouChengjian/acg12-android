package com.acg12.entity;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/3 10:50
 * Description:
 */
public class CaricatureChaptersPageEntity {

    private String url;
    private int index = -1;
    private int sort;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
