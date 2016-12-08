package com.kcj.animationfriend.view;

import android.app.Activity;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.kcj.animationfriend.ui.base.BaseFragment;

/**
 * Created by desmond on 12/4/15.
 */
public class ScrollTabHolderFragment extends BaseFragment implements ScrollTabHolder {

    protected ScrollTabHolder mScrollTabHolder;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mScrollTabHolder = (ScrollTabHolder) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ScrollTabHolder");
        }
    }

    @Override
    public void onDetach() {
        mScrollTabHolder = null;
        super.onDetach();
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {}

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount, int pagePosition) {}

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {}

}
