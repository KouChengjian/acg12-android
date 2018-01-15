package com.acg12.common.utils.skin.attr;

import android.content.res.ColorStateList;
import android.support.design.widget.NavigationView;
import android.view.View;

import com.acg12.common.utils.skin.SkinManager;
import com.acg12.common.utils.skin.entity.SkinAttr;


/**
 * Created by DELL on 2017/1/11.
 */
public class NavigationViewAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof NavigationView) {
            NavigationView tl = (NavigationView) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                int color = SkinManager.getInstance().getColor(attrValueRefId);
                tl.setItemTextColor(createColorStateList(0xff272e3e, color, color, color));
                tl.setItemIconTintList(createColorStateList(0xff272e3e, color, color, color));

                View headerView = tl.getHeaderView(0);
                headerView.setBackgroundColor(color);
            }
        }
    }

    /** 对TextView设置不同状态时其文字颜色。 */
    private ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
//        int[][] states = new int[6][];
//        states[0] = new int[] { android.R.attr.state_pressed , android.R.attr.state_enabled };
//        states[1] = new int[] { android.R.attr.state_enabled , android.R.attr.state_focused };
//        states[2] = new int[] { android.R.attr.state_enabled };
//        states[3] = new int[] { android.R.attr.state_focused };
//        states[4] = new int[] { android.R.attr.state_window_focused };
//        states[5] = new int[] { android.R.attr.state_checked};
//
//        int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_checked},
                new int[] { android.R.attr.state_checked}
        };

        int[] colors = new int[] {
                normal,
                pressed,
        };

        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
}
