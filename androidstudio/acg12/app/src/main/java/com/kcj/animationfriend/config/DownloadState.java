package com.kcj.animationfriend.config;

/**
 * Created by wyouflf on 15/11/10.
 */
public enum DownloadState {
    WAITING(0), STARTED(1), FINISHED(2), STOPPED(3), ERROR(4);

    private final int value;

    DownloadState(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static DownloadState valueOf(int value) {
        switch (value) {
            case 0:
                return WAITING; // 等待
            case 1:
                return STARTED; // 开始
            case 2:
                return FINISHED; // 完成
            case 3:
                return STOPPED; // 停止
            case 4:
                return ERROR; // 错误
            default:
                return STOPPED; // 停止
        }
    }
}
