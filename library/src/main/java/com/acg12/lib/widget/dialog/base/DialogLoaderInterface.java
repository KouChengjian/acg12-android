package com.acg12.lib.widget.dialog.base;

import android.app.Dialog;
import android.content.Context;

import java.io.Serializable;

public interface DialogLoaderInterface<T extends Dialog> extends Serializable {

    void showProgressDialog(Context context, String msg);

    void dismissProgressDialog(Context context);

    T createDialogLoader(Context context, String message);
}