package com.acg12.lib.ui;

import android.content.Context;

import com.acg12.lib.widget.dialog.factory.DialogLoader;


/**
 * Author  SLAN
 * <br>
 * 2018/9/5 20:18
 */
public interface SupportView {
    Context context();

    void setLoadingDialog(DialogLoader dialog);

    void showProgressDialog(String msg);

    void dismissProgressDialog();

    void showMsg(String msg);


}
