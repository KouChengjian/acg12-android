package com.kcj.animationfriend.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.listener.ILoginListener;
import com.kcj.animationfriend.listener.IResetPasswordListener;
import com.kcj.animationfriend.listener.ISignUpListener;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.util.StringUtils;
import com.kcj.animationfriend.util.TelNumMatch;
import com.kcj.animationfriend.view.DeletableEditText;
import com.kcj.animationfriend.view.SmoothProgressBar;

/**
 * @ClassName: RstAndLoginActivity
 * @Description: 注册 登入 寻回密码
 * @author:
 * @date:
 */
public class RstAndLoginActivity extends BaseSwipeBackActivity implements
		OnClickListener, ILoginListener, ISignUpListener,
		IResetPasswordListener {

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	protected TextView loginTitle;
	protected TextView registerTitle;
	protected TextView resetPassword;
	protected DeletableEditText userNameInput;
	protected DeletableEditText userPasswordInput;
	protected DeletableEditText userEmailInput;
	protected Button registerButton;
	protected SmoothProgressBar progressbar;
	protected UserOperation operation = UserOperation.LOGIN;
	
	public static ParameCallBack parameCallBack;

	private enum UserOperation {
		LOGIN, REGISTER, RESET_PASSWORD
	}
	
	public static void setParameCallBack(ParameCallBack callBack){
		parameCallBack = callBack;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rstlogin);
		initViews();
		initEvents();
		updateLayout(operation);
	}

	public void initViews() {
		// init actionbar
		setTitle(R.string.login);
		setSupportActionBar(toolbar);
		loginTitle = (TextView) findViewById(R.id.login_menu);
		registerTitle = (TextView) findViewById(R.id.register_menu);
		resetPassword = (TextView) findViewById(R.id.reset_password_menu);
		userNameInput = (DeletableEditText) findViewById(R.id.user_name_input);
		userPasswordInput = (DeletableEditText) findViewById(R.id.user_password_input);
		userEmailInput = (DeletableEditText) findViewById(R.id.user_email_input);
		registerButton = (Button) findViewById(R.id.register);
		progressbar = (SmoothProgressBar) findViewById(R.id.sm_progressbar);

	}

	public void initEvents() {
		loginTitle.setOnClickListener(this);
		registerTitle.setOnClickListener(this);
		resetPassword.setOnClickListener(this);
		registerButton.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	private void updateLayout(UserOperation op) {
		if (op == UserOperation.LOGIN) {
			loginTitle.setTextColor(Color.parseColor("#D95555"));
			loginTitle.setBackgroundResource(R.drawable.bg_login_tab);
			loginTitle.setPadding(16, 16, 16, 16);
			loginTitle.setGravity(Gravity.CENTER);

			registerTitle.setTextColor(Color.parseColor("#888888"));
			registerTitle.setBackgroundDrawable(null);
			registerTitle.setPadding(16, 16, 16, 16);
			registerTitle.setGravity(Gravity.CENTER);

			resetPassword.setTextColor(Color.parseColor("#888888"));
			resetPassword.setBackgroundDrawable(null);
			resetPassword.setPadding(16, 16, 16, 16);
			resetPassword.setGravity(Gravity.CENTER);

			userNameInput.setVisibility(View.VISIBLE);
			userPasswordInput.setVisibility(View.VISIBLE);
			userEmailInput.setVisibility(View.GONE);
			registerButton.setText("登录");
		} else if (op == UserOperation.REGISTER) {
			loginTitle.setTextColor(Color.parseColor("#888888"));
			loginTitle.setBackgroundDrawable(null);
			loginTitle.setPadding(16, 16, 16, 16);
			loginTitle.setGravity(Gravity.CENTER);

			registerTitle.setTextColor(Color.parseColor("#D95555"));
			registerTitle.setBackgroundResource(R.drawable.bg_login_tab);
			registerTitle.setPadding(16, 16, 16, 16);
			registerTitle.setGravity(Gravity.CENTER);

			resetPassword.setTextColor(Color.parseColor("#888888"));
			resetPassword.setBackgroundDrawable(null);
			resetPassword.setPadding(16, 16, 16, 16);
			resetPassword.setGravity(Gravity.CENTER);

			userNameInput.setVisibility(View.VISIBLE);
			userPasswordInput.setVisibility(View.VISIBLE);
			userEmailInput.setVisibility(View.VISIBLE);
			registerButton.setText("注册");
		} else {
			startAnimActivity(ConstructActivity.class);
//			loginTitle.setTextColor(Color.parseColor("#888888"));
//			loginTitle.setBackgroundDrawable(null);
//			loginTitle.setPadding(16, 16, 16, 16);
//			loginTitle.setGravity(Gravity.CENTER);
//
//			registerTitle.setTextColor(Color.parseColor("#888888"));
//			registerTitle.setBackgroundDrawable(null);
//			registerTitle.setPadding(16, 16, 16, 16);
//			registerTitle.setGravity(Gravity.CENTER);
//
//			resetPassword.setTextColor(Color.parseColor("#D95555"));
//			resetPassword.setBackgroundResource(R.drawable.bg_login_tab);
//			resetPassword.setPadding(16, 16, 16, 16);
//			resetPassword.setGravity(Gravity.CENTER);
//
//			userNameInput.setVisibility(View.GONE);
//			userPasswordInput.setVisibility(View.GONE);
//			userEmailInput.setVisibility(View.VISIBLE);
//			registerButton.setText("找回密码");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register: // 确定
			if (operation == UserOperation.LOGIN) { // 登入
				if (TextUtils.isEmpty(userNameInput.getText())) {
					userNameInput.setShakeAnimation();
					Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (TextUtils.isEmpty(userPasswordInput.getText())) {
					userPasswordInput.setShakeAnimation();
					Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				HttpProxy.setOnLoginListener(this);
				progressbar.setVisibility(View.VISIBLE);
				HttpProxy.login(mContext,userNameInput.getText().toString().trim(),
						userPasswordInput.getText().toString().trim());
			} else if (operation == UserOperation.REGISTER) { // 注册
				TelNumMatch telNumMatch = new TelNumMatch(userNameInput.getText().toString());
				if (TextUtils.isEmpty(userNameInput.getText())) {
					userNameInput.setShakeAnimation();
					ShowToast("请输入用户名");
					return;
				}
				if(telNumMatch.matchNum() == 4 || telNumMatch.matchNum() == 5){
					userNameInput.setShakeAnimation();
					ShowToast("手机号码错误");
					return;
				}
				if (TextUtils.isEmpty(userPasswordInput.getText())) {
					userPasswordInput.setShakeAnimation();
					ShowToast("请输入密码");
					return;
				}
				
						
//				if (TextUtils.isEmpty(userEmailInput.getText())) {
//					userEmailInput.setShakeAnimation();
//					Toast.makeText(mContext, "请输入邮箱地址", Toast.LENGTH_SHORT)
//							.show();
//					return;
//				}
//				if (!StringUtils.isValidEmail(userEmailInput.getText())) {
//					userEmailInput.setShakeAnimation();
//					Toast.makeText(mContext, "邮箱格式不正确", Toast.LENGTH_SHORT)
//							.show();
//					return;
//				}
				HttpProxy.setOnSignUpListener(this);
				progressbar.setVisibility(View.VISIBLE);
				HttpProxy.signUp(mContext,userNameInput.getText().toString().trim(),
						userPasswordInput.getText().toString().trim(),
						userEmailInput.getText().toString().trim());
			} else { // 找回密码
				if (TextUtils.isEmpty(userEmailInput.getText())) {
					userEmailInput.setShakeAnimation();
					Toast.makeText(mContext, "请输入邮箱地址", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (!StringUtils.isValidEmail(userEmailInput.getText())) {
					userEmailInput.setShakeAnimation();
					Toast.makeText(mContext, "邮箱格式不正确", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				HttpProxy.setOnResetPasswordListener(this);
				progressbar.setVisibility(View.VISIBLE);
				HttpProxy.resetPassword(userEmailInput.getText().toString()
						.trim());
			}
			break;
		case R.id.login_menu: // 登入界面
			operation = UserOperation.LOGIN;
			updateLayout(operation);
			break;
		case R.id.register_menu: // 注册界面
			operation = UserOperation.REGISTER;
			updateLayout(operation);
			break;
		case R.id.reset_password_menu: // 找回密码界面
//			operation = UserOperation.RESET_PASSWORD;
//			updateLayout(operation);
			startAnimActivity(ConstructActivity.class);
			break;
		default:
			break;
		}
	}

	/**
	 * 重置密码成功
	 */
	@Override
	public void onResetSuccess() {
		dimissProgressbar();
		ShowToast("请到邮箱修改密码后再登录。");
		operation = UserOperation.LOGIN;
		updateLayout(operation);
	}

	/**
	 * 重置密码失败
	 */
	@Override
	public void onResetFailure(String msg) {
		dimissProgressbar();
		ShowToast("重置密码失败。请确认网络连接后再重试。");
	}

	/**
	 * 注册成功
	 */
	@Override
	public void onSignUpSuccess() {
		//更新地理位置信息
		updateUserLocation();
		dimissProgressbar();
		ShowToast("注册成功");
		operation = UserOperation.LOGIN;
		updateLayout(operation);
	}

	/**
	 * 注册失败
	 */
	@Override
	public void onSignUpFailure(String msg) {
		dimissProgressbar();
		ShowToast(msg);
	}

	/**
	 * 登入成功
	 */
	@Override
	public void onLoginSuccess() {
		updateUserInfos();
		dimissProgressbar();
		ShowToast("登录成功。");
		setResult(RESULT_OK);
		finish();
	}

	/**
	 * 登入失败
	 */
	@Override
	public void onLoginFailure(String msg) {
		dimissProgressbar();
		ShowToast("登录失败~" + msg);
	}

	private void dimissProgressbar() {
		if (progressbar != null && progressbar.isShown()) {
			progressbar.setVisibility(View.GONE);
		}
	}

}
