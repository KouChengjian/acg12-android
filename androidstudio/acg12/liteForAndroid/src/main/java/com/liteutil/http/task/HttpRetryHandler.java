package com.liteutil.http.task;


import org.json.JSONException;

import com.liteutil.exception.HttpException;
import com.liteutil.http.HttpMethod;
import com.liteutil.http.listener.Callback;
import com.liteutil.http.request.UriRequest;
import com.liteutil.util.Log;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashSet;

/**
 * Author: wyouflf
 * Time: 2014/05/30
 */
public final class HttpRetryHandler {

    protected int maxRetryCount = 2;

    private static HashSet<Class<?>> blackList = new HashSet<Class<?>>();

    static {
        blackList.add(HttpException.class);
        blackList.add(Callback.CancelledException.class);
        blackList.add(MalformedURLException.class);
        blackList.add(URISyntaxException.class);
        blackList.add(NoRouteToHostException.class);
        blackList.add(PortUnreachableException.class);
        blackList.add(ProtocolException.class);
        blackList.add(NullPointerException.class);
        blackList.add(FileNotFoundException.class);
        blackList.add(JSONException.class);
        blackList.add(UnknownHostException.class);
        blackList.add(IllegalArgumentException.class);
    }

    public HttpRetryHandler() {
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public boolean retryRequest(Throwable ex, int count, UriRequest request) {

    	Log.w(ex.getMessage(), ex);

        if (count > maxRetryCount || request == null) {
        	Log.w("The Max Retry times has been reached!");
            return false;
        }

        if (!HttpMethod.permitsRetry(request.getParams().getMethod())) {
            Log.w("The Request Method can not be retried.");
            return false;
        }

        if (blackList.contains(ex.getClass())) {
            Log.w("The Exception can not be retried.");
            return false;
        }

        return true;
    }
}
