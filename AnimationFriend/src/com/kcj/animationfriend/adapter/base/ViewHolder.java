package com.kcj.animationfriend.adapter.base;

import android.util.SparseArray;
import android.view.View;

/**
 * @ClassName: ViewHolder
 * @Description: Viewholder的简化
 * @author: 
 * @date: 2014-5-28 上午9:56:29
 */
public class ViewHolder {
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}
