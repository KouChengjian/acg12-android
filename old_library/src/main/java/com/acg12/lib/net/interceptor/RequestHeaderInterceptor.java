package com.acg12.lib.net.interceptor;

import com.acg12.lib.cons.Constant;
import com.acg12.lib.utils.PreferencesUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

public class RequestHeaderInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl originalHttpUrl = request.url();
        HttpUrl.Builder htbuilder = originalHttpUrl.newBuilder();
        HttpUrl url = htbuilder.build();
        Request.Builder builder = request.newBuilder().url(url);
        String session = PreferencesUtils.getString(Constant.SESSION);
        StringBuffer headStr = new StringBuffer();
        builder.addHeader("sessionId", session);
//        if (Hawk.contains(HawkContants.SESSION)) {
//            String sessionId = Hawk.get(HawkContants.SESSION);
//            builder.addHeader("sessionId", sessionId);
//            if (headStr.length() == 0) {
//                headStr.append("sessionId=" + sessionId);
//            } else {
//                headStr.append("&");
//                headStr.append("sessionId=" + sessionId);
//            }
//        }
//        builder.addHeader("key", Http.key);
//        builder.addHeader("time", Http.time);
//        builder.addHeader("language", Http.language);

        RequestBody requestBody = request.body();
        //打印请求信息
        String requestMessage;
        requestMessage = request.method() + ' ' + request.url();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            requestMessage += "?" + headStr + ' ' + buffer.readString(UTF8);
        }
//        LogUtil.e("请求信息", requestMessage);

        return chain.proceed(builder.build());
    }
}