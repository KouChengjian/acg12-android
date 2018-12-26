package com.acg12.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
public class Subject extends Param {

    private Integer subjectId;
    private Integer sId;
    private String url;
    private Integer type; // 类型   1、书籍 2、动画 3、音乐 4、游戏 6、三次元
    private String typeName; // 类型名称
    private String name;
    private String nameCn; // 中文
    private String summary; // 概况
    private String image;
    // subject
    private Integer epsCount; // 话数
    private String airDate;// 放送开始 2015-10-10
    private Integer airWeekday;// 放送星期 3
    private String endDate; // 播放结束
    private String author;
    // crt
    private String height;
    private String weight;
    private String alias;
    private Integer gender;
    private Integer bloodtype;
    private String birthday;

    private List<SubjectDetail> detailList = new ArrayList<>();
    private List<SubjectStaff> staffList = new ArrayList<>();
    private List<SubjectCrt> crtList = new ArrayList<>();
    private List<SubjectSong> songList = new ArrayList<>();
    private List<SubjectOffprint> offprintList = new ArrayList<>();

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getEpsCount() {
        return epsCount;
    }

    public void setEpsCount(Integer epsCount) {
        this.epsCount = epsCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Integer getAirWeekday() {
        return airWeekday;
    }

    public void setAirWeekday(Integer airWeekday) {
        this.airWeekday = airWeekday;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(Integer bloodtype) {
        this.bloodtype = bloodtype;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object obj) {
        if (toString().hashCode() == obj.toString().hashCode()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "sId=" + sId +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", typeName=" + typeName +
                ", name='" + name + '\'' +
                ", nameCn='" + nameCn + '\'' +
                ", summary='" + summary + '\'' +
                ", image='" + image + '\'' +
                ", epsCount=" + epsCount +
                ", airDate='" + airDate + '\'' +
                ", airWeekday=" + airWeekday +
                ", endDate='" + endDate + '\'' +
                '}';
    }


    public List<SubjectDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<SubjectDetail> detailList) {
        this.detailList = detailList;
    }

    public List<SubjectStaff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<SubjectStaff> staffList) {
        this.staffList = staffList;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<SubjectCrt> getCrtList() {
        return crtList;
    }

    public void setCrtList(List<SubjectCrt> crtList) {
        this.crtList = crtList;
    }

    public List<SubjectSong> getSongList() {
        return songList;
    }

    public void setSongList(List<SubjectSong> songList) {
        this.songList = songList;
    }

    public List<SubjectOffprint> getOffprintList() {
        return offprintList;
    }

    public void setOffprintList(List<SubjectOffprint> offprintList) {
        this.offprintList = offprintList;
    }

    public String getTypeStatus() {
        // 1、书籍 2、动画 3、音乐 4、游戏 6、三次元
        String tname = "";
        if (type == 1) {
            tname = "书籍";
        } else if (type == 2) {
            tname = "动画";
        } else if (type == 3) {
            tname = "音乐";
        } else if (type == 4) {
            tname = "游戏";
        } else if (type == 6) {
            tname = "三次元";
        }
        return tname;
    }

    public String getTypeEps() {
        // 1、书籍 2、动画 3、音乐 4、游戏 6、三次元
        String tname = "";
        if (type == 1) {
            tname = "册数 " + (epsCount != 0 ? epsCount + "" : "???");
        } else if (type == 2) {
            tname = "集数 " + (epsCount != 0 ? epsCount + "" : "???");
        } else if (type == 3) {
            tname = "曲数 " + (epsCount != 0 ? epsCount + "" : "???");
        } else if (type == 4) {
            tname = "类型 " + (!typeName.isEmpty() ? typeName + "" : "???");
        } else if (type == 6) {
            tname = "???";
        }
        return tname;
    }

    public String getTypeTime() {
        // 1、书籍 2、动画 3、音乐 4、游戏 6、三次元
        String tname = "";
        if (type == 1) {
            tname = "发行时间 " + (airDate.equals("0000-00-00") ? "???":airDate);
        } else if (type == 2) {
            tname = "播放时间 " + (airDate.equals("0000-00-00") ? "???":airDate);
        } else if (type == 3) {
            tname = "发行时间 " + (airDate.equals("0000-00-00") ? "???":airDate);
        } else if (type == 4) {
            tname = "发行时间 " + (airDate.equals("0000-00-00") ? "???":airDate);
        } else if (type == 6) {
            tname = "播放时间 " + (airDate.equals("0000-00-00") ? "???":airDate);
        }
        return tname;
    }

    public String getTypeGender(){
        String sex = "性别：";
        if(gender == 1){
            sex += "男";
        } else if(gender == 2){
            sex += "女";
        } else{
            sex += "???";
        }
        return sex;
    }

    public String getOther() {
        String s1 = "";
        if (getBirthday() != null && !getBirthday().isEmpty()) {
            s1 += "生日 " + getBirthday();
        } else {
            s1 += "生日 ???";
        }
        if (getBloodtype() == 1) {
            s1 += " / 血型 A";
        } else if (getBloodtype() == 2) {
            s1 += " / 血型 B";
        } else if (getBloodtype() == 3) {
            s1 += " / 血型 AB";
        } else if (getBloodtype() == 4) {
            s1 += " / 血型 O";
        }
        if (getHeight() != null && !getHeight().isEmpty()) {
            s1 += " / 身高 " + getHeight();
        }
        if (getWeight() != null && !getWeight().isEmpty()) {
            s1 += " / 体重 " + getWeight();
        }
        return s1;
    }
}
