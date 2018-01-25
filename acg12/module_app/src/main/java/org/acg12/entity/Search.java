package org.acg12.entity;

import com.acg12.common.entity.Param;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Search extends Param {

    private int searchId;
    private String source;
    private String title;

    public int getSearchId() {
        return searchId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
