package com.liteutil.http.request.body;


import com.liteutil.http.task.ProgressHandler;

/**
 * Created by wyouflf on 15/8/13.
 */
public interface ProgressBody extends RequestBody {
    void setProgressHandler(ProgressHandler progressHandler);
}
