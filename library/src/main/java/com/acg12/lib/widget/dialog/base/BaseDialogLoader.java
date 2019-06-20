package com.acg12.lib.widget.dialog.base;

import android.content.Context;

import com.acg12.lib.R;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019-06-20 16:46
 * Description:
 */
public abstract class BaseDialogLoader implements DialogLoader<BaseDialog>  {

    protected BaseDialog mDialog;

    @Override
    public BaseDialog createDialogLoader(Context context, String message) {
        mDialog = new BaseDialog.Builder(context)
                .setView(R.layout.include_dialog_common)
                .create()
                .cancelable(false)
                .canceledOnTouchOutside(false);
        return mDialog;
    }

    @Override
    public void dismissDialog(Context context) {
        dismiss();
    }

    @Override
    public void showDialog(Context context, String message) {
        releaseDialog();
        mDialog = createDialogLoader(context, message).showDialog();
    }

    public void showDialog() {
        releaseDialog();
        mDialog.showDialog();
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

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    protected abstract int getViewId();

}
