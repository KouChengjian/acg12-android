package com.kcj.animationfriend.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.ui.base.BaseFragment;

/**
 * @ClassName: UserResULFragment
 * @Description: 用户资源--上传
 * @author: KCJ
 * @date: 2015-12-09
 */
public class UserResULFragment extends BaseFragment{

	public static BaseFragment newInstance(User user) {
    	BaseFragment fragment = new UserResULFragment();
    	Bundle args = new Bundle();
    	args.putSerializable("user", user);
    	fragment.setArguments(args);
    	return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_res_uploading, container, false);
		setContentView(view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initEvent();
		initDatas();
	}
	
}
