package com.skin.loader.entity;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.skin.loader.entity.base.SkinAttr;
import com.skin.loader.loader.SkinManager;

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
