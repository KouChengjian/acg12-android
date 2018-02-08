package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.acg12.ui.base.BaseFragment;
import org.acg12.ui.views.SearchIntroView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchIntroFragment extends BaseFragment<SearchIntroView> {

    public static SearchIntroFragment newInstance(String title) {
        SearchIntroFragment fragment = new SearchIntroFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }
}
