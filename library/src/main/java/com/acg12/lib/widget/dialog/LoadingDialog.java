package com.acg12.lib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.acg12.lib.R;
import com.acg12.lib.widget.dialog.base.BaseDialog;
import com.acg12.lib.widget.dialog.base.DialogLoader;

/**
 * Created by SLAN on 2016/7/8.
 * <p/>
 */
public class LoadingDialog implements DialogLoader<BaseDialog> {

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
    public BaseDialog createDialogLoader(Context context, String message) {
        return new BaseDialog.Builder(context)
                .setView(R.layout.include_dialog_loading)
                .create()
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .setText(R.id.tv_content, message)
                .setVisibility(R.id.tv_content, TextUtils.isEmpty(message) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void dismissDialog(Context context) {
        dismiss();
    }

    public void showDialog(Context context, String message) {
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
