package com.liteutil.http.request.cookie;

import android.text.TextUtils;


import java.net.HttpCookie;
import java.net.URI;

/**
 * Created by wyouflf on 15/8/20.
 * 数据库中的cookie实体
 */
/*package*/ final class CookieEntity {

    // ~ 100 year
    private static final long MAX_EXPIRY = System.currentTimeMillis() + 1000L * 60L * 60L * 24L * 30L * 12L * 100L;

    private long id;

    private String uri; // cookie add by this uri.

    private String name;
    private String value;
    private String comment;
    private String commentURL;
    private boolean discard;
    private String domain;
    private long expiry = MAX_EXPIRY;
    private String path;
    private String portList;
    private boolean secure;
    private int version = 1;

    public CookieEntity() {
    }

    public CookieEntity(URI uri, HttpCookie cookie) {
        this.uri = uri == null ? null : uri.toString();
        this.name = cookie.getName();
        this.value = cookie.getValue();
        this.comment = cookie.getComment();
        this.commentURL = cookie.getCommentURL();
        this.discard = cookie.getDiscard();
        this.domain = cookie.getDomain();
        long maxAge = cookie.getMaxAge();
        if (maxAge != -1L) {
            this.expiry = (maxAge * 1000L) + System.currentTimeMillis();
            if (this.expiry < 0L) {
                this.expiry = MAX_EXPIRY;
            }
        }
        this.path = cookie.getPath();
        if (!TextUtils.isEmpty(path) && path.length() > 1 && path.endsWith("/")) {
            this.path = path.substring(0, path.length() - 1);
        }
        this.portList = cookie.getPortlist();
        this.secure = cookie.getSecure();
        this.version = cookie.getVersion();
    }

    public HttpCookie toHttpCookie() {
        HttpCookie cookie = new HttpCookie(name, value);
        cookie.setComment(comment);
        cookie.setCommentURL(commentURL);
        cookie.setDiscard(discard);
        cookie.setDomain(domain);
        cookie.setMaxAge((expiry - System.currentTimeMillis()) / 1000L);
        cookie.setPath(path);
        cookie.setPortlist(portList);
        cookie.setSecure(secure);
        cookie.setVersion(version);
        return cookie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
