package com.acg12.lib.utils.glide;

import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.acg12.lib.cons.Constant;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Fetches an {@link InputStream} using the okhttp library.
 */
public class OkHttpStreamFetcher implements DataFetcher<InputStream>, okhttp3.Callback {

    private static final String TAG = "OkHttpFetcher";
    private final Call.Factory client;
    private final GlideUrl url;
    private volatile Call call;
    private DataCallback<? super InputStream> callback;

    public OkHttpStreamFetcher(Call.Factory client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public void loadData(Priority priority, final DataCallback<? super InputStream> callback) {
        Request.Builder requestBuilder = new Request.Builder().url(url.toStringUrl());
        for (Map.Entry<String, String> headerEntry : url.getHeaders().entrySet()) {
            String key = headerEntry.getKey();
//            if ("referer".equals(key.toLowerCase())) {
//                continue;
//            }
            requestBuilder.addHeader(key, headerEntry.getValue());
        }
//        requestBuilder.addHeader("referer", Constant.DOWNLOAD_IMG_REFERER);
        Request request = requestBuilder.build();
        this.callback = callback;

        this.call = client.newCall(request);
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            this.call.enqueue(this);
        } else {
            try {
                // Calling execute instead of enqueue is a workaround for #2355, where okhttp throws a
                // ClassCastException on O.
                onResponse(this.call, this.call.execute());
            } catch (IOException e) {
                onFailure(this.call, e);
            } catch (ClassCastException e) {
                // It's not clear that this catch is necessary, the error may only occur even on O if
                // enqueue is used.
                onFailure(this.call, new IOException("Workaround for framework bug on O", e));
            }
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "OkHttp failed to obtain result", e);
        }
        this.callback.onLoadFailed(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            InputStream stream = ContentLengthInputStream.obtain(response.body().byteStream(), response.body().contentLength());
            this.callback.onDataReady(stream);
        } else {
            //LogUtil.e("glide OkHttpStreamFetcher =============================>" + response.message() + "code:" + response.code() + " ==>" + call.request().toString());
            this.callback.onLoadFailed(new HttpException(response.message(), response.code()));
        }
    }

    @Override
    public void cleanup() {
        this.callback = null;
    }

    @Override
    public void cancel() {
        Call local = this.call;
        if (local != null) {
            local.cancel();
        }
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }

}

