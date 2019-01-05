package com.acg12.ui.fragment;

import android.os.Bundle;

import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.ui.views.CollectBangunView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 15:00
 * Description:
 */
public class CollectBangunFragment extends PresenterFragmentImpl<CollectBangunView> {

    public static CollectBangunFragment newInstance() {
        CollectBangunFragment fragment = new CollectBangunFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
