package com.acg12.lib.widget.recycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/22 14:32
 * Description:
 */
public class RecycleViewLayout extends FrameLayout {

    private Context context;

    public RecycleViewLayout(@NonNull Context context) {
        this(context,null);
    }

    public RecycleViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    private void initViews(){

    }
}
