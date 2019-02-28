package com.acg12.entity;

import com.litesuits.orm.db.annotation.Column;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/28 17:35
 * Description:
 */
public class CollectSubjectEntity {

    /** id */
    private long id;

    /** 用户id */
    private long userId;

    /** 关联id */
    private int relevanceId;

    /** 图片 */
    private String image;

    /** 名称 */
    private String name;

    /** 中文名称 */
    private String nameCn;

    /** 0:subject 1:crt 2:preson */
    private int type;

    /** 类型名称 */
    private String typeName;

    /** 是否收藏 */
    private int isCollect;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(int relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }
}
