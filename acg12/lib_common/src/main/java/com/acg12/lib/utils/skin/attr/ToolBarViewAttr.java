package com.acg12.lib.utils.skin.attr;

import android.view.View;

import com.acg12.lib.utils.skin.SkinManager;
import com.acg12.lib.utils.skin.entity.SkinAttr;
import com.acg12.lib.widget.ToolBarView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/7/3 16:59
 * Description:
 */
public class ToolBarViewAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof ToolBarView) {
            ToolBarView toolBarView = (ToolBarView)view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                int color = SkinManager.getInstance().getColor(attrValueRefId);
                toolBarView.setBackground(color);
            }
        }
    }
}
