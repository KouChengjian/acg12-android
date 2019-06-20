package com.acg12.lib.utils.skin.attr;

import android.view.View;
import android.widget.TextView;

import com.acg12.lib.utils.skin.SkinManager;
import com.acg12.lib.utils.skin.entity.SkinAttr;


public class TextColorAttr extends SkinAttr {

	@Override
	public void apply(View view) {
		if(view instanceof TextView){
			TextView tv = (TextView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				tv.setTextColor(SkinManager.getInstance().convertToColorStateList(attrValueRefId));
			}
		}
	}
}
