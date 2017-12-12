package com.acg12.cc.db.entity;

/**
 * Created by Administrator on 2017/5/12.
 */
public class Banner extends Param {

    private static final long serialVersionUID = -264913673841750289L;

    // banner基本数据
    private String title;
    private String imgUrl;
    private String url;
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
