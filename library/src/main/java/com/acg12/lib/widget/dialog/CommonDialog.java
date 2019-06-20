package com.acg12.lib.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.acg12.lib.R;
import com.acg12.lib.widget.dialog.base.BaseDialogLoader;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019-06-20 14:59
 * Description:
 */
public class CommonDialog extends BaseDialogLoader {

    private Callback callback;

    @Override
    protected int getViewId() {
        return R.layout.include_dialog_common;
    }

    public CommonDialog(Context context, String title, String message, boolean cancelable) {
        mDialog = createDialogLoader(context, message)
                .cancelable(cancelable)
                .canceledOnTouchOutside(cancelable)
                .setText(R.id.tv_title, title)
                .setVisibility(R.id.tv_title, TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE)
                .setText(R.id.tv_content, message)
                .setOnClickListener(R.id.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            callback.cancle();
                        }
                    }
                })
                .setOnClickListener(R.id.commit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            callback.commit();
                        }
                    }
                });
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void commit();

        void cancle();
    }
}
