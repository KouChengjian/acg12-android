package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.acg12.R;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.SearchVideoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchVideoFragment extends SkinBaseFragment<SearchVideoView> {


    public static SearchVideoFragment newInstance(String title) {
        SearchVideoFragment fragment = new SearchVideoFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }
}
