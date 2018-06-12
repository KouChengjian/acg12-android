package com.acg12.lib.utils;

import com.acg12.lib.conf.BaseConstant;
import com.acg12.lib.net.factory.ApiErrorCode;
import com.acg12.lib.net.factory.ApiException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParse {

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
                return null;
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
            if(BaseConstant.debug){
                throw new ApiException(ApiErrorCode.EXCEPTION_JSON , e.toString());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_JSON , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_JSON));
            }
        }
    }

    public static JSONArray getJSONArray(String response) {
        try{
            JSONArray json = new JSONArray(response);
            return json;
        }catch (Exception e){
            if(BaseConstant.debug){
                throw new ApiException(ApiErrorCode.EXCEPTION_JSON , e.toString());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_JSON , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_JSON));
            }
        }
    }
}
