package com.acg12.lib.utils.skin.attr;

import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.acg12.lib.utils.skin.SkinManager;
import com.acg12.lib.utils.skin.entity.SkinAttr;


/**
 * Created by DELL on 2017/1/9.
 */
public class TabLayoutIndicatorAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof TabLayout) {
            TabLayout tl = (TabLayout) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                int color = SkinManager.getInstance().getColor(attrValueRefId);
                //tl.setSelectedTabIndicatorColor(color);
//                tl.setBackgroundColor(color);
                tl.setSelectedTabIndicatorColor(color);
                tl.setTabTextColors(createColorStateList(0xff666666, color, color, color));
            }
        }
    }

//    private static ColorStateList createColorStateList(TabLayout tl ,int defaultColor, int selectedColor) {
//        final int[][] states = new int[2][];
//        final int[] colors = new int[2];
//        int i = 0;
//
//        states[i] = tl.SELECTED_STATE_SET;
//        colors[i] = selectedColor;
//        i++;
//
//        // Default enabled state
//        states[i] = EMPTY_STATE_SET;
//        colors[i] = defaultColor;
//        i++;
//
//        return new ColorStateList(states, colors);
//    }

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
                new int[] {-android.R.attr.state_selected},
                new int[] { android.R.attr.state_selected}
        };

        int[] colors = new int[] {
                normal,
                pressed,
        };

        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
}
