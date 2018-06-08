package com.acg12.lib.ui.activity;

import android.os.Bundle;

import com.acg12.lib.ui.base.IPresenter;
import com.acg12.lib.ui.base.IView;
import com.acg12.lib.ui.base.PresenterHelper;


public class PresenterActivityImpl<T extends IView> extends BsaeActivity implements IPresenter<T> {

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
