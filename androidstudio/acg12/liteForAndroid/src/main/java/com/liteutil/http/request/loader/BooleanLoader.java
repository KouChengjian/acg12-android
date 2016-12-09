package com.liteutil.http.request.loader;

import com.liteutil.bean.DiskCacheEntity;
import com.liteutil.http.request.UriRequest;

import java.io.InputStream;

/**
 * Author: wyouflf
 * Time: 2014/05/30
 */
public class BooleanLoader extends Loader<Boolean> {

    @Override
    public Loader<Boolean> newInstance() {
        return new BooleanLoader();
    }

    @Override
    public Boolean load(final InputStream in) throws Throwable {
        return false;
    }

    @Override
    public Boolean load(final UriRequest request) throws Throwable {
        request.sendRequest();
        return request.getResponseCode() < 300;
    }

    @Override
    public Boolean loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable {
        return null;
    }

    @Override
    public void save2Cache(final UriRequest request) {

    }
}
