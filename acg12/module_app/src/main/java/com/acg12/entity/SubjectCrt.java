package com.acg12.entity;


/**
 * Created by Administrator on 2018/4/11.
 */
public class SubjectCrt extends Param{

    private Integer subjectCrtId;
    private Integer subjectId;
    private Integer sId;
    private Integer cId ;
    private Integer pId ;
    private String name;
    private String nameCn;
    private String roleName;
    private String image;
    private String pName;
    private String pNameCn;
    private String pImage;

    public Integer getSubjectCrtId() {
        return subjectCrtId;
    }

    public void setSubjectCrtId(Integer subjectCrtId) {
        this.subjectCrtId = subjectCrtId;
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

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpNameCn() {
        return pNameCn;
    }

    public void setpNameCn(String pNameCn) {
        this.pNameCn = pNameCn;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    @Override
    public boolean equals(Object obj) {
        if(toString().hashCode() == obj.toString().hashCode()){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SubjectCrt{" +
                "subjectId=" + subjectId +
                ", sId=" + sId +
                ", cId=" + cId +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", nameCn='" + nameCn + '\'' +
                ", roleName='" + roleName + '\'' +
                ", image='" + image + '\'' +
                ", pName='" + pName + '\'' +
                ", pNameCn='" + pNameCn + '\'' +
                ", pImage='" + pImage + '\'' +
                '}';
    }
}
