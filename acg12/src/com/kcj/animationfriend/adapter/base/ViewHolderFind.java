package com.kcj.animationfriend.adapter.base;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.view.ScaleImageView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolderFind extends RecyclerView.ViewHolder{

	public ScaleImageView icon;
	public TextView msg;
	public TextView num;
	
	public ViewHolderFind(View itemView) {
		super(itemView);
		icon = (ScaleImageView) itemView.findViewById(R.id.iv_album_pic);
		msg = (TextView) itemView.findViewById(R.id.list_item_title);
		num = (TextView) itemView.findViewById(R.id.list_item_num);
	}

}
