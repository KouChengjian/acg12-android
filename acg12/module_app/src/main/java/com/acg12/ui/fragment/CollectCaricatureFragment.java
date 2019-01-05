package com.acg12.ui.fragment;

import android.os.Bundle;

import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.ui.views.CollectCaricatureView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 14:58
 * Description:
 */
public class CollectCaricatureFragment extends PresenterFragmentImpl<CollectCaricatureView> {

    public static CollectCaricatureFragment newInstance() {
        CollectCaricatureFragment fragment = new CollectCaricatureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
