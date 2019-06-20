package com.acg12.lib.utils.skin;

import com.acg12.lib.utils.skin.attr.BackgroundAttr;
import com.acg12.lib.utils.skin.attr.ContentScrimAttr;
import com.acg12.lib.utils.skin.attr.DividerAttr;
import com.acg12.lib.utils.skin.attr.ListSelectorAttr;
import com.acg12.lib.utils.skin.attr.NavigationViewAttr;
import com.acg12.lib.utils.skin.attr.SwipeRefreshLayoutAttr;
import com.acg12.lib.utils.skin.attr.TabLayoutIndicatorAttr;
import com.acg12.lib.utils.skin.attr.TextColorAttr;
import com.acg12.lib.utils.skin.attr.ToolBarViewAttr;
import com.acg12.lib.utils.skin.entity.SkinAttr;

public class AttrFactory {

    public static final String BACKGROUND = "background";
    public static final String TEXT_COLOR = "textColor";
    public static final String LIST_SELECTOR = "listSelector";
    public static final String DIVIDER = "divider";
    public static final String CONTENTSCRIM = "contentScrim";
    public static final String SELECTORDTABINDICATOR = "tabIndicatorColor";
    public static final String TOOLBARVIEWBACKGROUND = "bar_background_color";
    public static final String RECYCLE_REFRESH_SCHEME = "recycle_refresh_scheme";
    public static final String NAV_HEADER_BACKGROUND_COLOR = "nav_header_background_color";

    public static final String NAVIGATIONVIEW = "navigationView";

    public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName) {

        SkinAttr mSkinAttr = null;

        if (BACKGROUND.equals(attrName)) {
            mSkinAttr = new BackgroundAttr();
        } else if (TEXT_COLOR.equals(attrName)) {
            mSkinAttr = new TextColorAttr();
        } else if (LIST_SELECTOR.equals(attrName)) {
            mSkinAttr = new ListSelectorAttr();
        } else if (DIVIDER.equals(attrName)) {
            mSkinAttr = new DividerAttr();
        } else if (CONTENTSCRIM.equals(attrName)) {
            mSkinAttr = new ContentScrimAttr();
        } else if (SELECTORDTABINDICATOR.equals(attrName)) {
            mSkinAttr = new TabLayoutIndicatorAttr();
        } else if (TOOLBARVIEWBACKGROUND.equals(attrName)) {
            mSkinAttr = new ToolBarViewAttr();
        }  else if (RECYCLE_REFRESH_SCHEME.equals(attrName)) {
            mSkinAttr = new SwipeRefreshLayoutAttr();
        } else if (NAVIGATIONVIEW.equals(attrName)) {
            mSkinAttr = new NavigationViewAttr();
        } else if (NAV_HEADER_BACKGROUND_COLOR.equals(attrName)) {
            mSkinAttr = new NavigationViewAttr();
        } else {
            return null;
        }

        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValueRefId = attrValueRefId;
        mSkinAttr.attrValueRefName = attrValueRefName;
        mSkinAttr.attrValueTypeName = typeName;
        return mSkinAttr;
    }

    /**
     * Check whether the attribute is supported
     *
     * @param attrName
     * @return true : supported <br>
     * false: not supported
     */
    public static boolean isSupportedAttr(String attrName) {
        if (BACKGROUND.equals(attrName)) {
            return true;
        }
        if (TEXT_COLOR.equals(attrName)) {
            return true;
        }
        if (LIST_SELECTOR.equals(attrName)) {
            return true;
        }
        if (DIVIDER.equals(attrName)) {
            return true;
        }
        if (CONTENTSCRIM.equals(attrName)) {
            return true;
        }
        if (SELECTORDTABINDICATOR.equals(attrName)) {
            return true;
        }
        if (TOOLBARVIEWBACKGROUND.equals(attrName)) {
            return true;
        }
        if (RECYCLE_REFRESH_SCHEME.equals(attrName)) {
            return true;
        }
        if (NAV_HEADER_BACKGROUND_COLOR.equals(attrName)) {
            return true;
        }
        return false;
    }
}
