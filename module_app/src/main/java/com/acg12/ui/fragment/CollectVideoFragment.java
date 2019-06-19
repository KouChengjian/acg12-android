package com.acg12.ui.fragment;

import android.os.Bundle;

import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.ui.views.CollectVideoView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 15:00
 * Description:
 */
public class CollectVideoFragment extends PresenterFragmentImpl<CollectVideoView> {

    public static CollectVideoFragment newInstance() {
        CollectVideoFragment fragment = new CollectVideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
