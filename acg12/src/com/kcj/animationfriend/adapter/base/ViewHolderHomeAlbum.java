package com.kcj.animationfriend.adapter.base;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.view.ScaleImageView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewHolderHomeAlbum extends RecyclerView.ViewHolder{

	public ScaleImageView imageView;
	public LinearLayout ll_home_album_comment;
	public TextView tv_home_album_comment;
	public LinearLayout ll_home_album_attribute;
	public TextView tv_home_album_collection;
	public TextView tv_home_album_love;
	public RelativeLayout rl_home_album_icom;
	public ImageView iv_home_album_logo;
	public TextView tv_home_album_name;
	
	public ViewHolderHomeAlbum(View itemView) {
		super(itemView);
		imageView = (ScaleImageView) itemView.findViewById(R.id.iv_album_pic);
		ll_home_album_comment = (LinearLayout) itemView.findViewById(R.id.ll_home_album_comment);
		tv_home_album_comment = (TextView) itemView.findViewById(R.id.tv_home_album_comment);
		ll_home_album_attribute = (LinearLayout) itemView.findViewById(R.id.ll_home_album_attribute);
		tv_home_album_collection = (TextView) itemView.findViewById(R.id.tv_home_album_collection);
		tv_home_album_love = (TextView) itemView.findViewById(R.id.tv_home_album_love);
		rl_home_album_icom = (RelativeLayout) itemView.findViewById(R.id.rl_home_album_icom);
		iv_home_album_logo = (ImageView) itemView.findViewById(R.id.iv_home_album_logo);
		tv_home_album_name = (TextView) itemView.findViewById(R.id.tv_home_album_name);
	}

}
