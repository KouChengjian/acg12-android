package com.kcj.animationfriend.config;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.kcj.animationfriend.bean.Album;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.bean.Video;
import com.liteutil.http.LiteHttp;
import com.liteutil.orm.LiteOrm;
import com.liteutil.orm.db.DataBase;

/**
 * @ClassName: UserProxy
 * @Description: 用户操作信息-缓存
 * @author: KCJ
 * @date: 
 */
public class UserProxy {

	public static UserProxy userProxy;
	public Context mContext;
	public static DataBase db = null;
	public static List<Album> commentAlbum ; // 存储当前评论详细的画集
	public static Palette currentPalette; // 当前选择的画板
	public static int position = 0; // 当前主界面的位置
	public static ArrayList<Video> videoInfoList = new ArrayList<Video>();
	
	
	public UserProxy(Context mContext) {
		this.mContext = mContext;
		userProxy = this;
		new LiteHttp(mContext);
	    db = LiteOrm.newSingleInstance(mContext, Constant.DB_NAME);
	}
	
	public static UserProxy getUserProxyInstance(){
		return userProxy;
	}
	
	public static DataBase getLiteOrmInstance(){
		return db;
	}
	
	
}
