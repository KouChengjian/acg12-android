package com.acg12.common.utils.skin.listener;

import android.view.View;

import com.acg12.common.utils.skin.entity.DynamicAttr;

import java.util.List;


public interface IDynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
