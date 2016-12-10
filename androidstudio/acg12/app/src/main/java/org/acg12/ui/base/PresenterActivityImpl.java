package org.acg12.ui.base;

import android.os.Bundle;

import org.acg12.ui.IPresenter;
import org.acg12.ui.IView;


public class PresenterActivityImpl<T extends IView> extends BaseActivity implements IPresenter<T> {

	protected T mView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(savedInstanceState);
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
