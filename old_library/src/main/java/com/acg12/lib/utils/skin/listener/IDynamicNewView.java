package com.acg12.lib.utils.skin.listener;

import android.view.View;

import com.acg12.lib.utils.skin.entity.DynamicAttr;

import java.util.List;


public interface IDynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
