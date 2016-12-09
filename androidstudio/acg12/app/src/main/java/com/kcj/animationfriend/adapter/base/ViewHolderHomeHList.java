package com.kcj.animationfriend.adapter.base;

import com.kcj.animationfriend.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewHolderHomeHList extends RecyclerView.ViewHolder{

	public ImageView icon; // 图片
	public TextView ll_item_area_num;  // 数目
	public TextView ll_item_area_name; // 名称
	public TextView ll_item_area_author;// 作者
	
	public LinearLayout ll_item_area_album;
	public TextView tv_item_area_album_collection;
	public TextView tv_item_area_album_love;
	public LinearLayout ll_item_area_vedio;
	public TextView tv_item_area_vedio_baofang;
	public TextView tv_item_area_vedio_danmugu;
	public LinearLayout ll_item_name;
	public TextView tv_item_name;
	
	public ViewHolderHomeHList(View itemView) {
		super(itemView);
		icon = (ImageView) itemView.findViewById(R.id.iv_item_area_hlist);
		ll_item_area_num = (TextView) itemView.findViewById(R.id.ll_item_area_num);
		ll_item_area_name = (TextView) itemView.findViewById(R.id.ll_item_area_name);
		ll_item_area_author = (TextView) itemView.findViewById(R.id.ll_item_area_author);
		ll_item_area_album = (LinearLayout) itemView.findViewById(R.id.ll_item_area_album);
		tv_item_area_album_collection = (TextView) itemView.findViewById(R.id.tv_item_area_album_collection);
		tv_item_area_album_love = (TextView) itemView.findViewById(R.id.tv_item_area_album_love);
		ll_item_area_vedio = (LinearLayout) itemView.findViewById(R.id.ll_item_area_vedio);
		tv_item_area_vedio_baofang = (TextView) itemView.findViewById(R.id.tv_item_area_vedio_baofang);
		tv_item_area_vedio_danmugu = (TextView) itemView.findViewById(R.id.tv_item_area_vedio_danmugu);
		ll_item_name = (LinearLayout) itemView.findViewById(R.id.ll_item_name);
		tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
	}

}
