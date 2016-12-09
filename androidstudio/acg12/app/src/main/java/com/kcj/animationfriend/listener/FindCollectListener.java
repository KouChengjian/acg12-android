package com.kcj.animationfriend.listener;

import java.util.List;

import com.kcj.animationfriend.bean.Album;

/**
 * @ClassName: FindCollectListener
 * @Description: 采集
 * @author: KCJ
 * @date:
 */
public interface FindCollectListener {
	public void onFindCollectSuccess(List<Album> list);
	public void onFindCollectFailure(String msg);
}