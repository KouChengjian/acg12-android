package com.acg12.cc.ui.base;

import android.os.Bundle;

import com.acg12.cc.ui.IPresenter;
import com.acg12.cc.ui.IView;


public class PresenterActivityImpl<T extends IView> extends CCBaseActivity implements IPresenter<T> {

	protected T mView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        create(savedInstanceState);
        super.onCreate(savedInstanceState);
        try {
            mView = getViewClass().newInstance();
            setContentView(mView.create(getLayoutInflater(), null));
            mView.bindPresenter(this);
            mView.created();
            mView.bindEvent();
            created(savedInstanceState);
        } catch(Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
	
	@Override
	public Class<T> getViewClass() {
		return PresenterHelper.getViewClass(getClass());
	}

	@Override
	public void create(Bundle savedInstance) {}

	@Override
	public void created(Bundle savedInstance) {}

}
