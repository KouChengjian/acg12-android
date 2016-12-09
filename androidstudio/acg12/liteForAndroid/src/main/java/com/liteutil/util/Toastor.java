package com.liteutil.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * @ClassName: Toastor
 * @Description: 吐司
 * @author: KCJ
 * @date:
 */
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
}
