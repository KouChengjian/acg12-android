// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class NearPeopleActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.NearPeopleActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230922, "field 'mListView'");
    target.mListView = finder.castView(view, 2131230922, "field 'mListView'");
    view = finder.findRequiredView(source, 2131230872, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131230872, "field 'myRefreshListView'");
    view = finder.findRequiredView(source, 2131230869, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230869, "field 'toolbar'");
  }

  @Override public void reset(T target) {
    target.mListView = null;
    target.myRefreshListView = null;
    target.toolbar = null;
  }
}
