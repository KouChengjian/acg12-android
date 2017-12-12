package com.acg12.cc.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class ViewImpl implements IView {

	/**
     * create方法生成的view
     */
    protected View mRootView;
    
    /**
     * 绑定的presenter
     */
	protected IPresenter mPresenter;
	
	@Override
	public final View create(LayoutInflater inflater, ViewGroup container) {
		mRootView = inflater.inflate(getLayoutId(), container, false);
		ButterKnife.bind(this, mRootView);
        return mRootView;
	}

	@Override
	public void created(){}
	
	@Override
	public void bindEvent() {}

	public Context getContext(){
		return mRootView.getContext();
	}

	@Override
	public <V extends View> V findViewById(int id) {
		return (V) mRootView.findViewById(id);
	}

	@Override
    public void bindPresenter(IPresenter presenter) {
        mPresenter = presenter;
    }

}
