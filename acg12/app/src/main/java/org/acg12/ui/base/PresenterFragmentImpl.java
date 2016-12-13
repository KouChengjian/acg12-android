package org.acg12.ui.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.acg12.ui.IPresenter;
import org.acg12.ui.IView;


public class PresenterFragmentImpl<T extends IView> extends BaseFragment implements IPresenter<T> {

	protected T mView;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        create(savedInstanceState);
        try {
            mView = getViewClass().newInstance();
            View view = mView.create(inflater, container);
            mView.bindPresenter(this);
            mView.created();
            mView.bindEvent();
            created(savedInstanceState);
            return view;
        } catch(Exception e) {
            throw new RuntimeException(e.toString());
        }
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
