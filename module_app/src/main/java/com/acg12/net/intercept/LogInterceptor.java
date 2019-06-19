package com.acg12.net.intercept;

import com.acg12.lib.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by zhangdeming on 16/4/25.
 */
public class LogInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Inject
    public LogInterceptor() {}

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        RequestBody requestBody = request.body();
        ResponseBody responseBody = response.body();
        String responseBodyString = responseBody.string();
        String requestMessage;
        requestMessage = request.method() + ' ' + request.url() + "?token=" + request.header("token");

        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            requestMessage += "  " + buffer.readString(UTF8);
        }
        LogUtil.e("请求信息 " + requestMessage);
        LogUtil.e("响应信息 " + responseBodyString);
        return response.newBuilder().body(ResponseBody.create(responseBody.contentType(),
                responseBodyString.getBytes())).build();
    }

}
