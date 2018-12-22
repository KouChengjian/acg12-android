package com.acg12.lib.net.factory;


import com.acg12.lib.BuildConfig;

/**
 * Created by DELL on 2016/12/6.
 */
public class ApiErrorCode {

    public static final int HTTP_RESPONSE_SUCCEED = 0;
    public static final int HTTP_RESPONSE_CONVERTER_DATA_NULL = 20100;

    // 异常捕获
    public static final int EXCEPTION_IO = 1024;
    public static final int EXCEPTION_JSON = 1025;

    public static String getErrorCodeMsg(int code){
        if(BuildConfig.DEBUG){
            return debugMsg(code);
        }else{
            return userMsg(code);
        }
    }


    public static String debugMsg(int code){
        if(code == HTTP_RESPONSE_SUCCEED){
            return "请求成功";
        } else if(code == HTTP_RESPONSE_CONVERTER_DATA_NULL){
            return "Converter 数据为空";
        }


        return "未知错误";
    }

    public static String userMsg(int code){
        if(code == HTTP_RESPONSE_CONVERTER_DATA_NULL){
            return "数据加载异常，请重新加载";
        }

        if(code == EXCEPTION_IO){
            return "数据加载异常，请重新加载";
        } else if(code == EXCEPTION_JSON){
            return "数据加载异常，请重新加载";
        }

        return "网络异常";
    }

}
