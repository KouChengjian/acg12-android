package com.acg12.lib.utils.skin.entity;

/**
 * Created by DELL on 2016/12/29.
 */
public class Skin {

    private int color;
    private String name;
    private String path;
    private boolean hasSelector;

    public Skin(){}

    public Skin(int color , String name ,String path){
        this.color = color;
        this.name = name;
        this.path = path;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHasSelector() {
        return hasSelector;
    }

    public void setHasSelector(boolean hasSelector) {
        this.hasSelector = hasSelector;
    }
}
