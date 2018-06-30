package org.acg12.entity;

/**
 * Created by Administrator on 2018/4/4.
 */
public class SubjectDetail extends Param{

    private Integer subjectDetailId;
    private Integer subjectId;
    private Integer sId;
    private String otherTitle;
    private String otherValue;

    public Integer getSubjectDetailId() {
        return subjectDetailId;
    }

    public void setSubjectDetailId(Integer subjectDetailId) {
        this.subjectDetailId = subjectDetailId;
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

    public String getOtherTitle() {
        return otherTitle;
    }

    public void setOtherTitle(String otherTitle) {
        this.otherTitle = otherTitle;
    }

    public String getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(String otherValue) {
        this.otherValue = otherValue;
    }

}
