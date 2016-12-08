// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AddFriendActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.AddFriendActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296408, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131296408, "field 'myRefreshListView'");
    view = finder.findRequiredView(source, 2131296407, "field 'btn_search'");
    target.btn_search = finder.castView(view, 2131296407, "field 'btn_search'");
    view = finder.findRequiredView(source, 2131296409, "field 'mListView'");
    target.mListView = finder.castView(view, 2131296409, "field 'mListView'");
    view = finder.findRequiredView(source, 2131296405, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296405, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296406, "field 'et_find_name'");
    target.et_find_name = finder.castView(view, 2131296406, "field 'et_find_name'");
  }

  @Override public void reset(T target) {
    target.myRefreshListView = null;
    target.btn_search = null;
    target.mListView = null;
    target.toolbar = null;
    target.et_find_name = null;
  }
}
