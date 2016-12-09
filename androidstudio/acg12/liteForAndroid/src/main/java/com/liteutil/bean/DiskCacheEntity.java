package com.liteutil.bean;


import java.util.Date;

/**
 * Created by wyouflf on 15/8/2.
 * 磁盘缓存对象
 */
public final class DiskCacheEntity {

    private long id;

    private String key;

    private String path;

    private String textContent;

    // from "max-age" (since http 1.1)
    private long expires = Long.MAX_VALUE;

    private String etag;

    private long hits;

    private Date lastModify;

    private long lastAccess;


    public DiskCacheEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /*package*/ String getPath() {
        return path;
    }

    /*package*/ void setPath(String path) {
        this.path = path;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public Date getLastModify() {
        return lastModify;
    }

    public void setLastModify(Date lastModify) {
        this.lastModify = lastModify;
    }

    public long getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(long lastAccess) {
        this.lastAccess = lastAccess;
    }
}
