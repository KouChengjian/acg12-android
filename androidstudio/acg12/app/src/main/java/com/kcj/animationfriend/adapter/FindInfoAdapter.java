package com.kcj.animationfriend.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.Video;

public class FindInfoAdapter extends BaseAdapter{

	private Context mContext;
	private List<Video> mList;
	
	public FindInfoAdapter(Context mContext,List<Video> mList){
		this.mContext = mContext;
		this.mList = mList;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Video getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Video videoItem = getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_video_info_num, null);
			holder.titleTextView = (TextView) convertView.findViewById(R.id.title);
			holder.itemView = convertView.findViewById(R.id.linearlayout_row);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.titleTextView.setText(videoItem.getTitle());
		return convertView;
	}

	class ViewHolder {
		View itemView;
		TextView titleTextView;
	}
}
