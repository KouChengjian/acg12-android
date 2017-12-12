package com.acg12.cc.ui.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.cc.ui.IPresenter;
import com.acg12.cc.ui.IView;


public class PresenterFragmentImpl<T extends IView> extends CCBaseFragment implements IPresenter<T> {

	protected T mView;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        create(savedInstanceState);
        try {
            mView = getViewClass().newInstance();
            View view = mView.create(inflater, container);
            return view;
        } catch(Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView.bindPresenter(this);
        mView.created();
        mView.bindEvent();
        created(savedInstanceState);
    }

    @Override
	public Class<T> getViewClass() {
		return PresenterHelper.getViewClass(getClass());
	}

	@Override
	public void create(Bundle savedInstance) {
	}

	@Override
	public void created(Bundle savedInstance) {
	}

}
