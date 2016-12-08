package com.kcj.animationfriend.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.im.bean.BmobChatUser;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.base.BaseListAdapter;
import com.kcj.animationfriend.adapter.base.ViewHolder;
import com.kcj.animationfriend.util.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/** 
 * @ClassName: BlackListAdapter
 * @Description: 黑名单
 * @author smile
 * @date 2014-6-24 下午5:27:14
 */
public class BlackListAdapter extends BaseListAdapter<BmobChatUser>{

	public BlackListAdapter(Context context, List<BmobChatUser> list) {
		super(context, list);
	}

	@Override
	public View bindView(int arg0, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_black_list, null);
		}
		final BmobChatUser contract = getList().get(arg0);
		TextView tv_friend_name = ViewHolder.get(convertView, R.id.tv_friend_name);
		ImageView iv_avatar = ViewHolder.get(convertView, R.id.img_friend_avatar);
		String avatar = contract.getAvatar();
		if (avatar != null && !avatar.equals("")) {
			ImageLoader.getInstance().displayImage(avatar, iv_avatar, ImageLoadOptions.getOptions());
		} else {
			iv_avatar.setImageResource(R.drawable.user_icon_default);
		}
		tv_friend_name.setText(contract.getUsername());
		return convertView;
	}
}
