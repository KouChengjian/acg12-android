package com.acg12.widget.debug;

public class ServerUrl implements Cloneable {

    public String baseURL;

    public ServerUrl(String baseUrl) {
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