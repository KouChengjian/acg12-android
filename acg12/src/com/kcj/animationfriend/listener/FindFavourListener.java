package com.kcj.animationfriend.listener;

import java.util.List;

import com.kcj.animationfriend.bean.Album;

/**
 * @ClassName: FindFavourListener
 * @Description: 点赞
 * @author: KCJ
 * @date:
 */
public interface FindFavourListener {
	public void onFindFavourSuccess(List<Album> list);
	public void onFindFavourFailure(String msg);
}