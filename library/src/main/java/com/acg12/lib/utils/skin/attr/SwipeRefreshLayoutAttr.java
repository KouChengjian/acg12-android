package com.acg12.lib.utils.skin.attr;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.acg12.lib.utils.skin.SkinManager;
import com.acg12.lib.utils.skin.entity.SkinAttr;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019/4/3 16:30
 * Description:
 */
public class SwipeRefreshLayoutAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof SwipeRefreshLayout) {
            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                int color = SkinManager.getInstance().getColor(attrValueRefId);
                swipeRefreshLayout.setColorSchemeColors(color);
            }
        }
    }
}
