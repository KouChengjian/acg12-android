package org.acg12.listener;

import android.view.View;

import org.acg12.utlis.skin.entity.DynamicAttr;

import java.util.List;


public interface IDynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
