package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.acg12.entity.Subject;
import org.acg12.ui.base.SkinBaseFragment;
import org.acg12.ui.views.SearchIntroView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchIntroFragment extends SkinBaseFragment<SearchIntroView> {

    private Subject subject;

    public static SearchIntroFragment newInstance(String title ,Subject subject) {
        SearchIntroFragment fragment = new SearchIntroFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("subject" , subject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        subject = (Subject)getArguments().getSerializable("subject");
        mView.bindData(subject);
    }

}
