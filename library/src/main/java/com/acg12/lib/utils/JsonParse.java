package com.acg12.lib.utils;

import com.acg12.lib.net.factory.ApiErrorCode;
import com.acg12.lib.net.factory.ApiException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParse {

    private static Gson gson = new Gson();

    /**
     * @param content
     * @param clazz
     * @param <T>     List ： (Class<List<ShangyiGroupEntity>>) new TypeToken<List<ShangyiGroupEntity>>(){}.getType()
     * @return
     */
    public static <T> T fromJson(String content, Class<T> clazz) {
        if (content.isEmpty() || clazz == null) {
            return null;
        }
        try {
            return gson.fromJson(content, clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> fromListJson(String string, Class<T> T) {
        try {
            JsonArray array = new JsonParser().parse(string).getAsJsonArray();
            return fromListJson(array, T);
        } catch (Exception e) {
            return null;
        }

    }

    public static <T> List<T> fromListJson(JsonArray array, Class<T> T) {
        try {
            Gson gson = new Gson();
            List<T> lst = new ArrayList<>();
            for (final JsonElement element : array) {
                lst.add(gson.fromJson(element, T));
            }
            return lst;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getString(JSONObject json , String key){
        try {
            if(!json.isNull(key)){
                return json.getString(key);
            }else
                return "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getString(JSONArray json , int position){
        try {
            return json.getString(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getInt(JSONObject json ,String key){
        try {
            if(!json.isNull(key)){
                return json.getInt(key);
            }else
                return 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Double getDouble(JSONObject json ,String key){
        try {
            if(!json.isNull(key)){
                return json.getDouble(key);
            }else
                return 0.00;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0.00;
    }

    public static JSONArray getJSONArray(JSONObject json ,String key){
        try {
            if(!json.isNull(key)){
                return json.getJSONArray(key);
            }else
                return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONArray json ,int position){
        try {
            return json.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONObject json ,String key){
        JSONObject array = null;
        try {
            array = json.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static JSONObject getJSONObject(String response) {
        try{
            JSONObject json = new JSONObject(response);
            return json;
        }catch (Exception e){
            throw new ApiException(ApiErrorCode.EXCEPTION_JSON , e.toString());
        }
    }

    public static JSONArray getJSONArray(String response) {
        try{
            JSONArray json = new JSONArray(response);
            return json;
        }catch (Exception e){
            throw new ApiException(ApiErrorCode.EXCEPTION_JSON , e.toString());
        }
    }
}
