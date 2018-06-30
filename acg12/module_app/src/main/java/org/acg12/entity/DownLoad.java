package org.acg12.entity;

import org.acg12.net.download.DLStatus;

import com.litesuits.orm.db.annotation.Table;

/**
 * Created by Administrator on 2017/5/24.
 */
@Table("downLoad")
public class DownLoad extends Param {

    private String url;
    private String path;
    private String name;
    private int currentLength;
    private int totalLength;
    private float percentage;
    private int state = DLStatus.NONE;
    private int childTaskCount;
    private long date;
    private String lastModify;

    public DownLoad() {

    }

    public DownLoad(String url, String path, String name) {
        this.url = url;
        this.path = path;
        this.name = name;
    }

    public DownLoad(String url, String path, String name, int childTaskCount) {
        this.url = url;
        this.path = path;
        this.name = name;
        this.childTaskCount = childTaskCount;
    }

    public DownLoad(String url, String path, int childTaskCount, String name, int currentLength, int totalLength, String lastModify, long date) {
        this.url = url;
        this.path = path;
        this.childTaskCount = childTaskCount;
        this.currentLength = currentLength;
        this.name = name;
        this.totalLength = totalLength;
        this.lastModify = lastModify;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getChildTaskCount() {
        return childTaskCount;
    }

    public void setChildTaskCount(int childTaskCount) {
        this.childTaskCount = childTaskCount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getLastModify() {
        return lastModify;
    }

    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }
}
