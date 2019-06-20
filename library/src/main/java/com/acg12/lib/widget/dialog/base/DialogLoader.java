package com.acg12.lib.widget.dialog.base;

import android.app.Dialog;
import android.content.Context;

import java.io.Serializable;

public interface DialogLoader<T extends Dialog> extends Serializable {

    void showDialog(Context context, String msg);

    void dismissDialog(Context context);

    T createDialogLoader(Context context, String message);
}