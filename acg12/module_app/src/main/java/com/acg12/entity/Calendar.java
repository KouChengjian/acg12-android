package com.acg12.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/6/29
 * Time: 18:30
 * Description:
 */
public class Calendar implements Serializable{

    private int id;
    private String en; //Mon
    private String cn; //星期一
    private String ja; // 月耀日
    private int sId;
    private int type;
    private String name;
    private String name_cn;
    private String summary;
    private String air_date;
    private int air_weekday;
    private String image;
    private List<Calendar> calendarList = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getJa() {
        return ja;
    }

    public void setJa(String ja) {
        this.ja = ja;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_cn() {
        return name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getAir_weekday() {
        return air_weekday;
    }

    public void setAir_weekday(int air_weekday) {
        this.air_weekday = air_weekday;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Calendar> getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(List<Calendar> calendarList) {
        this.calendarList = calendarList;
    }



}
