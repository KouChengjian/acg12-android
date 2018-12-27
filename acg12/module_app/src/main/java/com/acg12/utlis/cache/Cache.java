package com.acg12.utlis.cache;

import android.content.Context;

import com.acg12.lib.utils.ACache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Cache {

    private Context mContext;
    private static Cache instance;
    private ACache mCache;

    public static Cache init(Context mContext) {
        return new Cache(mContext);
    }

    public Cache(Context mContext) {
        this.mContext = mContext;
        instance = this;
        mCache = ACache.get(mContext);
    }

    public static Cache getInstance() {
        return instance;
    }

    public void savaHistoryTags(List<String> list) {
        Gson gson = new Gson();
        mCache.put("history_tags", gson.toJson(list, new TypeToken<List<String>>() {
        }.getType()));
    }

    public List<String> getHistoryTags() {
        List<String> searchList = new ArrayList<>();
        Gson gson = new Gson();
        String banner = mCache.getAsString("history_tags");
        if (banner != null && !banner.isEmpty()) {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            searchList.addAll((List<String>) gson.fromJson(banner, type));
        }
        return searchList;
    }
}
