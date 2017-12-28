package com.acg12.kk.net;

import android.util.Log;

import com.acg12.kk.conf.BaseConstant;
import com.acg12.kk.listener.HttpRequestListener;
import com.acg12.kk.net.factory.ApiErrorCode;
import com.acg12.kk.net.factory.ApiException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Administrator on 2017/12/12.
 */
public class BaseRetrofitClient {

    public final static int CONNECT_TIMEOUT = 10;
    public final static int READ_TIMEOUT = 20;
    public final static int WRITE_TIMEOUT = 20;

    public static JSONObject parseJSONObject(ResponseBody response) {
        JSONObject data = null;
        try {
            String result = response.string();
            Log.e("success",result+"");
            JSONObject json = new JSONObject(result);
            int code = json.getInt("result");
            String desc = json.getString("desc");
            if (code == ApiErrorCode.HTTP_RESPONSE_SUCCEED) {
                data = json.getJSONObject("data");
            }else{
                throw new ApiException(Integer.valueOf(code).intValue() , desc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(BaseConstant.debug){
                throw new ApiException(ApiErrorCode.EXCEPTION_IO , e.toString());
            } else {
                throw new ApiException(ApiErrorCode.EXCEPTION_IO , ApiErrorCode.getErrorCodeMsg(ApiErrorCode.EXCEPTION_IO));
            }
        }
        return data;
    }

    public static void failure(){
        throw new ApiException(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL,ApiErrorCode.getErrorCodeMsg(ApiErrorCode.HTTP_RESPONSE_CONVERTER_DATA_NULL));
    }

    public static void failure(final Throwable e , final HttpRequestListener httpRequestListener){
        if(e instanceof ApiException){
            failure(e.getMessage() ,httpRequestListener);
        } else if(e instanceof HttpException){
            failure(e.getMessage() ,httpRequestListener);
        } else if(e instanceof ConnectException){
            failure(e.getMessage() ,httpRequestListener);
        } else {
            failure(e.toString() ,httpRequestListener);
        }
    }

    public static void failure(final String e , final HttpRequestListener httpRequestListener){
        if(httpRequestListener != null){
            httpRequestListener.onFailure(0, e);
        }else{
            failure();
        }
    }

    /** ----------------------静态函数调用-------------------------------- */

    public static RequestBody parseRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public static RequestBody parseImageRequestBody(File file) {
        return RequestBody.create(MediaType.parse("image/png"), file);
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
