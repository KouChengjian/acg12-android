package com.skin.loader.listener;

import java.util.List;

import android.view.View;

import com.skin.loader.entity.DynamicAttr;

public interface IDynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
