package com.kcj.animationfriend.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobGeoPoint;

import com.kcj.animationfriend.MyApplication;
import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.base.BaseListAdapter;
import com.kcj.animationfriend.adapter.base.ViewHolder;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.util.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * @ClassName: BlackListAdapter
 * @Description: 附近的人
 * @author: 
 * @date:
 */
public class NearPeopleAdapter extends BaseListAdapter<User>{

	public NearPeopleAdapter(Context context, List<User> list) {
		super(context, list);
	}

	@Override
	public View bindView(int arg0, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_near_people, null);
		}
		final User contract = getList().get(arg0);
		TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
		TextView tv_distance = ViewHolder.get(convertView, R.id.tv_distance);
		TextView tv_logintime = ViewHolder.get(convertView, R.id.tv_logintime);
		ImageView iv_avatar = ViewHolder.get(convertView, R.id.iv_avatar);
		String avatar = contract.getAvatar();
		if (avatar != null && !avatar.equals("")) {
			ImageLoader.getInstance().displayImage(avatar, iv_avatar,
					ImageLoadOptions.getOptions());
		} else {
			iv_avatar.setImageResource(R.drawable.user_icon_default);
		}
		BmobGeoPoint location = contract.getLocation();
		String currentLat = MyApplication.getInstance().getLatitude();
		String currentLong = MyApplication.getInstance().getLongtitude();
		if(location!=null && !currentLat.equals("") && !currentLong.equals("")){
			double distance = DistanceOfTwoPoints(Double.parseDouble(currentLat),Double.parseDouble(currentLong),contract.getLocation().getLatitude(), 
					contract.getLocation().getLongitude());
			tv_distance.setText(String.valueOf(distance)+"米");
		}else{
			tv_distance.setText("未知");
		}
		tv_name.setText(contract.getUsername());
		tv_logintime.setText("最近登录时间:"+contract.getUpdatedAt());
		return convertView;
	}

	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return 距离：单位为米
	 */
	public static double DistanceOfTwoPoints(double lat1, double lng1,double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
}
