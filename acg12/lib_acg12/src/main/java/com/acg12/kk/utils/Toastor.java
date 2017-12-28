package com.acg12.kk.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acg12.kk.R;


public class Toastor {

	private static Toast mToast;
	private static Context context ;

	public Toastor(Context context) {
		this.context = context.getApplicationContext();
	}

	public static void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}

	public static void ShowToast(final int resId) {
		if (mToast == null) {
			mToast = Toast.makeText(context.getApplicationContext(), resId,
					Toast.LENGTH_LONG);
		} else {
			mToast.setText(resId);
		}
		mToast.show();
	}
	
	public Toast getSingletonToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(resId);
        }
        return mToast;
    }

    public Toast getSingletonToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
        }
        return mToast;
    }

    public Toast getToast(int resId) {
        return Toast.makeText(context, resId, Toast.LENGTH_SHORT);
    }

    public Toast getToast(String text) {
        return Toast.makeText(context, text, Toast.LENGTH_SHORT);
    }

    public Toast getLongToast(int resId) {
        return Toast.makeText(context, resId, Toast.LENGTH_LONG);
    }

    public Toast getLongToast(String text) {
        return Toast.makeText(context, text, Toast.LENGTH_LONG);
    }


    public void showSingletonToast(int resId) {
        getSingletonToast(resId).show();
    }


    public void showSingletonToast(String text) {
        getSingletonToast(text).show();
    }

    public void showToast(int resId) {
        getToast(resId).show();
    }

    public void showToast(String text) {
        getToast(text).show();
    }

    public void showLongToast(int resId) {
        getLongToast(resId).show();
    }

    public void showLongToast(String text) {
        getLongToast(text).show();
    }


    public static void ShowToastView(int resId) {
        ShowToastView(context.getResources().getString(resId));
    }

    public static void ShowToastView(String s) {
        View layoutView =  LayoutInflater.from(context).inflate(R.layout.kk_common_toast, null);
        //设置文本的参数 设置加载文本文件的参数，必须通过LayoutView获取。
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //layoutView.setLayoutParams(params);
        TextView textView = (TextView)layoutView.findViewById(R.id.toast_content);
        textView.setText(s);

        //创建toast对象，
        Toast toast = new Toast(context);
        //把要Toast的布局文件放到toast的对象中
        toast.setView(layoutView);
        toast.setDuration(toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
