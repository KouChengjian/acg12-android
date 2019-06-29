package com.acg12.entity.po;

import com.acg12.entity.DBModel;
import com.litesuits.orm.db.annotation.Table;

/**
 * Created with Android Studio.
 * UserEntity: KCJ
 * Date: 2019-06-24 15:50
 * Description:
 */
@Table("manyou_home")
public class HomeEntity extends DBModel {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 类型名
     */
    private String typeName;

    /**
     * 锁定
     */
    private Integer isLock;

    /**
     * 图片
     */
    private String cover;

    /**
     * 排序
     */
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
