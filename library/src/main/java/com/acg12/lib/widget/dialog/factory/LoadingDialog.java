package com.acg12.lib.widget.dialog.factory;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by SLAN on 2016/7/8.
 * <p/>
 */
public class LoadingDialog extends DialogLoader {

    private Dialog mDialog;

    private static LoadingDialog instance;

    private LoadingDialog() {
    }

    public static LoadingDialog get() {
        if (null == instance) {
            synchronized (LoadingDialog.class) {
                if (null == instance) {
                    instance = new LoadingDialog();
                }
            }
        }
        return instance;
    }

    @Override
    public void dismissProgressDialog(Context context) {
        dismiss();
    }

    public void showProgressDialog(Context context, String message) {
        releaseDialog();
        mDialog = createDialogLoader(context, message).showDialog();
    }

    private void releaseDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
