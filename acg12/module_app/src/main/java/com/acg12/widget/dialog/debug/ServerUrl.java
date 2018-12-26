package com.acg12.widget.dialog.debug;

public class ServerUrl implements Cloneable {

    public int imAppid;
    public String videoId;
    public String baseURL;

    public ServerUrl(int imAppid, String videoId, String baseUrl) {
        this.imAppid = imAppid;
        this.videoId = videoId;
        this.baseURL = baseUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ServerUrl) {
            ServerUrl ss = (ServerUrl) obj;
            if (baseURL == null && ss.baseURL != null) {
                return false;
            }
            return baseURL.equals(ss.baseURL);
        }
        return false;
    }
}