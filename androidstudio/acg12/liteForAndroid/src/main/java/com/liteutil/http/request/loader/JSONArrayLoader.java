package com.liteutil.http.request.loader;

import java.io.InputStream;

import org.json.JSONArray;

import android.text.TextUtils;

import com.liteutil.bean.DiskCacheEntity;
import com.liteutil.http.request.RequestParams;
import com.liteutil.http.request.UriRequest;
import com.liteutil.util.IOUtil;

/**
 * Author: wyouflf
 * Time: 2014/06/16
 */
/*package*/ class JSONArrayLoader extends Loader<JSONArray> {

    private String charset = "UTF-8";
    private String resultStr = null;

    @Override
    public Loader<JSONArray> newInstance() {
        return new JSONArrayLoader();
    }

    @Override
    public void setParams(final RequestParams params) {
        if (params != null) {
            String charset = params.getCharset();
            if (!TextUtils.isEmpty(charset)) {
                this.charset = charset;
            }
        }
    }

    @Override
    public JSONArray load(final InputStream in) throws Throwable {
        resultStr = IOUtil.readStr(in, charset);
        return new JSONArray(resultStr);
    }

    @Override
    public JSONArray load(final UriRequest request) throws Throwable {
        request.sendRequest();
        return this.load(request.getInputStream());
    }

    @Override
    public JSONArray loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable {
        if (cacheEntity != null) {
            String text = cacheEntity.getTextContent();
            if (!TextUtils.isEmpty(text)) {
                return new JSONArray(text);
            }
        }

        return null;
    }

    @Override
    public void save2Cache(UriRequest request) {
        saveStringCache(request, resultStr);
    }
}
