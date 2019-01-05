package com.acg12.ui.fragment;

import android.os.Bundle;

import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.ui.views.CollectAlbumView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 14:57
 * Description:
 */
public class CollectAlbumFragment extends PresenterFragmentImpl<CollectAlbumView>{

    public static CollectAlbumFragment newInstance() {
        CollectAlbumFragment fragment = new CollectAlbumFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
