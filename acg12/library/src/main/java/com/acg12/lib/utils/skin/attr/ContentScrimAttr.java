package com.acg12.lib.utils.skin.attr;

import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;

import com.acg12.lib.utils.skin.SkinManager;
import com.acg12.lib.utils.skin.entity.SkinAttr;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/3/30 11:05
 * Description:
 */
public class ContentScrimAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof CollapsingToolbarLayout) {
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                ((CollapsingToolbarLayout)view).setContentScrimColor(SkinManager.getInstance().getColor(attrValueRefId));
            }
        }
    }
}
