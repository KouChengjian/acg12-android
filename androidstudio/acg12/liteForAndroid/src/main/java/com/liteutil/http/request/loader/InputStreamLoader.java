package com.liteutil.http.request.loader;

import com.liteutil.bean.DiskCacheEntity;
import com.liteutil.http.request.UriRequest;

import java.io.InputStream;

/**
 * Created by wyouflf on 15/11/4.
 */
public class InputStreamLoader extends Loader<InputStream> {
    @Override
    public Loader<InputStream> newInstance() {
        return new InputStreamLoader();
    }

    @Override
    public InputStream load(InputStream in) throws Throwable {
        return in;
    }

    @Override
    public InputStream load(UriRequest request) throws Throwable {
        request.sendRequest();
        return request.getInputStream();
    }

    @Override
    public InputStream loadFromCache(DiskCacheEntity cacheEntity) throws Throwable {
        return null;
    }

    @Override
    public void save2Cache(UriRequest request) {

    }
}
