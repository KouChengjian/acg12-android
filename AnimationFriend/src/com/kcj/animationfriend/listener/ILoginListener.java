package com.kcj.animationfriend.listener;


/**
 * @ClassName: ILoginListener
 * @Description: 登入
 * @author: KCJ
 * @date:
 */
public interface ILoginListener {
	void onLoginSuccess();

	void onLoginFailure(String msg);
}
