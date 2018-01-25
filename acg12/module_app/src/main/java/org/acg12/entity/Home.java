package org.acg12.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/25.
 */

public class Home {

    private String cover;
    private List<Tags> tagsList = new ArrayList<>();

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }
}
