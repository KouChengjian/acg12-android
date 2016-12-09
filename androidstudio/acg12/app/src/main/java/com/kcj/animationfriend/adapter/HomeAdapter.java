package com.kcj.animationfriend.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.Area;
import com.kcj.animationfriend.ui.HomeActivity;

/**
 * @ClassName: AreaAdapter
 * @Description: 分区
 * @author: KCJ
 * @date:  
 */
public class HomeAdapter extends BaseAdapter{

	private Context mContext;
	private List<Area> mList;
	
	public HomeAdapter(Context mContext,List<Area> mList){
		this.mContext = mContext;
		this.mList = mList;	
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Area getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_area, null);
			viewHolder = new ViewHolder();
			viewHolder.rl_home_title = (RelativeLayout) convertView.findViewById(R.id.rl_home_title);
			viewHolder.tv_home_title = (TextView) convertView.findViewById(R.id.tv_home_title);
			viewHolder.tv_home_go = (TextView) convertView.findViewById(R.id.tv_home_go);
			viewHolder.gv_home_show = (RecyclerView) convertView.findViewById(R.id.list_tools);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Area area = mList.get(position);
		viewHolder.tv_home_title.setText(area.getName());
		HomeHListAdapter areaHListAdapter = null;
		if(viewHolder.gv_home_show.getAdapter() instanceof HomeHListAdapter){
			areaHListAdapter = (HomeHListAdapter) viewHolder.gv_home_show.getAdapter();
			areaHListAdapter.setList(area.getObjectList());
			areaHListAdapter.notifyDataSetChanged();
		}else{
			LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);  
	        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);  
	        viewHolder.gv_home_show.setLayoutManager(linearLayoutManager); 
			areaHListAdapter = new HomeHListAdapter(mContext , area.getObjectList());
			viewHolder.gv_home_show.setAdapter(areaHListAdapter);
		}
		viewHolder.tv_home_go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				if(position == 0){
					intent.setClass(mContext, HomeActivity.class);
					intent.putExtra("AreaType",1);
				}else if(position == 1){
					intent.setClass(mContext, HomeActivity.class);
					intent.putExtra("AreaType",2);
				}else if(position == 2){
					intent.setClass(mContext, HomeActivity.class);
					intent.putExtra("AreaType",4);
				}else if(position == 3){
					intent.setClass(mContext, HomeActivity.class);
					intent.putExtra("AreaType",5);
				}
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		RelativeLayout rl_home_title;
		TextView tv_home_title;
		TextView tv_home_go;
		RecyclerView gv_home_show;
	}
}
