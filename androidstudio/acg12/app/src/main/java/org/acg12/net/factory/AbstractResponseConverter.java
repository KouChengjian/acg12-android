package org.acg12.net.factory;

import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by DELL on 2016/12/6.
 */
public abstract class AbstractResponseConverter<T> implements Converter<ResponseBody, T> {

    protected Gson gson;

    public AbstractResponseConverter(Gson gson) {
        this.gson = gson;
    }
}
