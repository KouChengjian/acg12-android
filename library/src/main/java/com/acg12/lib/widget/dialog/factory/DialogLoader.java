package com.acg12.lib.widget.dialog.factory;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.acg12.lib.R;
import com.acg12.lib.widget.dialog.CommonDialog;
import com.acg12.lib.widget.dialog.base.DialogLoaderInterface;


public abstract class DialogLoader implements DialogLoaderInterface<CommonDialog> {
    @Override
    public CommonDialog createDialogLoader(Context context, String message) {
        return new CommonDialog.Builder(context)
                .setView(R.layout.include_dialog_loading)
                .create()
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .setText(R.id.tv_content, message)
                .setVisibility(R.id.tv_content, TextUtils.isEmpty(message) ? View.GONE : View.VISIBLE);
    }
}
