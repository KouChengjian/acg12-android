package com.kcj.animationfriend.listener;

/**
 * @ClassName: ISignUpListener
 * @Description: 注册
 * @author: KCJ
 * @date:
 */
public interface ISignUpListener {
	void onSignUpSuccess();

	void onSignUpFailure(String msg);
}
