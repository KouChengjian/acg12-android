package com.acg12.lib.utils.glide;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;

import java.net.URL;

public class GlideUrlNoParams extends GlideUrl {
    public GlideUrlNoParams(URL url) {
        super(url);
    }

    public GlideUrlNoParams(String url) {
        super(url);
    }

    public GlideUrlNoParams(URL url, Headers headers) {
        super(url, headers);
    }

    public GlideUrlNoParams(String url, Headers headers) {
        super(url, headers);
    }

    @Override
    public String getCacheKey() {
        String url = toStringUrl();
        if (url.contains("?")) {
            return url.substring(0, url.lastIndexOf("?"));
        }
        return url;
    }
}
