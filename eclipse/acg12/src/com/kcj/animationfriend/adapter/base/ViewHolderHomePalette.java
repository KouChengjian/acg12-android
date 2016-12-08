package com.kcj.animationfriend.adapter.base;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.view.ScaleImageView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewHolderHomePalette extends RecyclerView.ViewHolder{

	public TextView tv_palete_name;
	public TextView tv_palete_num;
	public ScaleImageView imageView;
	public ImageView iv_palete_icon_1;
	public ImageView iv_palete_icon_2;
	public ImageView iv_palete_icon_3;
	
	public ViewHolderHomePalette(View itemView) {
		super(itemView);
		tv_palete_name = (TextView)itemView.findViewById(R.id.tv_fragment_palete_name);
		tv_palete_num = (TextView)itemView.findViewById(R.id.tv_fragment_palete_num);
		imageView = (ScaleImageView)itemView.findViewById(R.id.iv_fragment_palete);
		iv_palete_icon_1 = (ImageView)itemView.findViewById(R.id.iv_fragment_palete_icon_1);
		iv_palete_icon_2 = (ImageView)itemView.findViewById(R.id.iv_fragment_palete_icon_2);
		iv_palete_icon_3 = (ImageView)itemView.findViewById(R.id.iv_fragment_palete_icon_3);
	}

}
