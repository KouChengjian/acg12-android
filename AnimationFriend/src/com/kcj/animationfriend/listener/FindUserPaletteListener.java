package com.kcj.animationfriend.listener;

import java.util.List;

import com.kcj.animationfriend.bean.Palette;

/**
 * @ClassName: FindAllPaletteListener
 * @Description: 查询所有关注
 * @author: KCJ
 * @date:
 */
public interface FindUserPaletteListener {
	public void onFindUserPaletteSuccess(List<Palette> list);
	public void onFindUserPaletteFailure(String msg);
}