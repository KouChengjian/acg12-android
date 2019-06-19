package com.acg12.entity;

/**
 * Created by Administrator on 2018/1/25.
 */

public class Tags extends Param{
    /** id */
    private Long id;

    /** 名称 */
    private String name;

    /** 封面 */
    private String cover;

    /** 类型 */
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
