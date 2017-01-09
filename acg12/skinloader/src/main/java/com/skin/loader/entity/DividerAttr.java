package com.skin.loader.entity;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

import com.skin.loader.entity.base.SkinAttr;
import com.skin.loader.loader.SkinManager;

public class DividerAttr extends SkinAttr {

	public int dividerHeight = 1;
	
	@Override
	public void apply(View view) {
		if(view instanceof ListView){
			ListView tv = (ListView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				int color = SkinManager.getInstance().getColor(attrValueRefId);
				ColorDrawable sage = new ColorDrawable(color);
				tv.setDivider(sage);
				tv.setDividerHeight(dividerHeight);
			}else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
				tv.setDivider(SkinManager.getInstance().getDrawable(attrValueRefId));
			}
		}
	}
}
