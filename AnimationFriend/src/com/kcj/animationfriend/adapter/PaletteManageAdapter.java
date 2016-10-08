package com.kcj.animationfriend.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.Palette;


/**
 * @ClassName: PaletteAdapter
 * @Description: 画板管理适配器
 * @author: KCJ
 * @date: 
 */
public class PaletteManageAdapter extends BaseAdapter{

	private Context mContext;
	private List<Palette> list;
	
	public PaletteManageAdapter(Context mContext, List<Palette> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	public void addAll(List<Palette> datas) {
		list.addAll(datas);
	}
	
	public void add(Palette datas) {
		list.add(datas);
	}
	
	public List<Palette> getList() {
		return list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		Palette palette = list.get(position);
		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
			convertView = layoutInflator.inflate(R.layout.include_palette_manage_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.tv_palette_name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(palette.getName());
		return convertView;
	}

	class ViewHolder {
		TextView name;
	}
}
