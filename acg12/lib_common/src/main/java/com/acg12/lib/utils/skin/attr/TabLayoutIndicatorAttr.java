package com.acg12.lib.utils.skin.attr;

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
                tl.setBackgroundColor(color);
            }
        }
    }
}
