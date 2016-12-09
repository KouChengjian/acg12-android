package com.kcj.animationfriend.adapter.base;

import com.kcj.animationfriend.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewHolderHome extends RecyclerView.ViewHolder{
	
	public RelativeLayout rl_home_title;
	public TextView tv_home_title;
	public TextView tv_home_go;
	public RecyclerView gv_home_show;
	
	public ViewHolderHome(View itemView) {
		super(itemView);
		rl_home_title = (RelativeLayout) itemView.findViewById(R.id.rl_home_title);
		tv_home_title = (TextView) itemView.findViewById(R.id.tv_home_title);
		tv_home_go = (TextView) itemView.findViewById(R.id.tv_home_go);
		gv_home_show = (RecyclerView) itemView.findViewById(R.id.list_tools);
	}
	
	public ViewHolderHome(View itemView,View view) {
		super(itemView);
	}

}
