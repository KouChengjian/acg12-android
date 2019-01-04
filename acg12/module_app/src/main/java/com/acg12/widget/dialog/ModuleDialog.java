package com.acg12.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.acg12.R;
import com.acg12.constant.Constant;
import com.acg12.lib.utils.PreferencesUtils;


/**
 * @author Lai
 * @time 2018/1/29 22:44
 * @describe describe
 */

public class ModuleDialog extends Dialog {

    private Context mContext;
    private View mView;
    private RadioButton mCbPull;
    private RadioButton mCbPager;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    private View.OnClickListener mOnClickListener;

    public ModuleDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(mContext).inflate(R.layout.include_dialog_module, null);
        setContentView(mView);
        mCbPull = mView.findViewById(R.id.rb_pull);
        mCbPager = mView.findViewById(R.id.rb_pager);
        init();
    }

    private void init() {
        int module = PreferencesUtils.getInt(mContext, Constant.XML_KEY_CARICATURE_MODE, 0);
        if (module == 0)
            mCbPull.setChecked(true);
        else
            mCbPager.setChecked(true);

        mView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int module = mCbPager.isChecked() ? 1 : 0;
                PreferencesUtils.putInt(mContext, Constant.XML_KEY_CARICATURE_MODE, module);
                if (mOnClickListener != null)
                    mOnClickListener.onClick(v);
                dismiss();
            }
        });
    }

}
