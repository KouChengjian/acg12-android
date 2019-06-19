package com.acg12.entity;

/**
 * Created by Administrator on 2017/5/22.
 */
public class Update extends Param {

    private String versionCode;
    private String versionName;
    private String message;
    private int dialogStatus ; // 0:已是最新版本 1：普通更新 2：强制更新 3:忽略更新
    private String oldVersionCode;
    private String oldVersionName;
    private boolean isIgnore = false;

    public int getDialogStatus() {
        return dialogStatus;
    }

    public void setDialogStatus(int dialogStatus) {
        this.dialogStatus = dialogStatus;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIgnore() {
        return isIgnore;
    }

    public void setIgnore(boolean ignore) {
        isIgnore = ignore;
    }

    public String getOldVersionCode() {
        return oldVersionCode;
    }

    public void setOldVersionCode(String oldVersionCode) {
        this.oldVersionCode = oldVersionCode;
    }

    public String getOldVersionName() {
        return oldVersionName;
    }

    public void setOldVersionName(String oldVersionName) {
        this.oldVersionName = oldVersionName;
    }
}
