package com.kcj.animationfriend.listener;


/**
 * @ClassName: IResetPasswordListener
 * @Description: 重置密码
 * @author: KCJ
 * @date:
 */
public interface IResetPasswordListener {
	void onResetSuccess();

	void onResetFailure(String msg);
}
