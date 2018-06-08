package com.acg12.lib.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.lib.ui.base.IPresenter;
import com.acg12.lib.ui.base.IView;
import com.acg12.lib.ui.base.PresenterHelper;


public class PresenterFragmentImpl<T extends IView> extends BaseFragment implements IPresenter<T> {

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
