package com.acg12.entity;

/**
 * Created by Administrator on 2018/5/14.
 */
public class SubjectOffprint extends Param{

    private Integer subjectOffprintId;
    private Integer subjectId;
    private Integer sId;
    private String image;
    private String name;

    public Integer getSubjectOffprintId() {
        return subjectOffprintId;
    }

    public void setSubjectOffprintId(Integer subjectOffprintId) {
        this.subjectOffprintId = subjectOffprintId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
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
}
